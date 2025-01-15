package g4.comsci.minecat.item.custom;

import g4.comsci.minecat.MineCatClient;
import g4.comsci.minecat.network.CatLocatorPacketHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CatLocatorItem extends Item {

    public CatLocatorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            MineCatClient.openCatLocatorScreen(user);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    // --------- GUI Class ---------
    public static class CatLocatorScreen extends Screen {
        private final PlayerEntity player;
        private List<CatEntity> ownedCats = new ArrayList<>();
        private List<CatEntity> filteredCats = new ArrayList<>();
        private TextFieldWidget searchField;
        private String searchText = "";
        private boolean sortByDistance = false;
        private boolean sortAscending = true; // Default to ascending order
        private ButtonWidget nameSortButton;
        private ButtonWidget distanceSortButton;
        private List<ButtonWidget> catButtons = new ArrayList<>();

        public CatLocatorScreen(PlayerEntity player) {
            super(Text.literal("Cat Locator"));
            this.player = player;
        }

        @Override
        protected void init() {
            int centerX = this.width / 2; // Center of the screen horizontally
            int centerY = this.height / 2; // Center of the screen vertically

            // Load owned cats
            loadOwnedCats();

            // Search TextField
            this.searchField = new TextFieldWidget(this.textRenderer, centerX - 100, centerY - 110, 200, 20, Text.literal("Search Cat Name"));
            this.searchField.setChangedListener(text -> {
                this.searchText = text;
                filterCats();
            });
            this.addDrawableChild(this.searchField);

            // Position for buttons on the left
            int leftX = 10; // Fixed position on the left
            int buttonWidth = 150; // Width of the buttons
            int nameButtonY = centerY - 70; // Vertical position for "Sort By: Name"
            int distanceButtonY = nameButtonY + 30; // Slightly below the "Sort By: Name" button

            // Button: Sort by Name (Default to Asc)
            this.nameSortButton = ButtonWidget.builder(Text.literal("Sort By: Name (Asc)"), button -> {
                sortAscending = !sortAscending; // Toggle sort order
                sortByDistance = false; // Sort by name
                filterCats();
                updateSortButtons();
            }).dimensions(leftX, nameButtonY, buttonWidth, 20).build();
            this.addDrawableChild(this.nameSortButton);

            // Button: Sort by Distance (Default to Asc)
            this.distanceSortButton = ButtonWidget.builder(Text.literal("Sort By: Distance (Asc)"), button -> {
                sortAscending = !sortAscending; // Toggle sort order
                sortByDistance = true; // Sort by distance
                filterCats();
                updateSortButtons();
            }).dimensions(leftX, distanceButtonY, buttonWidth, 20).build();
            this.addDrawableChild(this.distanceSortButton);

            // Update filtered list
            filterCats();
        }

        private void loadOwnedCats() {
            World world = player.getWorld();
            Box searchArea = new Box(player.getX() - 50, player.getY() - 50, player.getZ() - 50,
                    player.getX() + 50, player.getY() + 50, player.getZ() + 50);

            this.ownedCats = world.getEntitiesByClass(CatEntity.class, searchArea,
                    cat -> cat.isTamed() && cat.getOwnerUuid() != null && cat.getOwnerUuid().equals(player.getUuid()));

            this.filteredCats = new ArrayList<>(ownedCats);
        }

        private void filterCats() {
            this.catButtons.forEach(this::remove); // Remove old buttons
            this.catButtons.clear(); // Clear button list

            // Filter and sort cats
            this.filteredCats = ownedCats.stream()
                    .filter(cat -> cat.getName().getString().toLowerCase().contains(searchText.toLowerCase()))
                    .sorted((cat1, cat2) -> {
                        if (sortByDistance) {
                            return sortAscending
                                    ? Double.compare(cat1.squaredDistanceTo(player), cat2.squaredDistanceTo(player))
                                    : Double.compare(cat2.squaredDistanceTo(player), cat1.squaredDistanceTo(player));
                        } else {
                            return sortAscending
                                    ? cat1.getName().getString().compareToIgnoreCase(cat2.getName().getString())
                                    : cat2.getName().getString().compareToIgnoreCase(cat1.getName().getString());
                        }
                    })
                    .collect(Collectors.toList());

            int centerX = this.width / 2;
            int startY = this.height / 2 - 70;
            int lineHeight = 30;
            int maxEntries = (this.height - startY - 10) / lineHeight;

            for (int i = 0; i < Math.min(filteredCats.size(), maxEntries); i++) {
                CatEntity cat = filteredCats.get(i);
                String name = cat.getName().getString();
                double distance = Math.sqrt(player.squaredDistanceTo(cat));
                String entry = name + " (Distance: " + Math.round(distance) + ")";

                int x = centerX - 100; // Centered horizontally
                int y = startY + (i * lineHeight); // Spaced vertically

                ButtonWidget catButton = ButtonWidget.builder(Text.literal(entry), button -> {
                    selectCat(cat);
                }).dimensions(x, y, 200, 20).build();

                this.addDrawableChild(catButton);
                this.catButtons.add(catButton);
            }
        }

        private void updateSortButtons() {
            String nameSortText = "Sort By: Name (" + (sortAscending ? "Asc" : "Desc") + ")";
            String distanceSortText = "Sort By: Distance (" + (sortAscending ? "Asc" : "Desc") + ")";

            this.nameSortButton.setMessage(Text.literal(nameSortText));
            this.distanceSortButton.setMessage(Text.literal(distanceSortText));
        }

        private void selectCat(CatEntity cat) {
            // Send a packet to the server to apply the glow effect
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(cat.getId()); // Write the entity ID
            ClientPlayNetworking.send(CatLocatorPacketHandler.SET_GLOW_PACKET_ID, buf);

            // Display a message with the cat's position
            Vec3d position = cat.getPos();
            player.sendMessage(Text.literal("Cat " + cat.getName().getString() + " is at: X=" +
                    Math.round(position.x) + ", Y=" + Math.round(position.y) + ", Z=" + Math.round(position.z)), false);

            // Close the UI
            this.close();
        }


        @Override
        public boolean shouldPause() {
            return false;
        }
    }
}

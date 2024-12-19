package g4.comsci.minecat.item.custom;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.client.MinecraftClient;

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
            MinecraftClient client = MinecraftClient.getInstance();
            client.execute(() -> client.setScreen(new CatLocatorScreen(user)));
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

        public CatLocatorScreen(PlayerEntity player) {
            super(Text.literal("Cat Locator"));
            this.player = player;
        }

        @Override
        protected void init() {
            int centerX = this.width / 2;
            int centerY = this.height / 2;

            // Load owned cats
            loadOwnedCats();

            // Search TextField
            this.searchField = new TextFieldWidget(this.textRenderer, centerX - 100, centerY - 100, 200, 20, Text.literal("Search Cat Name"));
            this.searchField.setChangedListener(text -> {
                this.searchText = text;
                filterCats();
            });
            this.addDrawableChild(this.searchField);

            // Button: Sort by Name (Positioned to the left)
            this.addDrawableChild(ButtonWidget.builder(Text.literal("Sort By: Name"), button -> {
                sortByDistance = false;
                filterCats();
            }).dimensions(10, centerY - 70, 100, 20).build());

            // Button: Sort by Distance (Positioned to the left)
            this.addDrawableChild(ButtonWidget.builder(Text.literal("Sort By: Distance"), button -> {
                sortByDistance = true;
                filterCats();
            }).dimensions(10, centerY - 40, 100, 20).build());

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
            // Filter by search text
            this.filteredCats = ownedCats.stream()
                    .filter(cat -> cat.getName().getString().toLowerCase().contains(searchText.toLowerCase()))
                    .sorted(sortByDistance
                            ? Comparator.comparingDouble(cat -> cat.squaredDistanceTo(player))  // เปรียบเทียบระยะทาง
                            : Comparator.comparing(cat -> cat.getName().getString(), String::compareToIgnoreCase))  // เปรียบเทียบชื่อแมว
                    .collect(Collectors.toList());  // ใช้ collect แทน toList
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {
            this.renderBackground(context);
            super.render(context, mouseX, mouseY, delta);
            this.searchField.render(context, mouseX, mouseY, delta);

            int startY = this.height / 2 - 50;
            int lineHeight = 20;
            int maxEntries = (this.height - startY - 10) / lineHeight;

            // Display filtered cat list
            for (int i = 0; i < Math.min(filteredCats.size(), maxEntries); i++) {
                CatEntity cat = filteredCats.get(i);
                String name = cat.getName().getString();
                double distance = Math.sqrt(player.squaredDistanceTo(cat));
                String entry = name + " (Distance: " + Math.round(distance) + ")";
                int x = this.width / 2 - 100;
                int y = startY + (i * lineHeight);

                // Add button for each cat to make it glow when clicked
                this.addDrawableChild(ButtonWidget.builder(Text.literal(entry), button -> {
                    selectCat(cat);  // Select the cat and make it glow
                }).dimensions(x, y, 200, 20).build());
            }
        }

        // Method to make the cat glow when selected and show its position
        private void selectCat(CatEntity cat) {
            // Make the cat glow using StatusEffect
            cat.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, Integer.MAX_VALUE, 0, false, false));

            Vec3d position = cat.getPos();
            player.sendMessage(Text.literal("Cat " + cat.getName().getString() + " is at: X=" +
                    Math.round(position.x) + ", Y=" + Math.round(position.y) + ", Z=" + Math.round(position.z)), false);
        }

        @Override
        public boolean shouldPause() {
            return false;
        }
    }
}

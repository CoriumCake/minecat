package g4.comsci.minecat.item.custom;

import g4.comsci.minecat.MineCatClient;
import g4.comsci.minecat.network.CatLocatorPacketHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
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
        private List<TameableEntity> ownedCats = new ArrayList<>();
        private List<TameableEntity> filteredCats = new ArrayList<>();
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

            // ค้นหาเอนทิตีที่เป็น TameableEntity และเจ้าของเป็นผู้เล่น
            this.ownedCats = world.getEntitiesByClass(TameableEntity.class, searchArea,
                    entity -> entity.isTamed() && entity.getOwnerUuid() != null && entity.getOwnerUuid().equals(player.getUuid()));

            this.filteredCats = new ArrayList<>(ownedCats);
        }


        private void filterCats() {
            this.catButtons.forEach(this::remove); // Remove old buttons
            this.catButtons.clear(); // Clear button list

            // Filter and sort cats
            this.filteredCats = ownedCats.stream()
                    .filter(entity -> entity.getName().getString().toLowerCase().contains(searchText.toLowerCase()))
                    .sorted((entity1, entity2) -> {
                        if (sortByDistance) {
                            return sortAscending
                                    ? Double.compare(entity1.squaredDistanceTo(player), entity2.squaredDistanceTo(player)) //จะเรียงลำดับจากระยะทางน้อย ไป มาก

                                    : Double.compare(entity2.squaredDistanceTo(player), entity1.squaredDistanceTo(player));//จะเรียงลำดับจากระยะทางมาก ไป น้อย
                            //ใช้เมธ็อด Double.compare() ที่ติดมากับ java อยู่แล้ว  ใช้ squaredDistanceTo(player) เพื่อคำนวณระยะทาง ที่ห่างกับตัวผู้เล่น
                        } else {
                            return sortAscending
                                    ? entity1.getName().getString().compareToIgnoreCase(entity2.getName().getString())
                                    : entity2.getName().getString().compareToIgnoreCase(entity1.getName().getString());
                            // getName().getString() เพื่อดึงชื่อของแมวในรูปแบบข้อความ compareToIgnoreCase() เปรียบเทียบโดยไม่สนตัวโหญ่ตัวเล็ก

                        }
                    })
                    .collect(Collectors.toList());


            int centerX = this.width / 2;
            int startY = this.height / 2 - 70;
            int lineHeight = 30;
            int maxEntries = (this.height - startY - 10) / lineHeight;

            for (int i = 0; i < Math.min(filteredCats.size(), maxEntries); i++) {
                TameableEntity entity = filteredCats.get(i);
                String name = entity.getName().getString();
                double distance = Math.sqrt(player.squaredDistanceTo(entity));
                String entry = name + " (Distance: " + Math.round(distance) + ")";

                int x = centerX - 100; // ตำแหน่งแนวนอน
                int y = startY + (i * lineHeight); // ตำแหน่งแนวตั้ง

                ButtonWidget catButton = ButtonWidget.builder(Text.literal(entry), button -> {
                    selectEntity(entity);
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

        private void selectEntity(TameableEntity entity) {
            // ส่งข้อมูลไปยังเซิร์ฟเวอร์เพื่อใช้เอฟเฟกต์
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(entity.getId()); // ส่ง ID ของเอนทิตี
            ClientPlayNetworking.send(CatLocatorPacketHandler.SET_GLOW_PACKET_ID, buf);

            // แสดงข้อความตำแหน่งของเอนทิตี
            Vec3d position = entity.getPos();
            player.sendMessage(Text.literal("Entity " + entity.getName().getString() + " is at: X=" +
                    Math.round(position.x) + ", Y=" + Math.round(position.y) + ", Z=" + Math.round(position.z)), false);

            // ปิด UI
            this.close();
        }



        @Override
        public boolean shouldPause() {
            return false;
        }
    }
}

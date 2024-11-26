package g4.comsci.minecat.item.custom;

import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.util.TypedActionResult;
import net.minecraft.text.Text;

import java.util.List;

public class CatLocatorItem extends Item {

    public CatLocatorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {

            // กำหนดพื้นที่ค้นหาสำหรับแมว (ระยะ 20 บล็อก)
            Box searchArea = new Box(user.getX() - 20, user.getY() - 20, user.getZ() - 20,
                    user.getX() + 20, user.getY() + 20, user.getZ() + 20);

            // ค้นหาแมวที่อยู่ในพื้นที่ที่กำหนด
            List<CatEntity> cats = world.getEntitiesByClass(CatEntity.class, searchArea, cat -> true);
            CatEntity closestCat = null;
            double closestDistance = Double.MAX_VALUE;

            // ค้นหาตำแหน่งของแมวที่ใกล้ที่สุด
            for (CatEntity cat : cats) {
                double distance = user.squaredDistanceTo(cat);
                if (distance < closestDistance) {
                    closestCat = cat;
                    closestDistance = distance;
                }
            }

            // หากพบแมวที่ใกล้ที่สุด
            if (closestCat != null) {
                // คุณสามารถใช้ตำแหน่งของแมวที่ใกล้ที่สุดที่นี่
                double catX = closestCat.getX();
                double catY = closestCat.getY();
                double catZ = closestCat.getZ();

                // สร้างข้อความที่จะแสดงตำแหน่งของแมวในช่องแชท
                Text message = Text.literal("Nearby cat: X=" + Math.round(catX) + " Y=" + Math.round(catY) + " Z=" + Math.round(catZ));

                // ส่งข้อความไปยังช่องแชทของผู้เล่น
                user.sendMessage(message, false);
            }
        }

        return TypedActionResult.success(user.getStackInHand(hand)); // ใช้ return type ที่ถูกต้อง
    }
}

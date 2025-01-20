package g4.comsci.minecat.entity.behavior;

import g4.comsci.minecat.entity.CustomCatEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;

public class CatWorkGoal extends Goal {
    private final TameableEntity cat;
    private final CustomCatEntity customCat;

    public CatWorkGoal(TameableEntity cat) {
        this.cat = cat;
        this.customCat = (CustomCatEntity) cat;
    }

    @Override
    public boolean canStart() {
        if (!(cat.getOwner() instanceof PlayerEntity) || !cat.isTamed() || cat.isSitting()) {
            return false;
        }
        return customCat.getBehaviorManager().hasActiveWork();
    }

    @Override
    public boolean shouldContinue() {
        return canStart() && !cat.isSitting();
    }

    @Override
    public void tick() {
        PlayerEntity owner = (PlayerEntity) cat.getOwner();
        if (owner != null) {
            customCat.getBehaviorManager().tick(owner);
        }
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }
}
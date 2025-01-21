package g4.comsci.minecat.datagen;

import g4.comsci.minecat.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        // Purrium Sword Recipe
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PURRIUM_SWORD, 1)
                .pattern(" P ")
                .pattern(" P ")
                .pattern(" S ")
                .input('P', ModItems.PURRIUM)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.PURRIUM), conditionsFromItem(ModItems.PURRIUM))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PURRIUM_SWORD)));

        // Purrium Pickaxe Recipe
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PURRIUM_PICKAXE, 1)
                .pattern("PPP")
                .pattern(" S ")
                .pattern(" S ")
                .input('P', ModItems.PURRIUM)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.PURRIUM), conditionsFromItem(ModItems.PURRIUM))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PURRIUM_PICKAXE)));

        // Purrium Axe Recipe
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PURRIUM_AXE, 1)
                .pattern("PP ")
                .pattern("PS ")
                .pattern(" S ")
                .input('P', ModItems.PURRIUM)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.PURRIUM), conditionsFromItem(ModItems.PURRIUM))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PURRIUM_AXE)));

        // Purrium Hoe Recipe
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PURRIUM_HOE, 1)
                .pattern("PP ")
                .pattern(" S ")
                .pattern(" S ")
                .input('P', ModItems.PURRIUM)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.PURRIUM), conditionsFromItem(ModItems.PURRIUM))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PURRIUM_HOE)));

        // Purrium Shovel Recipe
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PURRIUM_SHOVEL, 1)
                .pattern(" P ")
                .pattern(" S ")
                .pattern(" S ")
                .input('P', ModItems.PURRIUM)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.PURRIUM), conditionsFromItem(ModItems.PURRIUM))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PURRIUM_SHOVEL)));
    }
}
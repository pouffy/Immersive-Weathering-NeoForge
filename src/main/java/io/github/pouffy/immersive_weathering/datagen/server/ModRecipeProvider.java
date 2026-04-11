package io.github.pouffy.immersive_weathering.datagen.server;

import io.github.pouffy.immersive_weathering.ImmersiveWeathering;
import io.github.pouffy.immersive_weathering.reg.ModBlocks;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesTypeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput, HolderLookup.Provider holderLookup) {
        craftingRecipes(pRecipeOutput);
    }

    private void craftingRecipes(RecipeOutput pRecipeOutput) {
        for (var type : LeavesTypeRegistry.INSTANCE) {
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.LEAF_PILES.get(type), 6)
                    .pattern("###")
                    .define('#', type.leaves)
                    .unlockedBy("has_leaves", has(type.leaves))
                    .save(pRecipeOutput, ImmersiveWeathering.res("crafting/"+type.id.getPath()+"_leaf_pile"));
        }
    }
}

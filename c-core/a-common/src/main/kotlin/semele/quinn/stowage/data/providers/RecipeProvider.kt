package semele.quinn.stowage.data.providers

import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.Items
import semele.quinn.stowage.impl.Utils
import java.util.concurrent.CompletableFuture
import net.minecraft.data.recipes.RecipeProvider as VanillaRecipeProvider

class RecipeProvider(output: PackOutput,
                              lookupProvider: CompletableFuture<HolderLookup.Provider>
) : VanillaRecipeProvider(output, lookupProvider) {
    override fun buildRecipes(output: RecipeOutput) {
        val mutator = BuiltInRegistries.ITEM[Utils.id("mutator")]

        ShapedRecipeBuilder(RecipeCategory.TOOLS, mutator, 1)
            .unlockedBy("has_chest", has(Items.CHEST))
            .pattern("  C")
            .pattern(" S ")
            .pattern("S  ")
            .define('C', Items.CHEST)
            .define('S', Items.STICK)
            .save(output)
    }
}
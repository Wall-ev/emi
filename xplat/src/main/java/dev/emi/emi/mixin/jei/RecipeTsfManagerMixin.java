package dev.emi.emi.mixin.jei;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.screen.EmiScreenManager;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.library.recipes.RecipeTransferManager;
import net.minecraft.screen.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.emi.emi.screen.EmiScreenManager.recipeTypeMap;

@Mixin(RecipeTransferManager.class)
public class RecipeTsfManagerMixin {

//    @Mutable
//    @Shadow
//    @Final
//    private ImmutableTable<Class<? extends ScreenHandler>, RecipeType<?>, IRecipeTransferHandler<?, ?>> recipeTransferHandlers;

//    @Accessor("recipeTransferHandlers")
//    ImmutableTable<Class<? extends ScreenHandler>, RecipeType<?>, IRecipeTransferHandler<?, ?>> getRecipeTransferHandlers();

//    @Accessor("recipeTransferHandlers")
//    ImmutableTable<Class<? extends ScreenHandler>, RecipeType<?>, IRecipeTransferHandler<?, ?>> recipeTransferHandlers1;

    @Inject(at = @At("RETURN"), method = "<init>", remap = false)
    public void RecipeTsfManagerMixin(ImmutableTable<Class<? extends ScreenHandler>, RecipeType<?>, IRecipeTransferHandler<?, ?>> recipeTransferHandlers, CallbackInfo ci) {
        vanillaRecipeMapInit();
        pluginRecipeMapInit(recipeTransferHandlers);
    }

    @Unique
    private static void pluginRecipeMapInit(ImmutableTable<Class<? extends ScreenHandler>, RecipeType<?>, IRecipeTransferHandler<?, ?>> recipeTransferHandlers) {
        for (Table.Cell<Class<? extends ScreenHandler>, RecipeType<?>, IRecipeTransferHandler<?, ?>> classRecipeTypeIRecipeTransferHandlerCell : recipeTransferHandlers.cellSet()) {
            Class<? extends ScreenHandler> rowKey = classRecipeTypeIRecipeTransferHandlerCell.getRowKey();
            String name = rowKey.getName();
            RecipeType<?> columnKey = classRecipeTypeIRecipeTransferHandlerCell.getColumnKey();
            String string = columnKey.getUid().toString();
            EmiScreenManager.recipeTypeMap.put(name, string);
        }
    }

    @Unique
    private static void vanillaRecipeMapInit() {
        EmiScreenManager.recipeTypeMap.put(CraftingScreenHandler.class.getName(), VanillaEmiRecipeCategories.CRAFTING.getId().toString());
        EmiScreenManager.recipeTypeMap.put(FurnaceScreenHandler.class.getName(), VanillaEmiRecipeCategories.SMELTING.getId().toString());
        EmiScreenManager.recipeTypeMap.put(BlastFurnaceScreenHandler.class.getName(), VanillaEmiRecipeCategories.BLASTING.getId().toString());
        EmiScreenManager.recipeTypeMap.put(SmokerScreenHandler.class.getName(), VanillaEmiRecipeCategories.SMOKING.getId().toString());
        EmiScreenManager.recipeTypeMap.put(StonecutterScreenHandler.class.getName(), VanillaEmiRecipeCategories.STONECUTTING.getId().toString());
        EmiScreenManager.recipeTypeMap.put(BrewingStandScreenHandler.class.getName(), VanillaEmiRecipeCategories.BREWING.getId().toString());

//        EmiScreenManager.recipeTypeMap.put(SmithingScreenHandler.class.getName(), VanillaEmiRecipeCategories.SMITHING.getId().toString());
//        EmiScreenManager.recipeTypeMap.put(AnvilScreenHandler.class.getName(), VanillaEmiRecipeCategories.ANVIL_REPAIRING.getId().toString());
    }
}

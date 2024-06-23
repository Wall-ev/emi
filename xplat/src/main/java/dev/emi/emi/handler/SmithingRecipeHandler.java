package dev.emi.emi.handler;

import com.google.common.collect.Lists;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.recipe.handler.EmiCraftContext;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.screen.SmithingScreenHandler;
import net.minecraft.screen.slot.Slot;

import java.util.List;

public class SmithingRecipeHandler implements StandardRecipeHandler<SmithingScreenHandler> {

    /**
     * @param recipe
     * @return Whether the handler is applicable for the provided recipe.
     */
    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        return recipe.getCategory() == VanillaEmiRecipeCategories.SMITHING;
    }

    /**
     * @param handler
     * @return The slots for the recipe handler to source ingredients from.
     * Typically this should include the player's inventory, and crafting slots.
     */
    @Override
    public List<Slot> getInputSources(SmithingScreenHandler handler) {
        List<Slot> list = Lists.newArrayList();
//        // 水瓶
//        for (int i = 0; i < 3; i++) {
//            list.add(handler.getSlot(i));
//        }
//
//        // 原料
//        list.add(handler.getSlot(4));
        list.add(handler.getSlot(0));
        list.add(handler.getSlot(1));
        list.add(handler.getSlot(2));

        // 玩家背包
        int invStart = 4;
        for (int i = invStart; i < invStart + 36; i++) {
            list.add(handler.getSlot(i));
        }

        return list;
    }

    /**
     * @param handler
     * @return The slots where inputs should be placed to perform crafting.
     */
    @Override
    public List<Slot> getCraftingSlots(SmithingScreenHandler handler) {
        List<Slot> list = Lists.newArrayList();
//        list.add(handler.getSlot(4));

        for (int i = 0; i < 3; i++) {
            list.add(handler.getSlot(i));
        }

        return list;
    }

    @Override
    public boolean craft(EmiRecipe recipe, EmiCraftContext<SmithingScreenHandler> context) {
        boolean craft = StandardRecipeHandler.super.craft(recipe, context);
        return craft;
    }
}

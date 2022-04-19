package dev.emi.emi.recipe;

import java.util.List;

import dev.emi.emi.EmiRenderHelper;
import dev.emi.emi.VanillaPlugin;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class EmiCraftingRecipe implements EmiRecipe {
	protected final Identifier id;
	protected final List<EmiIngredient> input;
	protected final EmiStack output;
	protected final boolean shapeless;

	public EmiCraftingRecipe(List<EmiIngredient> input, EmiStack output, Identifier id) {
		this(input, output, id, true);
	}

	public EmiCraftingRecipe(List<EmiIngredient> input, EmiStack output, Identifier id, boolean shapeless) {
		this.input = input;
		this.output = output;
		this.id = id;
		this.shapeless = shapeless;
	}

	@Override
	public EmiRecipeCategory getCategory() {
		return VanillaPlugin.CRAFTING;
	}

	@Override
	public Identifier getId() {
		return id;
	}

	@Override
	public List<EmiIngredient> getInputs() {
		return input;
	}

	@Override
	public List<EmiStack> getOutputs() {
		return List.of(output);
	}

	@Override
	public int getDisplayWidth() {
		return 118;
	}

	@Override
	public int getDisplayHeight() {
		return 54;
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		widgets.addTexture(EmiRenderHelper.WIDGETS, 60, 18, 24, 17, 44, 0);
		if (shapeless) {
			widgets.addTexture(EmiRenderHelper.WIDGETS, 97, 0, 16, 14, 95, 0);
		}
		for (int i = 0; i < 9; i++) {
			if (i < input.size()) {
				widgets.addSlot(input.get(i), i % 3 * 18, i / 3 * 18);
			} else {
				widgets.addSlot(EmiStack.of(ItemStack.EMPTY), i % 3 * 18, i / 3 * 18);
			}
		}
		widgets.addSlot(output, 92, 14).output(true).recipeContext(this);
	}
}

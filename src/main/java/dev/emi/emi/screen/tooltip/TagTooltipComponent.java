package dev.emi.emi.screen.tooltip;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import dev.emi.emi.EmiClient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TagTooltipComponent implements TooltipComponent {
	private static final Identifier TEXTURE = new Identifier("emi", "textures/gui/widgets.png");
	private static final int MAX_DISPLAYED = 63;
	private final List<EmiStack> stacks;

	public TagTooltipComponent(List<EmiStack> stacks) {
		this.stacks = stacks;
	}

	public int getStackWidth() {
		if (stacks.size() > 16) {
			return 8;
		} else {
			return 4;
		}
	}

	@Override
	public int getHeight() {
		int s = stacks.size();
		if (s > MAX_DISPLAYED) {
			s = MAX_DISPLAYED;
		}
		return ((s - 1) / getStackWidth() + 1) * 18;
	}

	@Override
	public int getWidth(TextRenderer textRenderer) {
		return 18 * getStackWidth();
	}
	
	@Override
	public void drawItems(TextRenderer textRenderer, int x, int y, MatrixStack matrices, ItemRenderer itemRenderer, int z) {
		EmiClient.tagZOffset = z;
		int sw = getStackWidth();
		for (int i = 0; i < stacks.size() && i < MAX_DISPLAYED; i++) {
			stacks.get(i).renderIcon(matrices, x + i % sw * 18, y + i / sw * 18, MinecraftClient.getInstance().getTickDelta());
		}
		if (stacks.size() > MAX_DISPLAYED) {
			RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
			RenderSystem.setShaderTexture(0, TEXTURE);
			matrices.push();
			matrices.translate(0, 0, z);
			DrawableHelper.drawTexture(matrices, x + getWidth(textRenderer) - 14, y + getHeight() - 8, 0, 192, 9, 3, 256, 256);
			matrices.pop();
		}
		EmiClient.tagZOffset = 0;
	}
}

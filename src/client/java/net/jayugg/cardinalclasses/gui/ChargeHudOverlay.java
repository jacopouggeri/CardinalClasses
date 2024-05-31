package net.jayugg.cardinalclasses.gui;

import com.google.common.collect.BiMap;
import com.mojang.blaze3d.systems.RenderSystem;
import net.jayugg.cardinalclasses.CardinalClasses;
import net.jayugg.cardinalclasses.core.ActiveSkill;
import net.jayugg.cardinalclasses.core.PlayerClass;
import net.jayugg.cardinalclasses.core.SkillCooldownHelper;
import net.jayugg.cardinalclasses.core.SkillSlot;
import net.jayugg.cardinalclasses.component.ActiveSkillComponent;
import net.jayugg.cardinalclasses.component.ModComponents;
import net.jayugg.cardinalclasses.util.PlayerClassManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChargeHudOverlay {
    public static final Identifier BAR = new Identifier("cardinalclasses", "textures/gui/bar.png");

    private static final HashMap<SkillSlot, Integer> currentFilledWidths = new HashMap<>();

    public static void onHudRender(MatrixStack matrixStack) {
        int baseX;
        int baseY;
        int barWidth = 80;
        int xOffset = 90;
        int yOffset = -60;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return;
        }
        ClientPlayerEntity player = client.player;
        PlayerClass playerClass = PlayerClassManager.getClass(player);
        if (player == null || playerClass == null) {
            return;
        }
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        baseX = width / 2;
        baseY = height;

        List<Pair<Integer, Integer>> positions = Arrays.asList(
                new Pair<>(yOffset, -xOffset), // Position for the first skill on the left
                new Pair<>(yOffset - 6, -xOffset),  // Position for the second skill on the left
                new Pair<>(yOffset, xOffset - barWidth),  // Position for the first skill on the right
                new Pair<>(yOffset - 6, xOffset - barWidth)  // Position for the second skill on the right
        );

        BiMap<SkillSlot, ActiveSkill> activeSkills = playerClass.getActiveSkills();

        for (Map.Entry<SkillSlot, ActiveSkill> entry : activeSkills.entrySet()) {
            client.getProfiler().push("skillBar" + entry.getKey().getValue());
            int x = baseX;
            int y = baseY;
            Pair<Integer, Integer> position = positions.get(entry.getKey().getValue() - 1);
            x += position.getRight();
            y += position.getLeft();
            SkillSlot skillSlot = entry.getKey();
            ActiveSkill activeSkill = entry.getValue();

            int skillLevel = PlayerClassManager.getSkillLevel(player, activeSkill);
            if (skillLevel > 0) {
                SkillCooldownHelper cooldownHelper = activeSkill.getCooldownHelper();
                ActiveSkillComponent component = ModComponents.ACTIVE_SKILLS_COMPONENT.get(player);
                int lastUsed = (int) component.getLastUsed(skillSlot);
                int maxCharges = cooldownHelper.getMaxCharges(skillLevel);
                int chargeTime = cooldownHelper.getChargeTime(skillLevel);
                // Remove the skill from the list if it's been too long since it was last used
                if (CardinalClasses.CONFIG.hideSkillBarsWhenNotInUse() && (player.world.getTime() - lastUsed) > (long) chargeTime * maxCharges + 200) {
                   continue;
                }

                long worldTime = player.world.getTime();
                float progressPercent = cooldownHelper.fullChargeProgress(worldTime, lastUsed, skillLevel)
                        + cooldownHelper.nextChargeProgress(worldTime, lastUsed, skillLevel);
                int color = activeSkill.getColor();
                drawBarSmoothly(matrixStack, progressPercent, barWidth, skillSlot, x, y, color);
            }
        }
    }

    private static void drawBarSmoothly(MatrixStack matrixStack, float progressPercent, int barWidth, SkillSlot skillSlot, int x, int y, int color) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);

        int filledWidth = Math.round(progressPercent * barWidth);

        // Get current filled width for this skill slot
        Integer currentfilledWidth = currentFilledWidths.get(skillSlot);
        if (currentfilledWidth == null) {
            currentfilledWidth = 0;
        }

        // Update currentFilledWidth towards filledWidth
        if (currentfilledWidth < filledWidth) {
            currentfilledWidth++;
        } else if (currentfilledWidth > filledWidth) {
            currentfilledWidth--;
        }

        // Store updated width
        currentFilledWidths.put(skillSlot, currentfilledWidth);

        drawBar(BAR, matrixStack, x, y, currentfilledWidth, barWidth, color);
    }

    private static void drawBar(Identifier texture, MatrixStack matrixStack, int x, int y, int progress, int totalWidth, int color) {
        float alpha = ((color >> 24) & 0xFF) / 255.0F;

        for (int i = 0; i < totalWidth; i++) {
            float[] modColors = calculateModulatedColors(color, i, totalWidth);
            RenderSystem.setShaderColor(modColors[0], modColors[1], modColors[2], alpha);
            RenderSystem.setShaderTexture(0, texture);
            DrawableHelper.drawTexture(matrixStack, x + i, y, i, 0, 1, 5, totalWidth, 10);
            if (i < progress) {
                RenderSystem.setShaderTexture(0, texture);
                DrawableHelper.drawTexture(matrixStack, x + i, y, i, 5, 1, 5, totalWidth, 10);
            }
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F); // Reset color to white
    }

    private static float[] calculateModulatedColors(int color, int pixelIndex, int totalWidth) {
        float red = ((color >> 16) & 0xFF) / 255.0F;
        float green = ((color >> 8) & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;

        // Convert the RGB color to HSV
        float[] hsv = Color.RGBtoHSB((int) (red * 255), (int) (green * 255), (int) (blue * 255), null);

        // Linearly interpolate the hue based on the position of the pixel
        float hue = hsv[0] + 0.075f * ((float)pixelIndex / (totalWidth - 1) - 0.5f);

        // Convert the HSV color back to RGB
        int rgb = Color.HSBtoRGB(hue, hsv[1], hsv[2]);
        float modRed = ((rgb >> 16) & 0xFF) / 255.0F;
        float modGreen = ((rgb >> 8) & 0xFF) / 255.0F;
        float modBlue = (rgb & 0xFF) / 255.0F;

        return new float[]{modRed, modGreen, modBlue};
    }
}

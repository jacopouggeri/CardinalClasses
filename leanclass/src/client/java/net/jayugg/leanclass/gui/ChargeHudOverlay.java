package net.jayugg.leanclass.gui;

import com.google.common.collect.BiMap;
import com.mojang.blaze3d.systems.RenderSystem;
import net.jayugg.leanclass.base.ActiveSkill;
import net.jayugg.leanclass.base.SkillCooldownHelper;
import net.jayugg.leanclass.base.SkillSlot;
import net.jayugg.leanclass.component.ActiveSkillComponent;
import net.jayugg.leanclass.component.ModComponents;
import net.jayugg.leanclass.util.PlayerClassManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ChargeHudOverlay {
    public static final Identifier BAR_EMPTY = new Identifier("leanclass", "textures/gui/bar_empty.png");
    public static final Identifier BAR_FULL = new Identifier("leanclass", "textures/gui/bar_full.png");
    public static final Identifier BAR_EMPTY_5 = new Identifier("leanclass", "textures/gui/bar_empty_5.png");
    public static final Identifier BAR_FULL_5 = new Identifier("leanclass", "textures/gui/bar_full_5.png");
    public static final Identifier BAR_EMPTY_10 = new Identifier("leanclass", "textures/gui/bar_empty_10.png");
    public static final Identifier BAR_FULL_10 = new Identifier("leanclass", "textures/gui/bar_full_10.png");

    public static void onHudRender(MatrixStack matrixStack) {
        int baseX;
        int baseY;
        int barWidth = 90;
        int xOffset = 200;
        int yOffset = -15;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return;
        }
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        baseX = width / 2;
        baseY = height;

        List<Pair<Integer, Integer>> positions = Arrays.asList(
                new Pair<>(yOffset, -xOffset), // Position for the first skill on the left
                new Pair<>(yOffset - 15, -xOffset),  // Position for the second skill on the left
                new Pair<>(yOffset, xOffset - barWidth),  // Position for the first skill on the right
                new Pair<>(yOffset - 15, xOffset - barWidth)  // Position for the second skill on the right
        );

        assert MinecraftClient.getInstance() != null;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        BiMap<SkillSlot, ActiveSkill> activeSkills = PlayerClassManager.getClass(player).getActiveSkills();

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
                int charges = cooldownHelper.getCharges(player.getWorld().getTime(), component.getLastUsed(skillSlot), skillLevel);
                int maxCharges = cooldownHelper.getMaxCharges(skillLevel);
                int chargeTime = cooldownHelper.getChargeTime(skillLevel);
                float progress = (float) charges / maxCharges;
                int lastUsed = (int) component.getLastUsed(skillSlot);
                //LOGGER.warn("Skill slot " + skillSlot + " has " + charges + " charges out of " + maxCharges + " with progress " + progress);
                int color = activeSkill.getColor();

                // Remove the skill from the list if it's been too long since it was last used
                if ((player.world.getTime() - lastUsed) > (long) chargeTime * maxCharges + 100) {
                   continue;
                }

                RenderSystem.setShader(GameRenderer::getPositionTexProgram);

                int filledWidth = Math.round(progress * barWidth);
                if (maxCharges >= 18) {
                    drawBar(BAR_EMPTY, matrixStack, x, y, barWidth, barWidth, color);
                    drawBar(BAR_FULL, matrixStack, x, y, filledWidth, barWidth, color);
                } else if (maxCharges >= 5) {
                    drawBar(BAR_EMPTY_10, matrixStack, x, y, barWidth, barWidth, color);
                    drawBar(BAR_FULL_10, matrixStack, x, y, filledWidth, barWidth, color);
                } else {
                    drawBar(BAR_EMPTY_5, matrixStack, x, y, barWidth, barWidth, color);
                    drawBar(BAR_FULL_5, matrixStack, x, y, filledWidth, barWidth, color);
                }
                DrawableHelper.drawTextWithShadow(matrixStack, client.textRenderer, skillSlot.getName().substring(0, 1), x + barWidth/2 - 2, y - 5, color);

            }
        }
    }


    private static void drawBar(Identifier texture, MatrixStack matrixStack, int x, int y, int progress, int totalWidth, int color) {
        float alpha = ((color >> 24) & 0xFF) / 255.0F;
        float red = ((color >> 16) & 0xFF) / 255.0F;
        float green = ((color >> 8) & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;

        RenderSystem.setShaderColor(red, green, blue, alpha);
        RenderSystem.setShaderTexture(0, texture);
        DrawableHelper.drawTexture(matrixStack, x, y, 0, 0, progress, 5, totalWidth, 5);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F); // Reset color to white
    }
}

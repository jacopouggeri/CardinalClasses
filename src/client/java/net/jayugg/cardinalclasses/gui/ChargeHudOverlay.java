package net.jayugg.cardinalclasses.gui;

import com.google.common.collect.BiMap;
import com.mojang.blaze3d.systems.RenderSystem;
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

import java.util.HashMap;
import java.util.Map;

public class ChargeHudOverlay {
    public static final Identifier STAR_FRONT = new Identifier("cardinalclasses", "textures/gui/star_front.png");
    public static final Identifier STAR_BACK = new Identifier("cardinalclasses", "textures/gui/star_back.png");

    public static void onHudRender(MatrixStack matrixStack) {
        int baseX;
        int baseY;
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

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, STAR_BACK);
        DrawableHelper.drawTexture(matrixStack, baseX - 45, baseY - 80 - 45, 0, 0, 89, 89, 89, 89);

        BiMap<SkillSlot, ActiveSkill> activeSkills = playerClass.getActiveSkills();

        Map<SkillSlot, Float> percentages = new HashMap<>();
        Map<SkillSlot, Integer> colors = new HashMap<>();

        for (Map.Entry<SkillSlot, ActiveSkill> entry : activeSkills.entrySet()) {
            client.getProfiler().push("skillBar" + entry.getKey().getValue());
            SkillSlot skillSlot = entry.getKey();
            ActiveSkill activeSkill = entry.getValue();

            int skillLevel = PlayerClassManager.getSkillLevel(player, activeSkill);
            if (skillLevel > 0) {
                SkillCooldownHelper cooldownHelper = activeSkill.getCooldownHelper();
                ActiveSkillComponent component = ModComponents.ACTIVE_SKILLS_COMPONENT.get(player);
                int charges = cooldownHelper.getCharges(player.getWorld().getTime(), component.getLastUsed(skillSlot), skillLevel);
                int maxCharges = cooldownHelper.getMaxCharges(skillLevel);
                float progress = (float) charges / maxCharges;
                int color = activeSkill.getColor();

                percentages.put(skillSlot, progress);
                colors.put(skillSlot, color);
            }
        }

        SkillBarRenderer.renderSkillBars(matrixStack, baseX, baseY - 80, 21f, 7f, percentages, colors);
        //SkillBarRenderer.renderDirectionLetters(matrixStack, baseX, baseY - 80, 15, client.textRenderer, percentages);
        RenderSystem.setShaderTexture(0, STAR_FRONT);
        DrawableHelper.drawTexture(matrixStack, baseX - 45, baseY - 80 - 45, 0, 0, 89, 89, 89, 89);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
    }

}

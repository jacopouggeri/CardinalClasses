package net.jayugg.leanclass.event;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
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

import static net.jayugg.leanclass.LeanClass.LOGGER;

public class ChargeHudOverlay implements HudRenderCallback {
    public static final Identifier CHARGE_EMPTY_ICON = new Identifier("leanclass", "textures/gui/charge_empty_icon.png");
    public static final Identifier CHARGE_ICON = new Identifier("leanclass", "textures/gui/charge_icon.png");
    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {

        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            x = width / 2;
            y = height ;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CHARGE_EMPTY_ICON);
        for(int i = 0; i < 10; i++) {
            DrawableHelper.drawTexture(matrixStack,x - 94 + (i * 8),y - 54,0,0,10,10,
                    10,10);
        }

        RenderSystem.setShaderTexture(0, CHARGE_ICON);
        for (int i = 0; i < 10; i++) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            SkillCooldownHelper cooldownHelper = ((ActiveSkill) PlayerClassManager.getClass(player).getActiveSkills().get(SkillSlot.SOUTH)).getCooldownHelper();
            ActiveSkillComponent component = ModComponents.ACTIVE_SKILLS_COMPONENT.get(player);
            int charges = cooldownHelper.getCharges(player.getWorld().getTime(), component.getLastUsed(SkillSlot.SOUTH));
            if (charges > i) {
                DrawableHelper.drawTexture(matrixStack,x - 94 + (i * 8),y - 54,0,0,10,10,
                        10,10);
            } else {
                break;
            }
        }
    }
}

package net.jayugg.cardinalclasses.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jayugg.cardinalclasses.core.SkillSlot;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.Map;

public class SkillBarRenderer {


    public static void renderSkillBar(MatrixStack matrices, int centerX, int centerY, int radius, int thickness, float percentage, int color, int slot) {
        int startAngle = MathHelper.wrapDegrees(slot*90 - 45);
        int endAngle = startAngle + (int) (percentage * 90); // 90 degrees for a quarter circle

        // Fill the base bar with the desaturated color
        fillArc(matrices, centerX, centerY, radius+thickness, radius, startAngle, startAngle + 90, color, 0.2f);

        // Fill the actual bar with the original color
        fillArc(matrices, centerX, centerY, radius+thickness, radius, startAngle, endAngle, color, 0.8f);
    }

    public static void renderSkillBars(MatrixStack matrices, int centerX, int centerY, int radius, int thickness, Map<SkillSlot, Float> percentages, Map<SkillSlot, Integer> colors) {
        for (SkillSlot slot : SkillSlot.values()) {
            Float percentage = percentages.get(slot);
            Integer color = colors.get(slot);
            if (percentage != null && color != null) {
                renderSkillBar(matrices, centerX, centerY, radius, thickness, percentage, color, slot.ordinal());
            }
        }
    }

    public static void renderDirectionLetters(MatrixStack matrices, int centerX, int centerY, int radius, TextRenderer textRenderer, Map<SkillSlot, Float> percentages) {
        int offset = 16; // Adjust this value to move the letters further out
        for (SkillSlot slot : SkillSlot.values()) {
            Float percentage = percentages.get(slot);
            if (percentage != null) {
                // Draw the direction letters
                String direction = switch (slot) {
                    case NORTH -> "N";
                    case EAST -> "E";
                    case SOUTH -> "S";
                    case WEST -> "W";
                };
                int directionX = centerX + (int) ((radius + offset) * Math.cos(Math.toRadians(slot.ordinal() * 90 - 90))) - 2;
                int directionY = centerY + (int) ((radius + offset) * Math.sin(Math.toRadians(slot.ordinal() * 90 - 90))) - 2;
                textRenderer.draw(matrices, direction, directionX, directionY, 0xFFFFFF);
            }
        }
    }

    private static void fillArc(MatrixStack matrices, int centerX, int centerY, int outerRadius, int innerRadius, int startAngle, int endAngle, int color, float alpha) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        Matrix4f matrix = matrices.peek().getPositionMatrix();

        float[] hsbVals = Color.RGBtoHSB((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, null);
        float hue1 = hsbVals[0] - 0.02f; // Adjust these values to set the range of hues
        float hue2 = hsbVals[0] + 0.02f;

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

        for (int i = startAngle - 90; i <= endAngle - 90; i++) {
            double angle = Math.toRadians(i);
            float xOuter = (float) (Math.cos(angle) * outerRadius) + centerX;
            float yOuter = (float) (Math.sin(angle) * outerRadius) + centerY;
            float xInner = (float) (Math.cos(angle) * innerRadius) + centerX;
            float yInner = (float) (Math.sin(angle) * innerRadius) + centerY;

            // Calculate the ratio based on the current angle and the total angle of the bar
            float ratio = (float) i / 90;

            // Interpolate between the two hues based on the ratio
            float hue = (1 - ratio) * hue1 + ratio * hue2;

            // Convert the hue to RGB values
            int rgb = Color.HSBtoRGB(hue, hsbVals[1], hsbVals[2]);
            float interpolatedRed = ((rgb >> 16) & 0xFF) / 255.0F;
            float interpolatedGreen = ((rgb >> 8) & 0xFF) / 255.0F;
            float interpolatedBlue = (rgb & 0xFF) / 255.0F;

            bufferBuilder.vertex(matrix, xOuter, yOuter, 0).color(interpolatedRed, interpolatedGreen, interpolatedBlue, alpha).next();
            bufferBuilder.vertex(matrix, xInner, yInner, 0).color(interpolatedRed, interpolatedGreen, interpolatedBlue, alpha).next();
        }

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        Tessellator.getInstance().draw();
        RenderSystem.disableBlend();
    }
}
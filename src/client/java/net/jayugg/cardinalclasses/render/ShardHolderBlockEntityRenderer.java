package net.jayugg.cardinalclasses.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jayugg.cardinalclasses.block.ShardHolderBlock;
import net.jayugg.cardinalclasses.block.ShardHolderBlockEntity;
import net.jayugg.cardinalclasses.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class ShardHolderBlockEntityRenderer implements BlockEntityRenderer<ShardHolderBlockEntity> {

    public ShardHolderBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(ShardHolderBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        // Get the BlockState if not null
        if (blockEntity == null) {
            return;
        }
        BlockState state = blockEntity.getWorld().getBlockState(blockEntity.getPos());

        // Decide which item to render based on the BlockState
        if (!(state.getBlock() instanceof ShardHolderBlock)) {
            return;
        }

        ItemStack stack;
        if (state.get(ShardHolderBlock.HAS_SHARD)) {
            stack = new ItemStack(ModItems.SKILL_SHARD);
        } else {
            stack = new ItemStack(Items.AIR);
        }

        BakedModel model = MinecraftClient.getInstance().getItemRenderer().getModels().getModel(stack);
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        // Render the item
        matrices.push();

        double offset = 0.3f*Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
        matrices.translate(0.5, 0.9 + offset, 0.5); // Position the item above the block and make it bob up and down

        Quaternionf quaternion = new Quaternionf().rotateY((blockEntity.getWorld().getTime() + tickDelta) * 0.1f); // Create a quaternion for rotation
        matrices.multiply(quaternion); // Rotate the item

        float scaleFactor = 1f; // Define the scale factor. Change this value to scale the item.
        matrices.scale(scaleFactor, scaleFactor, scaleFactor); // Scale the item

        itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, false, matrices, vertexConsumers, light, overlay, model);
        matrices.pop();
    }
}
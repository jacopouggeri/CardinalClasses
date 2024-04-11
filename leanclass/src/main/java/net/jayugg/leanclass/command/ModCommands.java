package net.jayugg.leanclass.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.jayugg.leanclass.component.ModComponents;
import net.jayugg.leanclass.component.PlayerClassComponent;
import net.jayugg.leanclass.modules.*;
import net.jayugg.leanclass.registry.PlayerClassRegistry;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Collection;

import static net.jayugg.leanclass.LeanClass.MOD_ID;

public class ModCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> registerCommands(dispatcher));
    }

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal(MOD_ID + ":setskilllevel")
                .requires(source -> source.hasPermissionLevel(2)) // Require operator permission level
                .then(CommandManager.argument("target", EntityArgumentType.players())
                        .then(CommandManager.argument("skillNum", IntegerArgumentType.integer(1, 8))
                                .then(CommandManager.argument("skillLevel", IntegerArgumentType.integer(0, 3))
                        .executes(context -> {
                            ServerCommandSource source = context.getSource();
                            Collection<? extends Entity> targets = EntityArgumentType.getEntities(context, "target");
                            int skillNum = IntegerArgumentType.getInteger(context, "skillNum");
                            int skillLevel = IntegerArgumentType.getInteger(context, "skillLevel");
                            targets.stream()
                                    .filter(entity -> entity instanceof PlayerEntity)
                                    .map(entity -> (PlayerEntity) entity)
                                    .forEach((player -> {
                                        ModCommands.setSkill(player, skillNum, skillLevel);
                                        source.sendFeedback(Text.literal("Skill " +
                                                PlayerClassManager.getSkillInSlot(player, SkillSlot.fromValue(skillNum)).getId() +
                                                " to level " + skillLevel +
                                                " executed successfully for player " + player.getEntityName()), true);
                                    }));
                            return Command.SINGLE_SUCCESS;
                        })))));

        dispatcher.register(CommandManager.literal(MOD_ID + ":setascendedperk")
                .requires(source -> source.hasPermissionLevel(2)) // Require operator permission level
                .then(CommandManager.argument("target", EntityArgumentType.players())
                        .then(CommandManager.argument("perkId", IntegerArgumentType.integer(1, 2))
                        .executes(context -> {
                            ServerCommandSource source = context.getSource();
                            Collection<? extends Entity> targets = EntityArgumentType.getEntities(context, "target");
                            int perkId = IntegerArgumentType.getInteger(context, "perkId");
                            targets.stream()
                                    .filter(entity -> entity instanceof PlayerEntity)
                                    .map(entity -> (PlayerEntity) entity)
                                    .forEach(player -> ModCommands.setPerk(player, perkId));
                            source.sendFeedback(Text.literal("Perk ascension executed successfully for " + targets.size() + " player(s)."), true);
                            return Command.SINGLE_SUCCESS;
                        }))));

        dispatcher.register(CommandManager.literal(MOD_ID + ":setclass")
                .requires(source -> source.hasPermissionLevel(2)) // Require operator permission level
                .then(CommandManager.argument("target", EntityArgumentType.players())
                        .then(CommandManager.argument("className", StringArgumentType.string())
                                .suggests((context, builder) -> CommandSource.suggestMatching(PlayerClassRegistry.getClassIds(), builder))
                                .executes(context -> {
                                    ServerCommandSource source = context.getSource();
                                    Collection<? extends Entity> targets = EntityArgumentType.getEntities(context, "target");
                                    String className = StringArgumentType.getString(context, "className");
                                    targets.stream()
                                            .filter(entity -> entity instanceof PlayerEntity)
                                            .map(entity -> (PlayerEntity) entity)
                                            .forEach(player -> ModCommands.setClass(player, className));
                                    source.sendFeedback(Text.literal("Set class successfully for " + targets.size() + " player(s)."), true);
                                    return Command.SINGLE_SUCCESS;
                                }))));
    }

    private static void setPerk(PlayerEntity player, int perkId) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        playerClassComponent.setAscendedPerkCreative(PerkSlot.fromValue(perkId));
    }

    private static void setSkill(PlayerEntity player, int skillNum, int skillLevel) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        playerClassComponent.setSkillLevel(SkillSlot.fromValue(skillNum), skillLevel);
    }

    private static void setClass(PlayerEntity player, String className) {
        if (!PlayerClassRegistry.getClassIds().contains(className)) {
            return;
        }
        PlayerClassManager.setPlayerClass(player, className);
    }
}

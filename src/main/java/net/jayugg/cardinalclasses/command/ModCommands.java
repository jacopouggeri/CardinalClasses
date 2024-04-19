package net.jayugg.cardinalclasses.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.jayugg.cardinalclasses.advancement.ModCriteria;
import net.jayugg.cardinalclasses.core.AbilityType;
import net.jayugg.cardinalclasses.core.PerkSlot;
import net.jayugg.cardinalclasses.core.PlayerClass;
import net.jayugg.cardinalclasses.core.SkillSlot;
import net.jayugg.cardinalclasses.component.ModComponents;
import net.jayugg.cardinalclasses.component.PlayerClassComponent;
import net.jayugg.cardinalclasses.util.*;
import net.jayugg.cardinalclasses.registry.PlayerClassRegistry;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;

public class ModCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> registerCommands(dispatcher));
    }

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("skilllevel")
                .requires(source -> source.hasPermissionLevel(2)) // Require operator permission level
                .then(CommandManager.argument("target", EntityArgumentType.players())
                        .then(CommandManager.argument("abilityType", IntegerArgumentType.integer(1, 2))
                            .then(CommandManager.argument("skillSlot", IntegerArgumentType.integer(1, 4))
                                .then(CommandManager.argument("skillLevel", IntegerArgumentType.integer(0, 3))
                        .executes(context -> {
                            ServerCommandSource source = context.getSource();
                            Collection<? extends Entity> targets = EntityArgumentType.getEntities(context, "target");
                            int skillSlot = IntegerArgumentType.getInteger(context, "skillSlot");
                            int skillLevel = IntegerArgumentType.getInteger(context, "skillLevel");
                            SkillSlot slot = SkillSlot.fromValue(skillSlot);
                            AbilityType type = AbilityType.fromInt(IntegerArgumentType.getInteger(context, "abilityType"));
                            targets.stream()
                                    .filter(entity -> entity instanceof PlayerEntity)
                                    .map(entity -> (PlayerEntity) entity)
                                    .forEach((player -> {
                                        PlayerClass playerClass = PlayerClassManager.getClass(player);
                                        boolean setSkill = ModCommands.setSkill(player, playerClass, type, slot, skillLevel);
                                        if (setSkill) {
                                            source.sendFeedback(Text.literal(slot.getName() + " skill level set to " + skillLevel + " for player " + player.getEntityName()), true);
                                        } else {
                                            source.sendError(Text.literal("There was an error setting the " + slot.getName() + " skill level for player " + player.getEntityName()));
                                        }
                                    }));
                            return Command.SINGLE_SUCCESS;
                        }))))));

        dispatcher.register(CommandManager.literal("ascendperk")
                .requires(source -> source.hasPermissionLevel(2)) // Require operator permission level
                .then(CommandManager.argument("target", EntityArgumentType.players())
                        .then(CommandManager.argument("perkId", IntegerArgumentType.integer(1, 2))
                        .executes(context -> {
                            ServerCommandSource source = context.getSource();
                            Collection<? extends Entity> targets = EntityArgumentType.getEntities(context, "target");
                            int perkId = IntegerArgumentType.getInteger(context, "perkId");
                            PerkSlot slot = PerkSlot.fromInt(perkId);
                            targets.stream()
                                    .filter(entity -> entity instanceof PlayerEntity)
                                    .map(entity -> (PlayerEntity) entity)
                                    .forEach(player -> {
                                        PlayerClass playerClass = PlayerClassManager.getClass(player);
                                        boolean setPerk = ModCommands.setPerk(player, playerClass, slot);
                                        if (setPerk) {
                                            source.sendFeedback(Text.literal("Perk " + perkId + " ascended successfully for player " + player.getEntityName()), true);
                                        } else {
                                            source.sendError(Text.literal("There was an error ascending perk " + perkId + " for player " + player.getEntityName()));
                                        }
                                    });
                            return Command.SINGLE_SUCCESS;
                        }))));

        dispatcher.register(CommandManager.literal("setclass")
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
                                            .forEach(player -> {
                                                boolean setClass = ModCommands.setClass(player, className);
                                                if (setClass) {
                                                    source.sendFeedback(Text.literal("Class set to " + className + " for player " + player.getEntityName()), true);
                                                } else {
                                                    source.sendError(Text.literal("There was an error setting the class " + className + " for player " + player.getEntityName()));
                                                }
                                            });
                                    return Command.SINGLE_SUCCESS;
                                }))));
    }

    private static boolean setPerk(PlayerEntity player, PlayerClass playerClass, PerkSlot slot) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        boolean ascend = playerClassComponent.setAscendedPerk(slot);
        if (ascend) {
            ModComponents.CLASS_COMPONENT.sync(player);
            ModCriteria.ASCEND_PERK.trigger((ServerPlayerEntity) player, playerClass, playerClass.getPerks().get(slot), true);
        }
        return ascend;
    }

    private static boolean setSkill(PlayerEntity player, PlayerClass playerClass, AbilityType type, SkillSlot slot, int skillLevel) {
        PlayerClassComponent playerClassComponent = ModComponents.CLASS_COMPONENT.get(player);
        boolean setSkill = playerClassComponent.setSkillLevel(type, slot, skillLevel);
        if (setSkill) {
            ModComponents.CLASS_COMPONENT.sync(player);
            ModCriteria.OBTAIN_SKILL.trigger((ServerPlayerEntity) player, playerClass, playerClass.getSkills(type).get(slot), skillLevel);
        }
        return setSkill;
    }

    private static boolean setClass(PlayerEntity player, String className) {
        if (!PlayerClassRegistry.getClassIds().contains(className)) {
            return false;
        }
        PlayerClassManager.setPlayerClass(player, className);
        ModCriteria.OBTAIN_CLASS.trigger((ServerPlayerEntity) player, PlayerClassRegistry.getPlayerClass(className));
        ModComponents.CLASS_COMPONENT.sync(player);
        return true;
    }
}

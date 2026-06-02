package com.lay.betterbadges.command;

import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.league.Badge;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.registry.ModRegistries;
import com.lay.betterbadges.registry.RegistryKeys;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModCommands {

    private static final String LEAGUE = "league";
    private static final String BADGE = "badge";
    private static final String LEAGUE_ARG = "league_arg";
    private static final String BADGE_ARG = "badge_arg";

    public static void registerCommands(){
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("badges")
                    .requires(source -> source.hasPermission(2) && source.isPlayer())
                    .executes(ModCommands::giveAll)
                    .then(Commands.literal(BADGE)
                            .then(Commands.argument(BADGE_ARG, ItemArgument.item(registryAccess))
                                    .executes(ModCommands::giveSpecifiedBadge)))
                    .then(Commands.literal(LEAGUE)
                            .then(Commands.argument(LEAGUE_ARG, ResourceArgument.resource(registryAccess, RegistryKeys.LEAGUE))
                                    .executes(ModCommands::giveLeagueBadges))));
        });
    }

    private static void giveBadge(Player player, Item item){
        ItemStack stack = new ItemStack(item);
        stack.set(ModDataComponents.ITEM_OWNER, player.getStringUUID());
        player.getInventory().add(stack);
    }

    private static int giveAll(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayerOrException();
        for (League league : ModRegistries.LEAGUE) {
            for (Badge badge : league.getBadges()){
                giveBadge(player, badge.getItem());
            }
        }
        return 1;
    }

    private static int giveSpecifiedBadge(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayerOrException();
        Item item = ItemArgument.getItem(context, BADGE_ARG).getItem();
        giveBadge(player, item);
        return 1;
    }

    private static int giveLeagueBadges(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        League league = ModRegistries.LEAGUE.get(ResourceArgument.getResource(context, LEAGUE_ARG, RegistryKeys.LEAGUE).key());
        if(league == null || league == League.EMPTY) return 0;
        Player player = context.getSource().getPlayerOrException();
        for (Badge badge : league.getBadges()){
            giveBadge(player, badge.getItem());
        }
        return 1;
    }

}

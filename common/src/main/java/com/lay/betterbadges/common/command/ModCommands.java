package com.lay.betterbadges.common.command;

import com.lay.betterbadges.common.item.bounded.BoundItemWrapper;
import com.lay.betterbadges.common.api.league.BadgeSlot;
import com.lay.betterbadges.common.api.league.League;
import com.lay.betterbadges.common.registry.ModRegistries;
import com.lay.betterbadges.common.registry.ModRegistryKeys;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.architectury.event.events.common.CommandRegistrationEvent;
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
        CommandRegistrationEvent.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("badges")
                    .requires(source -> source.hasPermission(2) && source.isPlayer())
                    .executes(ModCommands::giveAll)
                    .then(Commands.literal(BADGE)
                            .then(Commands.argument(BADGE_ARG, ItemArgument.item(registryAccess))
                                    .executes(ModCommands::giveSpecifiedBadge)))
                    .then(Commands.literal(LEAGUE)
                            .then(Commands.argument(LEAGUE_ARG, ResourceArgument.resource(registryAccess, ModRegistryKeys.LEAGUE))
                                    .executes(ModCommands::giveLeagueBadges))));
        });
    }

    private static void giveBadge(Player player, Item item){
        BoundItemWrapper stack = new BoundItemWrapper(new ItemStack(item));
        stack.setItemOwner(player);
        player.getInventory().add(stack.getStack());
    }

    private static int giveAll(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayerOrException();
        for (League league : ModRegistries.LEAGUE) {
            for (BadgeSlot badgeSlot : league.getBadges()){
                giveBadge(player, badgeSlot.item());
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
        League league = ResourceArgument.getResource(context, LEAGUE_ARG, ModRegistryKeys.LEAGUE).value();
        if(league == null || league == League.EMPTY) return 0;
        Player player = context.getSource().getPlayerOrException();
        for (BadgeSlot badgeSlot : league.getBadges()){
            giveBadge(player, badgeSlot.item());
        }
        return 1;
    }

}

package com.lay.betterbadges.common.util;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerCooldown {

    private static final List<PlayerCooldown> cooldowns = new ArrayList<>();
    private static long tickCount = 0;

    public static void tickAll(){
        tickCount++;
        for (PlayerCooldown cooldown : cooldowns) cooldown.tick();
    }

    public static PlayerCooldown createAndRegister(){
        PlayerCooldown cooldown = new PlayerCooldown();
        cooldowns.add(cooldown);
        return cooldown;
    }

    private PlayerCooldown(){ }

    private final Map<Player, CooldownInformation> cooldown = new HashMap<>();

    public void tick(){
        this.cooldown.entrySet().removeIf(entry -> {
            CooldownInformation info = entry.getValue();
            if (info.ticks >= tickCount) return false;
            if (info.onRun != null) info.onRun.execute(entry.getKey());
            return true;
        });
    }

    public void add(Player player, long added){
        this.add(player, added, null);
    }

    public void add(Player player, long added, CooldownEnded onRun){
        this.cooldown.put(player, new CooldownInformation(tickCount + added, onRun));
    }

    public boolean hasPlayer(Player player){
        return this.cooldown.containsKey(player);
    }

    public record CooldownInformation(long ticks, @Nullable CooldownEnded onRun) { }

    public interface CooldownEnded{
        void execute(Player player);
    }
}

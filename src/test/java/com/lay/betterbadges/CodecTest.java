package com.lay.betterbadges;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodecTest {

    public static final int DEFAULT_SIZE = 8;

    public static final Codec<Map<ResourceLocation, List<String>>> mapCodec = Codec.unboundedMap(ResourceLocation.CODEC, Codec.STRING.listOf());

    @Test
    public void testRun(){

        List<String> string1 = new ArrayList<>();
        string1.add("hello1");
        string1.add("hello2");
        List<String> string2 = new ArrayList<>();
        string2.add("hi1");
        string2.add("hi2");
        DataResult<JsonElement> result = mapCodec.encodeStart(JsonOps.INSTANCE, Map.of(
                ResourceLocation.fromNamespaceAndPath("example", "number"), string1,
                ResourceLocation.fromNamespaceAndPath("example", "the_cooler_number"), string2
        ));

        JsonElement result2 = result.resultOrPartial().orElseThrow();

        // Now we'll deserialize the JsonElement back into a BlockPos
        DataResult<Map<ResourceLocation, List<String>>> result3 = mapCodec.parse(JsonOps.INSTANCE, result2);

        Map<ResourceLocation, List<String>> pos = result3.resultOrPartial().orElseThrow();

        BetterBadges.LOGGER.info("Deserialized BlockPos: {}", pos);

        BetterBadges.LOGGER.info(result.toString());


    }

    private int totalBadges = DEFAULT_SIZE;
    private HashMap<String, NonNullList<ItemStack>> badges;

    // Move this at the functioning part
//    public boolean addBadgeAt(Item badge, League league, int slot){
//        if(!(league.canInsertBadgeAtSlot(badge, slot))) return false;
//        if(badges.get(league.leagueName()).isEmpty()) return false;
//        return true;
//    }
}

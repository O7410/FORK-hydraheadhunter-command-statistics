package hydraheadhunter.commandstatistics.command.suggestionprovider;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class CustomStatsSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
     @Override
     public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
          ServerCommandSource source = context.getSource();

          // Add all Stats.CUSTOM stat ids to the suggestions.
          builder.suggest("minecraft:leave_game");
          builder.suggest("minecraft:play_time");
          builder.suggest("minecraft:total_world_time");
          builder.suggest("minecraft:time_since_death");
          builder.suggest("minecraft:time_since_rest");
          builder.suggest("minecraft:sneak_time");
          builder.suggest("minecraft:walk_one_cm");
          builder.suggest("minecraft:crouch_one_cm");
          builder.suggest("minecraft:sprint_one_cm");
          builder.suggest("minecraft:walk_on_water_one_cm");
          builder.suggest("minecraft:fall_one_cm");
          builder.suggest("minecraft:climb_one_cm");
          builder.suggest("minecraft:fly_one_cm");
          builder.suggest("minecraft:walk_under_water_one_cm");
          builder.suggest("minecraft:minecart_one_cm");
          builder.suggest("minecraft:boat_one_cm");
          builder.suggest("minecraft:pig_one_cm");
          builder.suggest("minecraft:horse_one_cm");
          builder.suggest("minecraft:aviate_one_cm");
          builder.suggest("minecraft:swim_one_cm");
          builder.suggest("minecraft:strider_one_cm");
          builder.suggest("minecraft:jump");
          builder.suggest("minecraft:drop");
          builder.suggest("minecraft:damage_dealt");
          builder.suggest("minecraft:damage_dealt_absorbed");
          builder.suggest("minecraft:damage_dealt_resisted");
          builder.suggest("minecraft:damage_taken");
          builder.suggest("minecraft:damage_blocked_by_shield");
          builder.suggest("minecraft:damage_absorbed");
          builder.suggest("minecraft:damage_resisted");
          builder.suggest("minecraft:deaths");
          builder.suggest("minecraft:mob_kills");
          builder.suggest("minecraft:animals_bred");
          builder.suggest("minecraft:player_kills");
          builder.suggest("minecraft:fish_caught");
          builder.suggest("minecraft:talked_to_villager");
          builder.suggest("minecraft:traded_with_villager");
          builder.suggest("minecraft:eat_cake_slice");
          builder.suggest("minecraft:fill_cauldron");
          builder.suggest("minecraft:use_cauldron");
          builder.suggest("minecraft:clean_armor");
          builder.suggest("minecraft:clean_banner");
          builder.suggest("minecraft:clean_shulker_box");
          builder.suggest("minecraft:interact_with_brewingstand");
          builder.suggest("minecraft:interact_with_beacon");
          builder.suggest("minecraft:inspect_dropper");
          builder.suggest("minecraft:inspect_hopper");
          builder.suggest("minecraft:inspect_dispenser");
          builder.suggest("minecraft:play_noteblock");
          builder.suggest("minecraft:tune_noteblock");
          builder.suggest("minecraft:pot_flower");
          builder.suggest("minecraft:trigger_trapped_chest");
          builder.suggest("minecraft:open_enderchest");
          builder.suggest("minecraft:enchant_item");
          builder.suggest("minecraft:play_record");
          builder.suggest("minecraft:interact_with_furnace");
          builder.suggest("minecraft:interact_with_crafting_table");
          builder.suggest("minecraft:open_chest");
          builder.suggest("minecraft:sleep_in_bed");
          builder.suggest("minecraft:open_shulker_box");
          builder.suggest("minecraft:open_barrel");
          builder.suggest("minecraft:interact_with_blast_furnace");
          builder.suggest("minecraft:interact_with_smoker");
          builder.suggest("minecraft:interact_with_lectern");
          builder.suggest("minecraft:interact_with_campfire");
          builder.suggest("minecraft:interact_with_cartography_table");
          builder.suggest("minecraft:interact_with_loom");
          builder.suggest("minecraft:interact_with_stonecutter");
          builder.suggest("minecraft:bell_ring");
          builder.suggest("minecraft:raid_trigger");
          builder.suggest("minecraft:raid_win");
          builder.suggest("minecraft:interact_with_anvil");
          builder.suggest("minecraft:interact_with_grindstone");
          builder.suggest("minecraft:target_hit");
          builder.suggest("minecraft:interact_with_smithing_table");





          // Lock the suggestions after we've modified them.
          return builder.buildFuture();
     }
}
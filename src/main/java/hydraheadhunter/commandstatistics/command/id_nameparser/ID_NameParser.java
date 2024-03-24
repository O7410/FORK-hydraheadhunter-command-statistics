package hydraheadhunter.commandstatistics.command.id_nameparser;

import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class ID_NameParser {

     public static Identifier parseIDName(String idName) throws NoSuchFieldException {
          return switch (idName) {
               case "minecraft:leave_game"                            -> Stats.LEAVE_GAME;
               case "minecraft:play_time"                             -> Stats.PLAY_TIME;
               case "minecraft:total_world_time"                      -> Stats.TOTAL_WORLD_TIME;
               case "minecraft:time_since_death"                      -> Stats.TIME_SINCE_DEATH;
               case "minecraft:time_since_rest"                       -> Stats.TIME_SINCE_REST;
               case "minecraft:sneak_time"                            -> Stats.SNEAK_TIME;
               case "minecraft:walk_one_cm"                           -> Stats.WALK_ONE_CM;
               case "minecraft:crouch_one_cm"                         -> Stats.CROUCH_ONE_CM;
               case "minecraft:sprint_one_cm"                         -> Stats.SPRINT_ONE_CM;
               case "minecraft:walk_on_water_one_cm"                  -> Stats.WALK_ON_WATER_ONE_CM;
               case "minecraft:fall_one_cm"                           -> Stats.FALL_ONE_CM;
               case "minecraft:climb_one_cm"                          -> Stats.CLIMB_ONE_CM;
               case "minecraft:fly_one_cm"                            -> Stats.FLY_ONE_CM;
               case "minecraft:walk_under_water_one_cm"               -> Stats.WALK_UNDER_WATER_ONE_CM;
               case "minecraft:minecart_one_cm"                       -> Stats.MINECART_ONE_CM;
               case "minecraft:boat_one_cm"                           -> Stats.BOAT_ONE_CM;
               case "minecraft:pig_one_cm"                            -> Stats.PIG_ONE_CM;
               case "minecraft:horse_one_cm"                          -> Stats.HORSE_ONE_CM;
               case "minecraft:aviate_one_cm"                         -> Stats.AVIATE_ONE_CM;
               case "minecraft:swim_one_cm"                           -> Stats.SWIM_ONE_CM;
               case "minecraft:strider_one_cm"                        -> Stats.STRIDER_ONE_CM;
               case "minecraft:jump"                                  -> Stats.JUMP;
               case "minecraft:drop"                                  -> Stats.DROP;
               case "minecraft:damage_dealt"                          -> Stats.DAMAGE_DEALT;
               case "minecraft:damage_dealt_absorbed"                 -> Stats.DAMAGE_DEALT_ABSORBED;
               case "minecraft:damage_dealt_resisted"                 -> Stats.DAMAGE_DEALT_RESISTED;
               case "minecraft:damage_taken"                          -> Stats.DAMAGE_TAKEN;
               case "minecraft:damage_blocked_by_shield"              -> Stats.DAMAGE_BLOCKED_BY_SHIELD;
               case "minecraft:damage_absorbed"                       -> Stats.DAMAGE_ABSORBED;
               case "minecraft:damage_resisted"                       -> Stats.DAMAGE_RESISTED;
               case "minecraft:deaths"                                -> Stats.DEATHS;
               case "minecraft:mob_kills"                             -> Stats.MOB_KILLS;
               case "minecraft:animals_bred"                          -> Stats.ANIMALS_BRED;
               case "minecraft:player_kills"                          -> Stats.PLAYER_KILLS;
               case "minecraft:fish_caught"                           -> Stats.FISH_CAUGHT;
               case "minecraft:talked_to_villager"                    -> Stats.TALKED_TO_VILLAGER;
               case "minecraft:traded_with_villager"                  -> Stats.TRADED_WITH_VILLAGER;
               case "minecraft:eat_cake_slice"                        -> Stats.EAT_CAKE_SLICE;
               case "minecraft:fill_cauldron"                         -> Stats.FILL_CAULDRON;
               case "minecraft:use_cauldron"                          -> Stats.USE_CAULDRON;
               case "minecraft:clean_armor"                           -> Stats.CLEAN_ARMOR;
               case "minecraft:clean_banner"                          -> Stats.CLEAN_BANNER;
               case "minecraft:clean_shulker_box"                     -> Stats.CLEAN_SHULKER_BOX;
               case "minecraft:interact_with_brewingstand"            -> Stats.INTERACT_WITH_BREWINGSTAND;
               case "minecraft:interact_with_beacon"                  -> Stats.INTERACT_WITH_BEACON;
               case "minecraft:inspect_dropper"                       -> Stats.INSPECT_DROPPER;
               case "minecraft:inspect_hopper"                        -> Stats.INSPECT_HOPPER;
               case "minecraft:inspect_dispenser"                     -> Stats.INSPECT_DISPENSER;
               case "minecraft:play_noteblock"                        -> Stats.PLAY_NOTEBLOCK;
               case "minecraft:tune_noteblock"                        -> Stats.TUNE_NOTEBLOCK;
               case "minecraft:pot_flower"                            -> Stats.POT_FLOWER;
               case "minecraft:trigger_trapped_chest"                 -> Stats.TRIGGER_TRAPPED_CHEST;
               case "minecraft:open_enderchest"                       -> Stats.OPEN_ENDERCHEST;
               case "minecraft:enchant_item"                          -> Stats.ENCHANT_ITEM;
               case "minecraft:play_record"                           -> Stats.PLAY_RECORD;
               case "minecraft:interact_with_furnace"                 -> Stats.INTERACT_WITH_FURNACE;
               case "minecraft:interact_with_crafting_table"          -> Stats.INTERACT_WITH_CRAFTING_TABLE;
               case "minecraft:open_chest"                            -> Stats.OPEN_CHEST;
               case "minecraft:sleep_in_bed"                          -> Stats.SLEEP_IN_BED;
               case "minecraft:open_shulker_box"                      -> Stats.OPEN_SHULKER_BOX;
               case "minecraft:open_barrel"                           -> Stats.OPEN_BARREL;
               case "minecraft:interact_with_blast_furnace"           -> Stats.INTERACT_WITH_BLAST_FURNACE;
               case "minecraft:interact_with_smoker"                  -> Stats.INTERACT_WITH_SMOKER;
               case "minecraft:interact_with_lectern"                 -> Stats.INTERACT_WITH_LECTERN;
               case "minecraft:interact_with_campfire"                -> Stats.INTERACT_WITH_CAMPFIRE;
               case "minecraft:interact_with_cartography_table"       -> Stats.INTERACT_WITH_CARTOGRAPHY_TABLE;
               case "minecraft:interact_with_loom"                    -> Stats.INTERACT_WITH_LOOM;
               case "minecraft:interact_with_stonecutter"             -> Stats.INTERACT_WITH_STONECUTTER;
               case "minecraft:bell_ring"                             -> Stats.BELL_RING;
               case "minecraft:raid_trigger"                          -> Stats.RAID_TRIGGER;
               case "minecraft:raid_win"                              -> Stats.RAID_WIN;
               case "minecraft:interact_with_anvil"                   -> Stats.INTERACT_WITH_ANVIL;
               case "minecraft:interact_with_grindstone"              -> Stats.INTERACT_WITH_GRINDSTONE;
               case "minecraft:target_hit"                            -> Stats.TARGET_HIT;
               case "minecraft:interact_with_smithing_table"          -> Stats.INTERACT_WITH_SMITHING_TABLE;
               default                                                -> throw new NoSuchFieldException();
          };
     }


}

package hydraheadhunter.commandstatistics.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import hydraheadhunter.commandstatistics.command.feedback.*;
import hydraheadhunter.commandstatistics.command.suggestionprovider.BreakableItemSuggestionProvider;
import hydraheadhunter.commandstatistics.command.suggestionprovider.CustomStatsSuggestionProvider;
import net.minecraft.block.Block;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.*;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Collection;

public class StatisticsCommand {
     private static final String STATISTICS            = "statistics" ;     private static final String TARGETS               = "targets"    ;

     private static final String QUERY                 = "query"      ;     private static final String RECORD                = "record"     ;
     private static final String ADD                   = "add"        ;     private static final String SET                   = "set"        ;
     private static final String REDUCE                = "reduce"     ;

     private static final String MINED                 = "mined"      ;     private static final String CRAFTED               = "crafted"    ;
     private static final String USED                  = "used"       ;     private static final String BROKEN                = "broken"     ;
     private static final String PICKED_UP             = "picked_up"  ;     private static final String DROPPED               = "dropped"    ;
     private static final String KILLED                = "killed"     ;     private static final String KILLED_BY             = "killed_by"  ;
     private static final String CUSTOM                = "custom"     ;

     private static final String STAT                  = "stat"       ;
     private static final String AMOUNT                = "amount"     ;     private static final String OBJECTIVE             = "objective"  ;

     //Execution OP 1 (Cannot change player statistics )
// /statistics query @p <statType<T (Block | Item | EntityType<?> | Identifier )>> <stat<T>>
     public static     void registerQUERY       (CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
          String executionMode = QUERY;
          int executionOP = 1;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(MINED)
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, BlockStateArgumentType.blockState(access))
                                                       .executes(                                                       context -> executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context, STAT).getBlockState().getBlock()                   //
                                                       )
                                                       )
                              )
                              )
                         )
                    )
               )
          )
          ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(CRAFTED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(USED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(BROKEN)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                            .suggests( new BreakableItemSuggestionProvider())
                                                       .executes(                                                       context -> executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(PICKED_UP)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(DROPPED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(KILLED)
                              .then(((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                  //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(KILLED_BY)
                              .then(((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                  //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(CUSTOM)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.CUSTOM_STAT))
                                                            .suggests( new CustomStatsSuggestionProvider() )
                                                       .executes(                                                       context -> executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getRegistryEntry(       context, STAT, RegistryKeys.CUSTOM_STAT).value()            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

    }

     private static <T> int  executeQUERY       (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified                    ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               int statValue = handler.getStat(statType, statSpecified);
               handler.save();

               QueryFeedback.provideFeedback(source, player, statType, statSpecified, statValue);

               toReturn +=statValue;
          }
          return toReturn;}

// /statistics record @p <statType<T (Block | Item | EntityType<?> | Identifier )>> <stat<T>> <objective>
     public static     void registerRECORD      (CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
          String executionMode = RECORD;
          int executionOP = 1;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(MINED)
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, BlockStateArgumentType.blockState(access)))
                                   .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                       .executes(                                                       context -> executeRECORD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context, STAT).getBlockState().getBlock()                   ,
                                                                      ScoreboardObjectiveArgumentType.getObjective(     context, OBJECTIVE)                                         //
                                                       )
                                                       )
                                   )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(CRAFTED)
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access)))
                                   .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                       .executes(                                                       context -> executeRECORD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      ScoreboardObjectiveArgumentType.getObjective(     context, OBJECTIVE)                                         //
                                                       )
                                                       )
                                   )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(USED)
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access)))
                                   .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                       .executes(                                                       context -> executeRECORD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      ScoreboardObjectiveArgumentType.getObjective(     context, OBJECTIVE)                                         //
                                                       )
                                                       )
                                   )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(BROKEN)
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access)))
                                   .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                            .suggests( new BreakableItemSuggestionProvider())
                                                       .executes(                                                       context -> executeRECORD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      ScoreboardObjectiveArgumentType.getObjective(     context, OBJECTIVE)                                         //
                                                       )
                                                       )
                                   )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(PICKED_UP)
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access)))
                                   .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                       .executes(                                                       context -> executeRECORD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      ScoreboardObjectiveArgumentType.getObjective(     context, OBJECTIVE)                                         //
                                                       )
                                                       )
                                   )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(DROPPED)
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access)))
                                   .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                       .executes(                                                       context -> executeRECORD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      ScoreboardObjectiveArgumentType.getObjective(     context, OBJECTIVE)                                         //
                                                       )
                                                       )
                                   )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(KILLED)
                              .then(((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE)))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                   .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                       .executes(                                                       context -> executeRECORD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                      ,
                                                                      ScoreboardObjectiveArgumentType.getObjective(     context, OBJECTIVE)                                         //
                                                       )
                                                       )
                                   )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(KILLED_BY)
                              .then(((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE)))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                   .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                       .executes(                                                       context -> executeRECORD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                      ,
                                                                      ScoreboardObjectiveArgumentType.getObjective(     context, OBJECTIVE)                                         //
                                                       )
                                                       )
                                   )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(CUSTOM)
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.CUSTOM_STAT)))
                                                            .suggests( new CustomStatsSuggestionProvider() )
                                   .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                       .executes(                                                       context -> executeRECORD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CUSTOM                                   /* - - - - - - - - - */                                        ,
                                                                      RegistryEntryArgumentType.getRegistryEntry(       context, STAT, RegistryKeys.CUSTOM_STAT).value()            ,
                                                                      ScoreboardObjectiveArgumentType.getObjective(     context, OBJECTIVE)                                         //
                                                       )
                                                       )
                                   )
                                   )
                              )
                         )
                    )
               )
          )
          ;
     }

     private static <T> int  executeRECORD      (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified, ScoreboardObjective objective ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               Scoreboard scoreboard = source.getServer().getScoreboard();
               handler.save();
                    int statValue = handler.getStat(statType, statSpecified);
                    scoreboard.getOrCreateScore(player, objective).setScore(statValue);
               handler.save();

               RecordFeedback.provideFeedback(source, player, statType, statSpecified, statValue, objective);

               toReturn +=statValue;
          }
          return toReturn;
     }

// /statistics add @p <statType<T (Block | Item | EntityType<?> | Identifier )>> <stat<T>> <amount (int) [default:1]>
     public static      void registerADD        (CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        String executionMode = ADD;
        int executionOP = 2;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(MINED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, BlockStateArgumentType.blockState(access))
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context, STAT).getBlockState().getBlock()                   //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context, STAT).getBlockState().getBlock()                   ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(CRAFTED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(USED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(BROKEN)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                            .suggests( new BreakableItemSuggestionProvider())
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(PICKED_UP)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(DROPPED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(KILLED)
                              .then(((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                      //
                                                       )
                                                       )
                                   )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                      ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                              )
                         )
                    )
               )
          )
          ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(KILLED_BY)
                              .then(((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                      //
                                                       )
                                                       )
                                   )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                      ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                              )
                         )
                    )
               )
          )
          ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(CUSTOM)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.CUSTOM_STAT))
                                                            .suggests( new CustomStatsSuggestionProvider() )
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getRegistryEntry(       context, STAT, RegistryKeys.CUSTOM_STAT).value()            //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getRegistryEntry(       context, STAT, RegistryKeys.CUSTOM_STAT).value()            ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

     }

     private static <T> int  executeADD         (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified                    ) throws CommandSyntaxException {
        return executeADD(source,targets,statType,statSpecified, 1);
     }
     private static <T> int  executeADD         (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified, int amount        ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
                    int statValue = handler.getStat(statType, statSpecified);
                    player.increaseStat( statType.getOrCreateStat(statSpecified), amount);
               handler.save();

               AddFeedback.provideFeedback(source, player, statType,statSpecified, statValue, amount);
               toReturn+=amount;

          }
          return toReturn;
     }

// /statistics set @p <statType<T (Block | Item | EntityType<?> | Identifier )>> <stat<T>> <amount (int) [default:0]>
     public static      void registerSET        (CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        String executionMode = SET;
        int executionOP = 3;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(MINED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, BlockStateArgumentType.blockState(access))
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context, STAT).getBlockState().getBlock()                   //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context, STAT).getBlockState().getBlock()                   ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(CRAFTED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(USED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(BROKEN)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                            .suggests( new BreakableItemSuggestionProvider())
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(PICKED_UP)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(DROPPED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(KILLED)
                              .then(((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                      //
                                                       )
                                                       )
                                   )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                      ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                              )
                         )
                    )
               )
          )
          ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(KILLED_BY)
                              .then(((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                      //
                                                       )
                                                       )
                                   )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                      ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                              )
                         )
                    )
               )
          )
          ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(CUSTOM)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.CUSTOM_STAT))
                                                            .suggests( new CustomStatsSuggestionProvider() )
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getRegistryEntry(       context, STAT, RegistryKeys.CUSTOM_STAT).value()            //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                       .executes(                                                       context -> executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getRegistryEntry(       context, STAT, RegistryKeys.CUSTOM_STAT).value()            ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

    }

     private static <T> int  executeSET         (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified                    ) throws CommandSyntaxException {
        return executeSET(source,targets,statType,statSpecified, 0);
    }
     private static <T> int  executeSET         (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified, int amount        ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
                    int statValue = handler.getStat(statType, statSpecified);
                    player.resetStat(statType.getOrCreateStat(statSpecified));
                         handler.save();
                    player.increaseStat(statType.getOrCreateStat(statSpecified), amount);
               handler.save();

               SetFeedback.provideFeedback(source, player, statType,statSpecified, statValue, amount);

               toReturn+=amount;
          }
          return toReturn;
     }

// /statistics set @p <statType<T (Block | Item | EntityType<?> | Identifier )>> <stat<T>> <amount (int) [default:1]
     public static      void registerREDUCE     (CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        String executionMode = REDUCE;
        int executionOP = 3;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(MINED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, BlockStateArgumentType.blockState(access))
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context, STAT).getBlockState().getBlock()                   //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context, STAT).getBlockState().getBlock()                   ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(CRAFTED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(USED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(BROKEN)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                            .suggests( new BreakableItemSuggestionProvider())
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(PICKED_UP)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(DROPPED)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, STAT).getItem()                                    ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(KILLED)
                              .then(((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                      //
                                                       )
                                                       )
                                   )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                      ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                              )
                         )
                    )
               )
          )
          ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                         .then( CommandManager.literal(KILLED_BY)
                              .then(((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                      //
                                                       )
                                                       )
                                   )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()                                      ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                              )
                         )
                    )
               )
          )
          ;

         dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP)))
              .then(CommandManager.literal(executionMode)
                   .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                        .then( CommandManager.literal(CUSTOM)
                             .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.CUSTOM_STAT))
                                                            .suggests( new CustomStatsSuggestionProvider() )
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getRegistryEntry(       context, STAT, RegistryKeys.CUSTOM_STAT).value()            //
                                                       )
                                                       )
                                  )
                                  .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, TARGETS)                                           ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getRegistryEntry(       context, STAT, RegistryKeys.CUSTOM_STAT).value()            ,
                                                                      IntegerArgumentType.getInteger(                   context, AMOUNT)                                            //
                                                       )
                                                       )
                                  )
                             )
                        )
                   )
              )
         )
         ;

    }

     private static <T> int  executeREDUCE      (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified                    ) throws CommandSyntaxException {
        return executeREDUCE(source,targets,statType,statSpecified, 1);
    }
     private static <T> int  executeREDUCE      (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified, int amount        ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
                    int statValue     = handler.getStat(statType, statSpecified);
                    int statValueNext = Math.max(0, statValue - amount);
                    player.resetStat(statType.getOrCreateStat(statSpecified));
                         handler.save();
                    player.increaseStat(statType.getOrCreateStat(statSpecified), statValueNext);
               handler.save();

               ReduceFeedback.provideFeedback(source, player, statType,statSpecified, statValue, amount, statValueNext);

               toReturn+=amount;
          }
          return toReturn;
     }


}

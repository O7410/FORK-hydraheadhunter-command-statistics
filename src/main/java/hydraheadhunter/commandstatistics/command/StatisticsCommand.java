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
import hydraheadhunter.commandstatistics.command.id_nameparser.ID_NameParser;
import hydraheadhunter.commandstatistics.command.suggestionprovider.BreakableItemSuggestionProvider;
import hydraheadhunter.commandstatistics.command.suggestionprovider.CustomStatsSuggestionProvider;
import net.minecraft.block.Block;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.*;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
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
     //Moderator OP 1
     //TODO change CUSTOM to accept identifiers as an argument
          //Be careful doing this as it WILl break Custom if the identifier in the argument isn't the exact argument that is registered in Stats.
     public static void registerQUERY    (CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
          String executionMode = "query";
          int executionOP = 1;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("mined")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("block", BlockStateArgumentType.blockState(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context, "block").getBlockState().getBlock()                //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context,"block").getBlockState().getBlock()                 ,
                                                                      BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("crafted")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("used")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("broken")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                            .suggests( new BreakableItemSuggestionProvider())
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("picked_up")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("dropped")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("killed")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("entity", RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  ,
                                                                      BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("killed_by")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("entity", RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  ,
                                                                      BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("custom")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("idName", StringArgumentType.string())
                                                            .suggests( new CustomStatsSuggestionProvider())
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      StringArgumentType.getString(             context, "idName")                                          //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      StringArgumentType.getString(                     context, "idName")                                          ,
                                                                      BoolArgumentType.getBool(                         context, "broadcast")                                       //
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
     //TODO Implement /statistics record @p statType stat scoreboard_objective
          //Is currently a functionally identical copy of query
     //Which will take a statistic from a player and record it into the source's scoreboard_objective if possible.
     public static void registerRECORD   (CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
          String executionMode = "record";
          int executionOP = 1;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("mined")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("block", BlockStateArgumentType.blockState(access))
                                        .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                  (ServerCommandSource)                             context.getSource()                                         ,
                                                  EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                  Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                  BlockStateArgumentType.getBlockState(             context, "block").getBlockState().getBlock()                //
                                             )
                                        )
                                   )
                                        .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                             .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                       (ServerCommandSource)                             context.getSource()                                         ,
                                                       EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                       Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                       BlockStateArgumentType.getBlockState(             context,"block").getBlockState().getBlock()                 ,
                                                       BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                  )
                                             )
                                        )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("crafted")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                        .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                  (ServerCommandSource)                             context.getSource()                                         ,
                                                  EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                  Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                  ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                             )
                                        )
                                   )
                                        .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                             .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                       (ServerCommandSource)                             context.getSource()                                         ,
                                                       EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                       Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                       ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                       BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                  )
                                             )
                                        )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("used")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                        .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                  (ServerCommandSource)                             context.getSource()                                         ,
                                                  EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                  Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                  ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                             )
                                        )
                                   )
                                        .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                             .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                       (ServerCommandSource)                             context.getSource()                                         ,
                                                       EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                       Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                       ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                       BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                  )
                                             )
                                        )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("broken")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                        .suggests( new BreakableItemSuggestionProvider())
                                        .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                  (ServerCommandSource)                             context.getSource()                                         ,
                                                  EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                  Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                  ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                             )
                                        )
                                   )
                                        .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                             .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                       (ServerCommandSource)                             context.getSource()                                         ,
                                                       EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                       Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                       ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                       BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                  )
                                             )
                                        )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("picked_up")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                        .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                  (ServerCommandSource)                             context.getSource()                                         ,
                                                  EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                  Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                  ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                             )
                                        )
                                   )
                                        .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                             .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                       (ServerCommandSource)                             context.getSource()                                         ,
                                                       EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                       Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                       ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                       BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                  )
                                             )
                                        )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("dropped")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                        .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                  (ServerCommandSource)                             context.getSource()                                         ,
                                                  EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                  Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                  ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                             )
                                        )
                                   )
                                        .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                             .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                       (ServerCommandSource)                             context.getSource()                                         ,
                                                       EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                       Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                       ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                       BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                  )
                                             )
                                        )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("killed")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("entity", RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                        .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                        .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                  (ServerCommandSource)                             context.getSource()                                         ,
                                                  EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                  Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                  RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  //
                                             )
                                        )
                                   )
                                        .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                             .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                       (ServerCommandSource)                             context.getSource()                                         ,
                                                       EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                       Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                       RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  ,
                                                       BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                  )
                                             )
                                        )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("killed_by")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("entity", RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                        .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                        .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                  (ServerCommandSource)                             context.getSource()                                         ,
                                                  EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                  Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                  RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  //
                                             )
                                        )
                                   )
                                        .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                             .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                       (ServerCommandSource)                             context.getSource()                                         ,
                                                       EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                       Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                       RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  ,
                                                       BoolArgumentType.getBool(                         context, "broadcast")                                       //
                                                  )
                                             )
                                        )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("custom")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("idName", StringArgumentType.string())
                                        .suggests( new CustomStatsSuggestionProvider())
                                        .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                  (ServerCommandSource)                             context.getSource()                                         ,
                                                  EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                  Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                  StringArgumentType.getString(             context, "idName")                                          //
                                             )
                                        )
                                   )
                                        .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                             .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                       (ServerCommandSource)                             context.getSource()                                         ,
                                                       EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                       Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                       StringArgumentType.getString(                     context, "idName")                                          ,
                                                       BoolArgumentType.getBool(                         context, "broadcast")                                       //
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


     //Gamesmaster OP 2
     //TODO add optional broadcast boolean
     //TODO add broadcast feedback with classes.
     //TODO add scoreboard functionality (allow the int argument to be a scoreboard objective's value)
     public static void registerADD      (CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
          String executionMode = "add";
          int executionOP = 2;
          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("mined")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("block", BlockStateArgumentType.blockState(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context,"block").getBlockState().getBlock()                 //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context,"block").getBlockState().getBlock()                 ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("crafted")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;
          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("used")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.USED                                         /* - - - - - - - - - */                                    ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("broken")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                            .suggests( new BreakableItemSuggestionProvider())
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("picked_up")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("dropped")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("killed")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("entity", RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("killed_by")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("entity", RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("custom")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("idName", StringArgumentType.string())
                                                            .suggests( new CustomStatsSuggestionProvider())
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      StringArgumentType.getString(                     context, "idName")                                              //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      StringArgumentType.getString(                     context, "id")                                              ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
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
     //Adminstrator OP 3
     //TODO add optional broadcast boolean
     //TODO add          broadcast feedback in execute on boolean argument.
     //TODO add scoreboard functionality (allow the int argument to be a scoreboard objective's value)
     public static void registerSET      (CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
          String executionMode = "set";
          int executionOP = 3 ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("mined")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("block", BlockStateArgumentType.blockState(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context,"block").getBlockState().getBlock()                 //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context,"block").getBlockState().getBlock()                 ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("crafted")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;
          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("used")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.USED                                         /* - - - - - - - - - */                                    ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("broken")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                            .suggests( new BreakableItemSuggestionProvider())
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("picked_up")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("dropped")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("killed")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("entity", RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("killed_by")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("entity", RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("custom")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("idName", StringArgumentType.string())
                                                            .suggests( new CustomStatsSuggestionProvider())
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      StringArgumentType.getString(                     context, "idName")                                              //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      StringArgumentType.getString(                     context, "idName")                                              ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
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
     //TODO add optional broadcast boolean
     //TODO add          broadcast feedback in execute on boolean argument.
     //TODO add scoreboard functionality (allow the int argument to be a scoreboard objective's value)
     public static void registerREDUCE   (CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
          String executionMode = "reduce";
          int executionOP = 3;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("mined")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("block", BlockStateArgumentType.blockState(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context,"block").getBlockState().getBlock()                 //
                                                       )
                                                       )
                                        )
                                        .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.MINED                                       /* - - - - - - - - - */                                     ,
                                                                      BlockStateArgumentType.getBlockState(             context,"block").getBlockState().getBlock()                 ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                        )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("crafted")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CRAFTED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;
          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("used")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.USED                                         /* - - - - - - - - - */                                    ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.USED                                        /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("broken")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                            .suggests( new BreakableItemSuggestionProvider())
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.BROKEN                                      /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("picked_up")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.PICKED_UP                                   /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("dropped")
                              .then((ArgumentBuilder<ServerCommandSource, ?>)((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                            .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.DROPPED                                     /* - - - - - - - - - */                                     ,
                                                                      ItemStackArgumentType.getItemStackArgument(       context, "item").getItem().asItem()                         ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                            )
                                                            )
                                    )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("killed")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("entity", RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                            .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED                                      /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("killed_by")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("entity", RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                             .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.KILLED_BY                                   /* - - - - - - - - - */                                     ,
                                                                      RegistryEntryArgumentType.getSummonableEntityType(context, "entity").value()                                  ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
                                                       )
                                                       )
                                   )
                              )
                         )
                    )
               )
          )
          ;

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(executionOP)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("custom")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("idName", StringArgumentType.string())
                                                            .suggests( new CustomStatsSuggestionProvider())
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      StringArgumentType.getString(             context, "idName")                                              //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      StringArgumentType.getString(                     context, "idName")                                              ,
                                                                      IntegerArgumentType.getInteger(                   context, "amount")                                          //
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

     // statistics query @p [statType] [Stat] (false)
     private static int executeQUERY    (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Block>         statType, Block      block                     ) throws CommandSyntaxException {
          return executeQUERY(source,targets,statType,block ,false);
     }
     private static int executeQUERY    (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Item>          statType, Item       item                      ) throws CommandSyntaxException {
          return executeQUERY(source,targets,statType,item  ,false);
     }
     private static int executeQUERY    (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<EntityType<?>> statType, EntityType entity                    ) throws CommandSyntaxException {
          return executeQUERY(source,targets,statType,entity,false);
     }
     private static int executeQUERY    (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, String idName                        ) throws CommandSyntaxException {
          return executeQUERY(source,targets,statType,idName,false);
     }

     // statistics query @p [statType] [Stat] [boolean]
     private static int executeQUERY    (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Block>         statType, Block      block , boolean broadcast ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               int statQueried = handler.getStat(statType, block);

               if (broadcast) QueryBlockFeedback.provideBlockFeedback(source, player, statType, block, statQueried);


               toReturn +=statQueried;
          }
          return toReturn;
     }
     private static int executeQUERY    (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Item>          statType, Item       item  , boolean broadcast ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               int statQueried = handler.getStat(statType, item);

               if (broadcast) QueryItemFeedback.provideBlockFeedback(source,player,statType,item,statQueried);

               toReturn +=statQueried;
          }
          return toReturn;
     }
     private static int executeQUERY    (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<EntityType<?>> statType, EntityType entity, boolean broadcast ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               int statQueried = handler.getStat(statType, entity);

               if (broadcast) QueryEntityFeedback.provideBlockFeedback(source,player,statType,entity,statQueried);

               toReturn +=statQueried;
          }
          return toReturn;
     }
     private static int executeQUERY    (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, String idName    , boolean broadcast ) throws CommandSyntaxException {
          Identifier id; int toReturn=0;
          try                            { id  =  ID_NameParser.parseIDName(idName);                       }
          catch (NoSuchFieldException e) { return UnsupportedCustomStatistic.giveFeedback(source, idName); }


          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               int statQueried = handler.getStat(statType, id);

               if (broadcast) QueryCustomFeedback.provideCustomFeedback(source, player, idName, statQueried);

               toReturn +=statQueried;
          }
          return toReturn;
     }

     // statistics add @p [statType] [Stat] (1)
     private static int executeADD     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Block>         statType, Block      block                      ) throws CommandSyntaxException {
          return executeADD(source, targets, statType, block , 1);
     }
     private static int executeADD     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Item>          statType, Item       item                       ) throws CommandSyntaxException {
          return executeADD(source, targets, statType, item  , 1);
     }
     private static int executeADD     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<EntityType<?>> statType, EntityType entity                     ) throws CommandSyntaxException {
          return executeADD(source, targets, statType, entity, 1);
     }
     private static int executeADD     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, String     idName                     ) throws CommandSyntaxException {
          return executeADD(source, targets, statType, idName    , 1);
     }

     // statistics add @p [statType] [Stat] <amount>
     private static int executeADD     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Block>         statType, Block      block,  int amount         ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               player.increaseStat( statType.getOrCreateStat(block), amount);
               handler.save();

               toReturn+=amount;
          }
          return toReturn;
     }
     private static int executeADD     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Item>          statType, Item       item,   int amount         ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               player.increaseStat( statType.getOrCreateStat(item), amount);
               handler.save();

               toReturn+=amount;
          }
          return toReturn;
     }
     private static int executeADD     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<EntityType<?>> statType, EntityType entity, int amount         ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               player.increaseStat( statType.getOrCreateStat(entity), amount);
               handler.save();

               toReturn+=amount;
          }
          return toReturn;
     }
     private static int executeADD     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, String     idName, int amount         ) throws CommandSyntaxException {
          Identifier id; int toReturn=0;
          try {id = ID_NameParser.parseIDName(idName); }
          catch (NoSuchFieldException e) { UnsupportedCustomStatistic.giveFeedback(source, idName); return -1; }

          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               player.increaseStat( statType.getOrCreateStat(id), amount);
               handler.save();

               toReturn+=amount;
          }
          return toReturn;
     }

     // statistics add @p [statType] [Stat] (0)
     private static int executeSET     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Block>         statType, Block      block                      ) throws CommandSyntaxException {
          return executeSET(source, targets, statType, block , 0);
     }
     private static int executeSET     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Item>          statType, Item       item                       ) throws CommandSyntaxException {
          return executeSET(source, targets, statType, item  , 0);
     }
     private static int executeSET     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<EntityType<?>> statType, EntityType entity                     ) throws CommandSyntaxException {
          return executeSET(source, targets, statType, entity, 0);
     }
     private static int executeSET     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, String     idName                     ) throws CommandSyntaxException {
          return executeSET(source, targets, statType, idName    , 0);
     }

     // statistics add @p [statType] [Stat] <amount>
     private static int executeSET     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Block>         statType, Block      block,  int amount         ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               player.resetStat(statType.getOrCreateStat(block));
               handler.save();
               player.increaseStat(statType.getOrCreateStat(block), amount);
               handler.save();

               toReturn+=amount;
          }
          return toReturn;
     }
     private static int executeSET     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Item>          statType, Item       item,   int amount         ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               player.resetStat(statType.getOrCreateStat(item));
               handler.save();
               player.increaseStat(statType.getOrCreateStat(item), amount);
               handler.save();

               toReturn+=amount;
          }
          return toReturn;
     }
     private static int executeSET     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<EntityType<?>> statType, EntityType entity, int amount         ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               player.resetStat(statType.getOrCreateStat(entity));
               handler.save();
               player.increaseStat(statType.getOrCreateStat(entity), amount);
               handler.save();

               toReturn+=amount;
          }
          return toReturn;
     }
     private static int executeSET     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, String     idName, int amount         ) throws CommandSyntaxException {
          Identifier id; int toReturn=0;
          try {id = ID_NameParser.parseIDName(idName); }
          catch (NoSuchFieldException e) { UnsupportedCustomStatistic.giveFeedback(source, idName); return -1; }

          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               player.resetStat(statType.getOrCreateStat(id));
               handler.save();
               player.increaseStat(statType.getOrCreateStat(id), amount);
               handler.save();

               toReturn+=amount;
          }
          return toReturn;
     }

     // statistics remove @p [statType] [Stat] (1)
     private static int executeREDUCE  (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Block>         statType, Block      block                      ) throws CommandSyntaxException {
          return executeREDUCE(source, targets, statType, block , 1);
     }
     private static int executeREDUCE  (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Item>          statType, Item       item                       ) throws CommandSyntaxException {
          return executeREDUCE(source, targets, statType, item  , 1);
     }
     private static int executeREDUCE  (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<EntityType<?>> statType, EntityType entity                     ) throws CommandSyntaxException {
          return executeREDUCE(source, targets, statType, entity, 1);
     }
     private static int executeREDUCE  (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, String     idName                     ) throws CommandSyntaxException {
          return executeREDUCE(source, targets, statType, idName    , 1);
     }

     // statistics remove @p [statType] [Stat] <amount>
     private static int executeREDUCE  (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Block>         statType, Block      block , int amount         ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               int currentStat = executeQUERY(source, targets, statType, block);
               int targetStat  = Math.max( 0, currentStat - amount);
               executeSET(source,targets, statType, block, targetStat);
               handler.save();

               toReturn += amount;
          }
          return toReturn;
     }
     private static int executeREDUCE  (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Item>          statType, Item       item  , int amount         ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               int currentStat = executeQUERY(source, targets, statType, item);
               int targetStat  = Math.max( 0, currentStat - amount);
               executeSET(source,targets, statType, item, targetStat);
               handler.save();

               toReturn += amount;
          }
          return toReturn;
     }
     private static int executeREDUCE  (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<EntityType<?>> statType, EntityType entity, int amount         ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               int currentStat = executeQUERY(source, targets, statType, entity);
               int targetStat  = Math.max( 0, currentStat - amount);
               executeSET(source,targets, statType, entity, targetStat);
               handler.save();

               toReturn += amount;
          }
          return toReturn;
     }
     private static int executeREDUCE  (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, String     idName, int amount         ) throws CommandSyntaxException {
          Identifier id; int toReturn=0;
          try {id = ID_NameParser.parseIDName(idName); }
          catch (NoSuchFieldException e) { UnsupportedCustomStatistic.giveFeedback(source, idName); return -1; }

          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               int currentStat = executeQUERY(source, targets, statType, idName);
               int targetStat  = Math.max( 0, currentStat - amount);
               executeSET(source,targets, statType, idName, targetStat);
               handler.save();

               toReturn += amount;
          }
          return toReturn;
     }





}

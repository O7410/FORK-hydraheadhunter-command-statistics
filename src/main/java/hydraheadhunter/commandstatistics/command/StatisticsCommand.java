package hydraheadhunter.commandstatistics.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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

     public static void registerQUERY(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
          String executionMode = "query";

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("custom")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("id", IdentifierArgumentType.identifier())
                                                            .suggests( new CustomStatsSuggestionProvider())
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      IdentifierArgumentType.getIdentifier(             context, "id")                                              //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("broadcast", BoolArgumentType.bool())
                                                       .executes(                                                       context -> StatisticsCommand.executeQUERY(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      IdentifierArgumentType.getIdentifier(             context, "id")                                              ,
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
     public static void registerADD(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
          String executionMode = "add";

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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
          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("custom")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("id", IdentifierArgumentType.identifier())
                                                            .suggests( new CustomStatsSuggestionProvider())
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      IdentifierArgumentType.getIdentifier(             context, "id")                                              //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeADD(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      IdentifierArgumentType.getIdentifier(             context, "id")                                              ,
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
     public static void registerSET(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
          String executionMode = "set";

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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
          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("custom")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("id", IdentifierArgumentType.identifier())
                                                            .suggests( new CustomStatsSuggestionProvider())
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      IdentifierArgumentType.getIdentifier(             context, "id")                                              //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeSET(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      IdentifierArgumentType.getIdentifier(             context, "id")                                              ,
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

     public static void registerREDUCE(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
          String executionMode = "reduce";

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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
          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
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

          dispatcher.register( (LiteralArgumentBuilder)(  (LiteralArgumentBuilder) CommandManager.literal("statistics").requires(source -> source.hasPermissionLevel(1)))
               .then(CommandManager.literal(executionMode)
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                         .then( CommandManager.literal("custom")
                              .then(((RequiredArgumentBuilder)CommandManager.argument("id", IdentifierArgumentType.identifier())
                                                            .suggests( new CustomStatsSuggestionProvider())
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      IdentifierArgumentType.getIdentifier(             context, "id")                                              //
                                                       )
                                                       )
                                   )
                                   .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                       .executes(                                                       context -> StatisticsCommand.executeREDUCE(
                                                                      (ServerCommandSource)                             context.getSource()                                         ,
                                                                      EntityArgumentType.getPlayers(                    context, "targets")                                         ,
                                                                      Stats.CUSTOM                                      /* - - - - - - - - - */                                     ,
                                                                      IdentifierArgumentType.getIdentifier(             context, "id")                                              ,
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
     private static int executeQUERY(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Block>         statType, Block      block                         ) throws CommandSyntaxException {
          return executeQUERY(source,targets,statType,block ,false);
     }
     private static int executeQUERY(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Item>          statType, Item       item                          ) throws CommandSyntaxException {
          return executeQUERY(source,targets,statType,item  ,false);
     }
     private static int executeQUERY(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<EntityType<?>> statType, EntityType entity                        ) throws CommandSyntaxException {
          return executeQUERY(source,targets,statType,entity,false);
     }
     private static int executeQUERY(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, Identifier id                            ) throws CommandSyntaxException {
          return executeQUERY(source,targets,statType,id    ,false);
     }

     // statistics query @p [statType] [Stat] [boolean]
     private static int executeQUERY(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Block>         statType, Block      block , boolean broadcast     ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               int statQueried = handler.getStat(statType, block);

               if (broadcast)                          source.sendFeedback(() -> Text.of("You have " + statType.getName() +" "+ block.getName() +" "+ statQueried + " times."),true);

               toReturn +=statQueried;
          }
          return toReturn;
     }
     private static int executeQUERY(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Item>          statType, Item       item  , boolean broadcast     ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               int statQueried = handler.getStat(statType, item);

               if(broadcast)                           source.sendFeedback(() -> Text.of("You have " + statType.getName() +" "+ item.getName() +" "+ statQueried + " times."),true);

               toReturn +=statQueried;
          }
          return toReturn;
     }
     private static int executeQUERY(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<EntityType<?>> statType, EntityType entity, boolean broadcast     ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               int statQueried = handler.getStat(statType, entity);

               if(broadcast)                           source.sendFeedback(() -> Text.of("You have " + statType.getName() +" "+ entity.getName() +" "+ statQueried + " times."),true);

               toReturn +=statQueried;
          }
          return toReturn;
     }
     private static int executeQUERY(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, Identifier id    , boolean broadcast     ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               int statQueried = handler.getStat(statType, id);

               if(broadcast) {
                                                       source.sendFeedback(() -> Text.of("debug" + id.toString() + ": " + statQueried), true);
                                                       source.sendFeedback(() -> Text.of("You have " + statType.getName() + " " + id.toTranslationKey() + " " + statQueried + " times."), true);
               }

               toReturn +=statQueried;
          }
          return toReturn;
     }

     // statistics add @p [statType] [Stat] (1)
     private static int executeADD     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Block>         statType, Block block                           ) throws CommandSyntaxException {
          return executeADD(source, targets, statType, block , 1);
     }
     private static int executeADD     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Item>          statType, Item item                             ) throws CommandSyntaxException {
          return executeADD(source, targets, statType, item  , 1);
     }
     private static int executeADD     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<EntityType<?>> statType, EntityType entity                     ) throws CommandSyntaxException {
          return executeADD(source, targets, statType, entity, 1);
     }
     private static int executeADD     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, Identifier id                         ) throws CommandSyntaxException {
          return executeADD(source, targets, statType, id    , 1);
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
     private static int executeADD     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, Identifier id,     int amount         ) throws CommandSyntaxException {
          int toReturn=0;
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
     private static int executeSET     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Block>         statType, Block block                           ) throws CommandSyntaxException {
          return executeSET(source, targets, statType, block , 0);
     }
     private static int executeSET     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Item>          statType, Item item                             ) throws CommandSyntaxException {
          return executeSET(source, targets, statType, item  , 0);
     }
     private static int executeSET     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<EntityType<?>> statType, EntityType entity                     ) throws CommandSyntaxException {
          return executeSET(source, targets, statType, entity, 0);
     }
     private static int executeSET     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, Identifier id                         ) throws CommandSyntaxException {
          return executeSET(source, targets, statType, id    , 0);
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
     private static int executeSET     (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, Identifier id,     int amount         ) throws CommandSyntaxException {
          int toReturn=0;
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
     private static int executeREDUCE  (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Block>         statType, Block block                           ) throws CommandSyntaxException {
          return executeREDUCE(source, targets, statType, block , 1);
     }
     private static int executeREDUCE  (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Item>          statType, Item item                             ) throws CommandSyntaxException {
          return executeREDUCE(source, targets, statType, item  , 1);
     }
     private static int executeREDUCE  (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<EntityType<?>> statType, EntityType entity                     ) throws CommandSyntaxException {
          return executeREDUCE(source, targets, statType, entity, 1);
     }
     private static int executeREDUCE  (ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, Identifier id                         ) throws CommandSyntaxException {
          return executeREDUCE(source, targets, statType, id    , 1);
     }

     // statistics remove @p [statType] [Stat] <amount>
     private static int executeREDUCE(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Block>         statType, Block      block, int amount         ) throws CommandSyntaxException {
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
     private static int executeREDUCE(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Item>          statType, Item       item, int amount         ) throws CommandSyntaxException {
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
     private static int executeREDUCE(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<EntityType<?>> statType, EntityType entity, int amount         ) throws CommandSyntaxException {
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
     private static int executeREDUCE(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<Identifier>    statType, Identifier id, int amount         ) throws CommandSyntaxException {
          int toReturn=0;
          for (ServerPlayerEntity player : targets) {
               ServerStatHandler handler = player.getStatHandler();
               handler.save();
               int currentStat = executeQUERY(source, targets, statType, id);
               int targetStat  = Math.max( 0, currentStat - amount);
               executeSET(source,targets, statType, id, targetStat);
               handler.save();

               toReturn += amount;
          }
          return toReturn;
     }




}

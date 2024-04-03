package hydraheadhunter.commandstatistics.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import hydraheadhunter.commandstatistics.command.argument.BlockArgumentType;
import hydraheadhunter.commandstatistics.command.argument.ItemArgumentType;
import hydraheadhunter.commandstatistics.command.feedback.*;
import hydraheadhunter.commandstatistics.command.suggestionprovider.CustomStatsSuggestionProvider;
import hydraheadhunter.commandstatistics.command.suggestionprovider.ModSuggestionProviders;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.RegistryEntryArgumentType;
import net.minecraft.command.argument.ScoreboardObjectiveArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;

import java.util.Collection;

public class StatisticsCommand {
    private static final String STATISTICS = "statistics";
    private static final String TARGETS = "targets";

    private static final String QUERY = "query";
    private static final String STORE = "store";
    private static final String ADD = "add";
    private static final String SET = "set";
    private static final String REDUCE = "reduce";

    private static final String MINED = "mined";
    private static final String CRAFTED = "crafted";
    private static final String USED = "used";
    private static final String BROKEN = "broken";
    private static final String PICKED_UP = "picked_up";
    private static final String DROPPED = "dropped";
    private static final String KILLED = "killed";
    private static final String KILLED_BY = "killed_by";
    private static final String CUSTOM = "custom";

    private static final String STAT = "stat";
    private static final String AMOUNT = "amount";
    private static final String OBJECTIVE = "objective";

    //Execution OP 1 (Cannot change player statistics )
// /statistics query @p <statType<T (Block | Item | EntityType<?> | Identifier )>> <stat<T>>
    public static void registerQuery(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        String executionMode = QUERY;
        int executionOP = 1;

        dispatcher.register(CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP))
                .then(CommandManager.literal(executionMode)
                        .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                                .then(CommandManager.literal(MINED)
                                        .then(CommandManager.argument(STAT, BlockArgumentType.block(access))
                                                .executes(context -> executeQuery(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.MINED,
                                                                BlockArgumentType.getBlock(context, STAT).block()
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CRAFTED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeQuery(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.CRAFTED,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(USED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeQuery(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.USED,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(BROKEN)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeQuery(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.BROKEN,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(PICKED_UP)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeQuery(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.PICKED_UP,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(DROPPED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeQuery(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.DROPPED,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .executes(context -> executeQuery(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.KILLED,
                                                                RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED_BY)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .executes(context -> executeQuery(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.KILLED_BY,
                                                                RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CUSTOM)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.CUSTOM_STAT))
                                                .suggests(new CustomStatsSuggestionProvider())
                                                .executes(context -> executeQuery(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.CUSTOM,
                                                                RegistryEntryArgumentType.getRegistryEntry(context, STAT, RegistryKeys.CUSTOM_STAT).value()
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

    }

    private static <T> int executeQuery(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified) throws CommandSyntaxException {
        int toReturn = 0;
        for (ServerPlayerEntity player : targets) {
            ServerStatHandler handler = player.getStatHandler();

            int statValue = handler.getStat(statType, statSpecified);

            QueryFeedback.provideFeedback(source, player, statType, statSpecified, statValue);

            toReturn += statValue;
        }
        return toReturn;
    }

    // /statistics store @p <statType<T (Block | Item | EntityType<?> | Identifier )>> <stat<T>> <objective>
    public static void registerStore(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        String executionMode = STORE;
        int executionOP = 1;

        dispatcher.register(CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP))
                .then(CommandManager.literal(executionMode)
                        .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                                .then(CommandManager.literal(MINED)
                                        .then(CommandManager.argument(STAT, BlockArgumentType.block(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeStore(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.MINED,
                                                                        BlockArgumentType.getBlock(context, STAT).block(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CRAFTED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeStore(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.CRAFTED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(USED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeStore(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.USED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(BROKEN)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeStore(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.BROKEN,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(PICKED_UP)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeStore(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.PICKED_UP,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(DROPPED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeStore(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.DROPPED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeStore(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.KILLED,
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED_BY)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeStore(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.KILLED_BY,
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CUSTOM)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.CUSTOM_STAT))
                                                .suggests(new CustomStatsSuggestionProvider())
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeStore(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.CUSTOM,
                                                                        RegistryEntryArgumentType.getRegistryEntry(context, STAT, RegistryKeys.CUSTOM_STAT).value(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static <T> int executeStore(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified, ScoreboardObjective objective) throws CommandSyntaxException {
        int toReturn = 0;
        for (ServerPlayerEntity player : targets) {
            ServerStatHandler handler = player.getStatHandler();
            Scoreboard scoreboard = source.getServer().getScoreboard();

            int statValue = handler.getStat(statType, statSpecified);
            scoreboard.getOrCreateScore(player, objective).setScore(statValue);

            RecordFeedback.provideFeedback(source, player, statType, statSpecified, statValue, objective);

            toReturn += statValue;
        }
        return toReturn;
    }

    // /statistics add @p <statType<T (Block | Item | EntityType<?> | Identifier )>> <stat<T>> <amount (int) [default:1]>
    public static void registerAdd(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        String executionMode = ADD;
        int executionOP = 2;
        dispatcher.register(CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP))
                .then(CommandManager.literal(executionMode)
                        .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                                .then(CommandManager.literal(MINED)
                                        .then(CommandManager.argument(STAT, BlockArgumentType.block(access))
                                                .executes(context -> executeAdd(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.MINED,
                                                                BlockArgumentType.getBlock(context, STAT).block()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.MINED,
                                                                        BlockArgumentType.getBlock(context, STAT).block(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CRAFTED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeAdd(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.CRAFTED,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.CRAFTED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(USED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeAdd(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.USED,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.USED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(BROKEN)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeAdd(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.BROKEN,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.BROKEN,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(PICKED_UP)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeAdd(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.PICKED_UP,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.PICKED_UP,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(DROPPED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeAdd(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.DROPPED,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.DROPPED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .executes(context -> executeAdd(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.KILLED,
                                                                RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.KILLED,
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED_BY)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .executes(context -> executeAdd(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.KILLED_BY,
                                                                RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.KILLED_BY,
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CUSTOM)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.CUSTOM_STAT))
                                                .suggests(new CustomStatsSuggestionProvider())
                                                .executes(context -> executeAdd(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.CUSTOM,
                                                                RegistryEntryArgumentType.getRegistryEntry(context, STAT, RegistryKeys.CUSTOM_STAT).value()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.CUSTOM,
                                                                        RegistryEntryArgumentType.getRegistryEntry(context, STAT, RegistryKeys.CUSTOM_STAT).value(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

    }

    public static void registerAddObj(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        String executionMode = ADD;
        int executionOP = 2;

        dispatcher.register(CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP))
                .then(CommandManager.literal(executionMode)
                        .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                                .then(CommandManager.literal(MINED)
                                        .then(CommandManager.argument(STAT, BlockArgumentType.block(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.MINED,
                                                                        BlockArgumentType.getBlock(context, STAT).block(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CRAFTED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.CRAFTED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(USED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.USED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(BROKEN)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.BROKEN,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(PICKED_UP)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.PICKED_UP,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(DROPPED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.DROPPED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.KILLED,
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED_BY)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.KILLED_BY,
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CUSTOM)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.CUSTOM_STAT))
                                                .suggests(new CustomStatsSuggestionProvider())
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeAdd(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.CUSTOM,
                                                                        RegistryEntryArgumentType.getRegistryEntry(context, STAT, RegistryKeys.CUSTOM_STAT).value(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );


    }

    private static <T> int executeAdd(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified) throws CommandSyntaxException {
        return executeAdd(source, targets, statType, statSpecified, 1);
    }

    private static <T> int executeAdd(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified, int amount) throws CommandSyntaxException {
        int toReturn = 0;
        for (ServerPlayerEntity player : targets) {
            ServerStatHandler handler = player.getStatHandler();

            int statValue = handler.getStat(statType, statSpecified);
            player.increaseStat(statType.getOrCreateStat(statSpecified), amount);

            AddFeedback.provideFeedback(source, player, statType, statSpecified, statValue, amount);
            toReturn += amount;

        }
        return toReturn;
    }

    private static <T> int executeAdd(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified, ScoreboardObjective obj) throws CommandSyntaxException {
        int toReturn = 0;

        for (ServerPlayerEntity player : targets) {
            ServerStatHandler handler = player.getStatHandler();
            Scoreboard scoreboard = player.getScoreboard();
            int amount = scoreboard.getOrCreateScore(player, obj).getScore();

            int statValue = handler.getStat(statType, statSpecified);
            player.increaseStat(statType.getOrCreateStat(statSpecified), amount);

            AddFeedback.provideFeedback(source, player, statType, statSpecified, statValue, amount, obj);
            toReturn += amount;

        }
        return toReturn;
    }

    // /statistics set @p <statType<T (Block | Item | EntityType<?> | Identifier )>> <stat<T>> <amount (int) [default:0]>
    public static void registerSet(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        String executionMode = SET;
        int executionOP = 3;

        dispatcher.register(CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP))
                .then(CommandManager.literal(executionMode)
                        .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                                .then(CommandManager.literal(MINED)
                                        .then(CommandManager.argument(STAT, BlockArgumentType.block(access))
                                                .executes(context -> executeSet(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.MINED,
                                                                BlockArgumentType.getBlock(context, STAT).block()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.MINED,
                                                                        BlockArgumentType.getBlock(context, STAT).block(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CRAFTED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeSet(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.CRAFTED,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.CRAFTED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(USED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeSet(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.USED,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.USED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(BROKEN)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeSet(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.BROKEN,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.BROKEN,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(PICKED_UP)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeSet(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.PICKED_UP,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.PICKED_UP,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(DROPPED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeSet(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.DROPPED,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.DROPPED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .executes(context -> executeSet(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.KILLED,
                                                                RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.KILLED,
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED_BY)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .executes(context -> executeSet(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.KILLED_BY,
                                                                RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.KILLED_BY,
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CUSTOM)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.CUSTOM_STAT))
                                                .suggests(new CustomStatsSuggestionProvider())
                                                .executes(context -> executeSet(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.CUSTOM,
                                                                RegistryEntryArgumentType.getRegistryEntry(context, STAT, RegistryKeys.CUSTOM_STAT).value()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(0))
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.CUSTOM,
                                                                        RegistryEntryArgumentType.getRegistryEntry(context, STAT, RegistryKeys.CUSTOM_STAT).value(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

    }

    public static void registerSetObj(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        String executionMode = SET;
        int executionOP = 3;

        dispatcher.register(CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP))
                .then(CommandManager.literal(executionMode)
                        .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                                .then(CommandManager.literal(MINED)
                                        .then(CommandManager.argument(STAT, BlockArgumentType.block(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.MINED,
                                                                        BlockArgumentType.getBlock(context, STAT).block(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CRAFTED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.CRAFTED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(USED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.USED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(BROKEN)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.BROKEN,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(PICKED_UP)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.PICKED_UP,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(DROPPED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.DROPPED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.KILLED,
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED_BY)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.KILLED_BY,
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CUSTOM)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.CUSTOM_STAT))
                                                .suggests(new CustomStatsSuggestionProvider())
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeSet(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.CUSTOM,
                                                                        RegistryEntryArgumentType.getRegistryEntry(context, STAT, RegistryKeys.CUSTOM_STAT).value(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

    }

    private static <T> int executeSet(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified) throws CommandSyntaxException {
        return executeSet(source, targets, statType, statSpecified, 0);
    }

    private static <T> int executeSet(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified, int amount) throws CommandSyntaxException {
        int toReturn = 0;
        for (ServerPlayerEntity player : targets) {
            ServerStatHandler handler = player.getStatHandler();

            int statValue = handler.getStat(statType, statSpecified);
            handler.setStat(player, statType.getOrCreateStat(statSpecified), amount);

            SetFeedback.provideFeedback(source, player, statType, statSpecified, statValue, amount);

            toReturn += amount;
        }
        return toReturn;
    }

    private static <T> int executeSet(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified, ScoreboardObjective obj) throws CommandSyntaxException {
        int toReturn = 0;
        for (ServerPlayerEntity player : targets) {
            ServerStatHandler handler = player.getStatHandler();
            Scoreboard scoreboard = player.getScoreboard();
            int amount = scoreboard.getOrCreateScore(player, obj).getScore();

            int statValue = handler.getStat(statType, statSpecified);
            handler.setStat(player, statType.getOrCreateStat(statSpecified), amount);

            SetFeedback.provideFeedback(source, player, statType, statSpecified, statValue, amount, obj);

            toReturn += amount;
        }
        return toReturn;
    }

    // /statistics set @p <statType<T (Block | Item | EntityType<?> | Identifier )>> <stat<T>> <amount (int) [default:1]
    public static void registerReduce(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        String executionMode = REDUCE;
        int executionOP = 3;

        dispatcher.register(CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP))
                .then(CommandManager.literal(executionMode)
                        .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                                .then(CommandManager.literal(MINED)
                                        .then(CommandManager.argument(STAT, BlockArgumentType.block(access))
                                                .executes(context -> executeReduce(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.MINED,
                                                                BlockArgumentType.getBlock(context, STAT).block()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.MINED,
                                                                        BlockArgumentType.getBlock(context, STAT).block(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CRAFTED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeReduce(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.CRAFTED,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.CRAFTED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(USED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeReduce(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.USED,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.USED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(BROKEN)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeReduce(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.BROKEN,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.BROKEN,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(PICKED_UP)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeReduce(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.PICKED_UP,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.PICKED_UP,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(DROPPED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .executes(context -> executeReduce(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.DROPPED,
                                                                ItemArgumentType.getItemArgument(context, STAT).getItem()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.DROPPED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .executes(context -> executeReduce(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.KILLED,
                                                                RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.KILLED,
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED_BY)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .executes(context -> executeReduce(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.KILLED_BY,
                                                                RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.KILLED_BY,
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CUSTOM)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.CUSTOM_STAT))
                                                .suggests(new CustomStatsSuggestionProvider())
                                                .executes(context -> executeReduce(
                                                                context.getSource(),
                                                                EntityArgumentType.getPlayers(context, TARGETS),
                                                                Stats.CUSTOM,
                                                                RegistryEntryArgumentType.getRegistryEntry(context, STAT, RegistryKeys.CUSTOM_STAT).value()
                                                        )
                                                )
                                                .then(CommandManager.argument(AMOUNT, IntegerArgumentType.integer(1))
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.CUSTOM,
                                                                        RegistryEntryArgumentType.getRegistryEntry(context, STAT, RegistryKeys.CUSTOM_STAT).value(),
                                                                        IntegerArgumentType.getInteger(context, AMOUNT)
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

    }

    public static void registerReduceObj(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        String executionMode = REDUCE;
        int executionOP = 3;
        dispatcher.register(CommandManager.literal(STATISTICS).requires(source -> source.hasPermissionLevel(executionOP))
                .then(CommandManager.literal(executionMode)
                        .then(CommandManager.argument(TARGETS, EntityArgumentType.players())
                                .then(CommandManager.literal(MINED)
                                        .then(CommandManager.argument(STAT, BlockArgumentType.block(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.MINED,
                                                                        BlockArgumentType.getBlock(context, STAT).block(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CRAFTED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.CRAFTED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(USED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.USED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(BROKEN)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.BROKEN,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(PICKED_UP)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.PICKED_UP,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(DROPPED)
                                        .then(CommandManager.argument(STAT, ItemArgumentType.item(access))
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.DROPPED,
                                                                        ItemArgumentType.getItemArgument(context, STAT).getItem(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.KILLED,
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(KILLED_BY)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.ENTITY_TYPE))
                                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.KILLED_BY,
                                                                        RegistryEntryArgumentType.getSummonableEntityType(context, STAT).value(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                ).then(CommandManager.literal(CUSTOM)
                                        .then(CommandManager.argument(STAT, RegistryEntryArgumentType.registryEntry(access, RegistryKeys.CUSTOM_STAT))
                                                .suggests(new CustomStatsSuggestionProvider())
                                                .then(CommandManager.argument(OBJECTIVE, ScoreboardObjectiveArgumentType.scoreboardObjective())
                                                        .executes(context -> executeReduce(
                                                                        context.getSource(),
                                                                        EntityArgumentType.getPlayers(context, TARGETS),
                                                                        Stats.CUSTOM,
                                                                        RegistryEntryArgumentType.getRegistryEntry(context, STAT, RegistryKeys.CUSTOM_STAT).value(),
                                                                        ScoreboardObjectiveArgumentType.getObjective(context, OBJECTIVE)
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static <T> int executeReduce(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified) throws CommandSyntaxException {
        return executeReduce(source, targets, statType, statSpecified, 1);
    }

    private static <T> int executeReduce(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified, int amount) throws CommandSyntaxException {
        int toReturn = 0;
        for (ServerPlayerEntity player : targets) {
            ServerStatHandler handler = player.getStatHandler();
            int statValue = handler.getStat(statType, statSpecified);
            int statValueNext = Math.max(0, statValue - amount);
            handler.setStat(player, statType.getOrCreateStat(statSpecified), statValueNext);
            ReduceFeedback.provideFeedback(source, player, statType, statSpecified, statValue, amount, statValueNext);

            toReturn += amount;
        }
        return toReturn;
    }

    private static <T> int executeReduce(ServerCommandSource source, Collection<ServerPlayerEntity> targets, StatType<T> statType, T statSpecified, ScoreboardObjective obj) throws CommandSyntaxException {
        int toReturn = 0;
        for (ServerPlayerEntity player : targets) {
            ServerStatHandler handler = player.getStatHandler();
            Scoreboard scoreboard = player.getScoreboard();
            int amount = scoreboard.getOrCreateScore(player, obj).getScore();

            int statValue = handler.getStat(statType, statSpecified);
            int statValueNext = statValue - amount;
            handler.setStat(player, statType.getOrCreateStat(statSpecified), statValueNext);
            ReduceFeedback.provideFeedback(source, player, statType, statSpecified, statValue, amount, statValueNext, obj);

            toReturn += amount;
        }
        return toReturn;
    }

}

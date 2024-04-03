package hydraheadhunter.commandstatistics.util;

import hydraheadhunter.commandstatistics.command.StatisticsCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModRegistries {

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(StatisticsCommand::registerQuery);
        CommandRegistrationCallback.EVENT.register(StatisticsCommand::registerStore);
        CommandRegistrationCallback.EVENT.register(StatisticsCommand::registerAdd);
        CommandRegistrationCallback.EVENT.register(StatisticsCommand::registerSet);
        CommandRegistrationCallback.EVENT.register(StatisticsCommand::registerReduce);

        CommandRegistrationCallback.EVENT.register(StatisticsCommand::registerAddObj);
        CommandRegistrationCallback.EVENT.register(StatisticsCommand::registerSetObj);
        CommandRegistrationCallback.EVENT.register(StatisticsCommand::registerReduceObj);

    }
}

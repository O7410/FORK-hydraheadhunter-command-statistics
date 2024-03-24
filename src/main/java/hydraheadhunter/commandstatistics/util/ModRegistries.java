package hydraheadhunter.commandstatistics.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import hydraheadhunter.commandstatistics.command.StatisticsCommand;

public class ModRegistries {

     public static void registerCommands(){
          CommandRegistrationCallback.EVENT.register(StatisticsCommand::registerQUERY  );
          CommandRegistrationCallback.EVENT.register(StatisticsCommand::registerRECORD );
          CommandRegistrationCallback.EVENT.register(StatisticsCommand::registerADD    );
          CommandRegistrationCallback.EVENT.register(StatisticsCommand::registerSET    );
          CommandRegistrationCallback.EVENT.register(StatisticsCommand::registerREDUCE );
     }
}

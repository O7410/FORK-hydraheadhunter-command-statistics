package hydraheadhunter.commandstatistics.command.argument;

import com.mojang.brigadier.context.CommandContext;
import hydraheadhunter.commandstatistics.command.ExecutionMode;
import net.minecraft.command.argument.EnumArgumentType;
import net.minecraft.server.command.ServerCommandSource;

public class ExecutionModeArgumentType extends EnumArgumentType<ExecutionMode> {
    private ExecutionModeArgumentType() {
        super(ExecutionMode.CODEC, ExecutionMode::values);
    }

    public static ExecutionModeArgumentType executionMode() {
        return new ExecutionModeArgumentType();
    }

    public static ExecutionMode getExecutionMode(CommandContext<ServerCommandSource> context, String id) {
        return context.getArgument(id, ExecutionMode.class);
    }

    public static ExecutionMode.ExecuteFunction getExecuteFunction(CommandContext<ServerCommandSource> context, String id) {
        return ExecutionModeArgumentType.getExecutionMode(context, id).executeFunction;
    }
}

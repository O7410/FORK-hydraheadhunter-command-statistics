package hydraheadhunter.commandstatistics.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatType;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public enum ExecutionMode implements StringIdentifiable {
    QUERY("query"),
    STORE("store"),
    ADD("add"),
    SET("set"),
    REDUCE("reduce"),
    ADD_OBJ("add"),
    SET_OBJ("set"),
    REDUCE_OBJ("reduce");

    public static final Codec<ExecutionMode> CODEC = StringIdentifiable.createCodec(ExecutionMode::values);

    private final String name;

    private ExecutionMode(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}

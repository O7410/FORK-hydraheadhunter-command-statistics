package hydraheadhunter.commandstatistics.command.argument;

import net.minecraft.block.Block;

import java.util.function.Predicate;

public record BlockArgument(Block block) implements Predicate<Block> {

    @Override
    public boolean test(Block block) {
        return this.block == block;
    }
}

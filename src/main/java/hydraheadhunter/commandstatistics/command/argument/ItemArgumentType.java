package hydraheadhunter.commandstatistics.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ItemArgumentType implements ArgumentType<ItemArgument> {
    private static final Collection<String> EXAMPLES = Arrays.asList("stick", "minecraft:stick");
    private final RegistryWrapper<Item> registryWrapper;

    public ItemArgumentType(CommandRegistryAccess commandRegistryAccess) {
        this.registryWrapper = commandRegistryAccess.createWrapper(RegistryKeys.ITEM);
    }

    public static ItemArgumentType item(CommandRegistryAccess commandRegistryAccess) {
        return new ItemArgumentType(commandRegistryAccess);
    }

    @Override
    public ItemArgument parse(StringReader stringReader) throws CommandSyntaxException {
        ItemNoStackStringReader.ItemResult itemResult = ItemNoStackStringReader.item(this.registryWrapper, stringReader);
        return new ItemArgument(itemResult.item());
    }

    public static <S> ItemArgument getItemArgument(CommandContext<S> context, String name) {
        return context.getArgument(name, ItemArgument.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return ItemNoStackStringReader.getSuggestions(this.registryWrapper, builder, false);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}

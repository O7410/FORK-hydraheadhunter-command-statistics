package hydraheadhunter.commandstatistics.command.suggestionprovider;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import hydraheadhunter.commandstatistics.CommandStatistics;
import net.minecraft.command.CommandSource;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

public class ModSuggestionProviders {
    public static final SuggestionProvider<ServerCommandSource> BREAKABLE_ITEMS = SuggestionProviders.register(new Identifier(CommandStatistics.MOD_ID, "breakable_items"),
            (context, builder) -> CommandSource.suggestIdentifiers(
                    Registries.ITEM.stream()
                            .filter(item -> item.isEnabled(context.getSource().getEnabledFeatures()) &&
                                    item.isDamageable())
                            .map(Registries.ITEM::getId),
                    builder
            ));
}

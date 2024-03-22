package hydraheadhunter.commandstatistics.command.suggestionprovider;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class BreakableItemSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
     @Override
     public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
          ServerCommandSource source = context.getSource();


          // Add all items with durability to the builder.
          builder.suggest("minecraft:turtle_shell_helmet");
          builder.suggest("minecraft:leather_helmet");
          builder.suggest("minecraft:golden_helmet");
          builder.suggest("minecraft:chainmail_helmet");
          builder.suggest("minecraft:iron_helmet");
          builder.suggest("minecraft:diamond_helmet");
          builder.suggest("minecraft:netherite_helmet");
          builder.suggest("minecraft:leather_chestplate");
          builder.suggest("minecraft:golden_chestplate");
          builder.suggest("minecraft:chainmail_chestplate");
          builder.suggest("minecraft:iron_chestplate");
          builder.suggest("minecraft:diamond_chestplate");
          builder.suggest("minecraft:netherite_chestplate");
          builder.suggest("minecraft:leather_leggings");
          builder.suggest("minecraft:golden_leggings");
          builder.suggest("minecraft:chainmail_leggings");
          builder.suggest("minecraft:iron_leggings");
          builder.suggest("minecraft:diamond_leggings");
          builder.suggest("minecraft:netherite_leggings");
          builder.suggest("minecraft:leather_boots");
          builder.suggest("minecraft:golden_boots");
          builder.suggest("minecraft:chainmail_boots");
          builder.suggest("minecraft:iron_boots");
          builder.suggest("minecraft:diamond_boots");
          builder.suggest("minecraft:netherite_boots");

          builder.suggest("minecraft:wooden_axe");
          builder.suggest("minecraft:stone_axe");
          builder.suggest("minecraft:iron_axe");
          builder.suggest("minecraft:diamond_axe");
          builder.suggest("minecraft:netherite_axe");

          builder.suggest("minecraft:wooden_pickaxe");
          builder.suggest("minecraft:stone_pickaxe");
          builder.suggest("minecraft:iron_pickaxe");
          builder.suggest("minecraft:diamond_pickaxe");
          builder.suggest("minecraft:netherite_pickaxe");

          builder.suggest("minecraft:wooden_shovel");
          builder.suggest("minecraft:stone_shovel");
          builder.suggest("minecraft:iron_shovel");
          builder.suggest("minecraft:diamond_shovel");
          builder.suggest("minecraft:netherite_shovel");

          builder.suggest("minecraft:wooden_hoe");
          builder.suggest("minecraft:stone_hoe");
          builder.suggest("minecraft:iron_hoe");
          builder.suggest("minecraft:diamond_hoe");
          builder.suggest("minecraft:netherite_hoe");

          builder.suggest("minecraft:wooden_sword");
          builder.suggest("minecraft:stone_sword");
          builder.suggest("minecraft:iron_sword");
          builder.suggest("minecraft:diamond_sword");
          builder.suggest("minecraft:netherite_sword");

          builder.suggest("minecraft:fishing_rod");
          builder.suggest("minecraft:carrot_on_a_stick");
          builder.suggest("minecraft:warped_fungus_on_a_stick");
          builder.suggest("minecraft:flint_and_steel");
          builder.suggest("minecraft:trident");
          builder.suggest("minecraft:elytra");
          builder.suggest("minecraft:shield");



          // Lock the suggestions after we've modified them.
          return builder.buildFuture();
     }
}
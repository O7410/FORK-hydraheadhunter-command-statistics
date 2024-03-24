package hydraheadhunter.commandstatistics.command.feedback;

import net.minecraft.entity.EntityType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.minecraft.text.Text.stringifiedTranslatable;

public class QueryEntityFeedback {

     public static void provideBlockFeedback (ServerCommandSource source, ServerPlayerEntity player, StatType<EntityType<?>> statType, EntityType<?> statSpecific, int statValue){
          String formatKey       = "commandstatistics.feedback.query.default";
          Text playerName        = player.getName();
          Text statTypeText      = statType.getName();
          Text statSpecificText  = Text.translatable(statSpecific.getTranslationKey()).formatted(Formatting.GOLD);
          Text statValueText     = Text.literal( String.valueOf(statValue) );

          source.sendFeedback(() -> stringifiedTranslatable( formatKey, playerName, statTypeText, statSpecificText, statValueText ), false );
     }


}

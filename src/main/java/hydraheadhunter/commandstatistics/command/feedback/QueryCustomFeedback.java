package hydraheadhunter.commandstatistics.command.feedback;


import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.minecraft.text.Text.stringifiedTranslatable;

public class QueryCustomFeedback {

     public static void provideCustomFeedback (ServerCommandSource source, ServerPlayerEntity player, String statSpecific, int statValue){
          String formatKey       = "commandstatistics.feedback.query.default";
          Text playerName        = player.getName();
          Text statTypeText      = Stats.CUSTOM.getName();
          Text statSpecificText  = Text.literal(statSpecific).formatted(Formatting.GOLD);
          Text statValueText     = Text.literal( String.valueOf(statValue) );

          source.sendFeedback(() -> stringifiedTranslatable( formatKey, playerName, statTypeText, statSpecificText, statValueText ), false );
     }
}



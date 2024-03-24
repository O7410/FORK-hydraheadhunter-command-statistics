package hydraheadhunter.commandstatistics.command.feedback;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class UnsupportedCustomStatistic {

     public static int giveFeedback (ServerCommandSource source, String unsupportedID){
          source.sendFeedback(() -> Text.stringifiedTranslatable( "commandstatistics.unsupported", ((MutableText)Text.of(unsupportedID)).formatted(Formatting.RED)), false);
          return -1;
     }
}

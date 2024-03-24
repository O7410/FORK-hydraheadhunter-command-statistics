package hydraheadhunter.commandstatistics.util;

import hydraheadhunter.commandstatistics.CommandStatistics;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

//These tags are a hold over from when I was avoiding figuring out how to fix my STATS custom code by doing fancy translation stuff.
// I ripped all that out in favor of bare bones feedback. I might add it back in later when polishing, and if I do, I'll be needing theses.
public class ModTags {
     public static class Blocks{
          public static final TagKey<Block> USE_DEFINITE_THE          = createTag("uses_indefinite_the"   );
          public static final TagKey<Block> USE_INDEFINITE_A          = createTag("uses_indefinite_a"     );
          public static final TagKey<Block> USE_INDEFINITE_AN         = createTag("uses_indefinite_an"    );
          public static final TagKey<Block> USE_INDEFINITE_PAIR       = createTag("uses_indefinite_pain"  );
          public static TagKey<Block> createTag(String name){
               return TagKey.of(RegistryKeys.BLOCK, new Identifier(CommandStatistics.MOD_ID, name));

          }

     }
     public static class Items{
          public static final TagKey<Item> USE_DEFINITE_THE           = createTag("uses_indefinite_the"   );
          public static final TagKey<Item> USE_INDEFINITE_A           = createTag("uses_indefinite_a"     );
          public static final TagKey<Item> USE_INDEFINITE_AN          = createTag("uses_indefinite_an"    );
          public static final TagKey<Item> USE_INDEFINITE_PAIR        = createTag("uses_indefinite_pain"  );

          public static TagKey<Item> createTag(String name){
               return TagKey.of(RegistryKeys.ITEM, new Identifier(CommandStatistics.MOD_ID, name));

          }
     }


}

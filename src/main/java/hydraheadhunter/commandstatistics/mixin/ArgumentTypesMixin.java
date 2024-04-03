package hydraheadhunter.commandstatistics.mixin;

import com.mojang.brigadier.arguments.ArgumentType;
import hydraheadhunter.commandstatistics.CommandStatistics;
import hydraheadhunter.commandstatistics.command.argument.BlockArgumentType;
import hydraheadhunter.commandstatistics.command.argument.ItemArgumentType;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArgumentTypes.class)
public abstract class ArgumentTypesMixin {
    @Shadow
    private static <A extends ArgumentType<?>, T extends ArgumentSerializer.ArgumentTypeProperties<A>> ArgumentSerializer<A, T> register(Registry<ArgumentSerializer<?, ?>> registry,
                                                                                                                                         String id,
                                                                                                                                         Class<? extends A> clazz,
                                                                                                                                         ArgumentSerializer<A, T> serializer) {
        throw new AssertionError();
    }

    @Inject(method = "register(Lnet/minecraft/registry/Registry;)Lnet/minecraft/command/argument/serialize/ArgumentSerializer;", at = @At("TAIL"))
    private static void registerCustomArgumentType(Registry<ArgumentSerializer<?, ?>> registry, CallbackInfoReturnable<ArgumentSerializer<?, ?>> cir) {
        register(registry, CommandStatistics.MOD_ID + ":item", ItemArgumentType.class, ConstantArgumentSerializer.of(ItemArgumentType::item));
        register(registry, CommandStatistics.MOD_ID + ":block", BlockArgumentType.class, ConstantArgumentSerializer.of(BlockArgumentType::block));
    }
}

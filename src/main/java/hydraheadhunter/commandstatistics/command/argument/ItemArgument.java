package hydraheadhunter.commandstatistics.command.argument;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public class ItemArgument implements Predicate<Item> {
    private final RegistryEntry<Item> item;

    public ItemArgument(RegistryEntry<Item> item) {
        this.item = item;
    }

    public Item getItem() {
        return this.item.value();
    }

    @Override
    public boolean test(Item item) {
        return this.item == item;
    }

    public String asString() {
        return this.getIdString();
    }

    private String getIdString() {
        return this.item.getKey().map(RegistryKey::getValue).map(Identifier::toString).orElseGet(() -> "unknown[" + this.item + "]");
    }
}

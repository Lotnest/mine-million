package dev.lotnest.minemillion.util;

import com.google.common.collect.Multimap;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class ItemStackBuilder {

    private final Material material;
    private final ItemMeta itemMeta;
    private int amount = 1;

    private ItemStackBuilder(@NotNull Material material) {
        this.material = material;
        itemMeta = new ItemStack(material).getItemMeta();
    }

    private ItemStackBuilder(@NotNull ItemStack itemStack) {
        this.material = itemStack.getType();
        itemMeta = itemStack.getItemMeta();
    }

    @Contract("_ -> new")
    public static @NotNull ItemStackBuilder material(@NotNull Material material) {
        return new ItemStackBuilder(material);
    }

    @Contract("_ -> new")
    public static @NotNull ItemStackBuilder itemStack(@NotNull ItemStack itemStack) {
        return new ItemStackBuilder(itemStack);
    }

    public @NotNull ItemStack build() {
        Validate.notNull(material, "Material can not be null");

        ItemStack result = new ItemStack(material, amount);

        if (itemMeta != null) {
            result.setItemMeta(itemMeta);
        }

        return result;
    }

    public @NotNull ItemStackBuilder name(@Nullable String name) {
        if (name != null) {
            itemMeta.setDisplayName(MessageUtil.color(name));
        }
        return this;
    }

    public @NotNull ItemStackBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public @NotNull ItemStackBuilder lore(@Nullable String... lore) {
        if (lore != null) {
            itemMeta.setLore(Arrays.stream(lore)
                    .filter(Objects::nonNull)
                    .map(loreLine -> MessageUtil.color(loreLine.replaceAll("%n", "")))
                    .toList());
        }
        return this;
    }

    public @NotNull ItemStackBuilder lore(@Nullable Collection<String> lore) {
        if (lore != null) {
            itemMeta.setLore(lore.stream().toList());
        }
        return this;
    }

    public @NotNull ItemStackBuilder unbreakable(boolean value) {
        itemMeta.setUnbreakable(value);
        return this;
    }

    public @NotNull ItemStackBuilder localizedName(@NotNull String localizedName) {
        itemMeta.setLocalizedName(localizedName);
        return this;
    }

    public @NotNull ItemStackBuilder attributeModifiers(@NotNull Multimap<Attribute, AttributeModifier> attributeModifiersMultimap) {
        itemMeta.setAttributeModifiers(attributeModifiersMultimap);
        return this;
    }

    public @NotNull ItemStackBuilder skullOwner(@NotNull OfflinePlayer skullOwner) {
        ((SkullMeta) itemMeta).setOwningPlayer(skullOwner);
        return this;
    }
}
package dev.lotnest.minemillion.gui;

import dev.lotnest.minemillion.util.InventoryUtil;
import dev.lotnest.minemillion.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Page(@NotNull String title, @NotNull List<Item> items) {

    public Page(@NotNull String title, @NotNull List<Item> items) {
        this.title = title;
        this.items = Stream.ofNullable(items)
                .flatMap(Collection::stream)
                .limit(54)
                .collect(Collectors.toList());
    }

    public @NotNull Optional<Item> findItem(@NotNull ItemStack itemStack) {
        return items.stream()
                .filter(item -> ItemUtil.isEqual(item.getItemStack(), itemStack))
                .findFirst();
    }

    public @NotNull Optional<Item> findItem(@NotNull Item item) {
        return items.stream()
                .filter(item::equals)
                .findFirst();
    }

    public void addItem(@NotNull Item item) {
        items.add(item);
    }

    public void removeItem(@NotNull Item item) {
        items.remove(item);
    }

    public void clearItems() {
        items.clear();
    }

    public @NotNull Inventory toInventory() {
        Inventory result = Bukkit.createInventory(null, InventoryUtil.getClosestValidSize(items.size()), title);
        items.forEach(item -> result.addItem(item.getItemStack()));
        return result;
    }
}

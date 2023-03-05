package dev.lotnest.minemillion.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import dev.lotnest.minemillion.util.InventoryUtil;
import dev.lotnest.minemillion.util.ItemUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jooq.tools.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class GUI {

    private static final Set<GUI> GUIS = Sets.newHashSet();

    private final @NotNull String title;
    private final List<Page> pages;
    private final Map<UUID, Integer> uuidCurrentPageMap;

    private GUI(@NotNull Inventory inventory, @NotNull String title) {
        this.title = title;
        pages = Lists.newArrayList(new Page(title, Item.itemStacksToItems(Lists.newArrayList(inventory.getContents()))));
        uuidCurrentPageMap = Maps.newHashMap();
        GUIS.add(this);
    }

    private GUI(@NotNull String title, @NotNull List<Page> pages) {
        this.title = title;
        this.pages = pages;
        uuidCurrentPageMap = Maps.newHashMap();
        GUIS.add(this);
    }

    public static @NotNull Optional<GUI> findGUI(@NotNull UUID uuid) {
        return GUIS.stream()
                .filter(gui -> gui.getUuidCurrentPageMap().containsKey(uuid))
                .findFirst();
    }

    public static @NotNull Optional<GUI> findGUI(@NotNull String title) {
        return GUIS.stream()
                .filter(gui -> gui.getTitle().equals(title))
                .findFirst();
    }

    public static @NotNull Optional<GUI> findGUI(@NotNull Inventory inventory) {
        return GUIS.stream()
                .filter(gui -> gui.getPages().stream()
                        .anyMatch(page -> InventoryUtil.isSimilar(page.toInventory(), inventory)))
                .findFirst();
    }

    public static @NotNull Optional<GUI> findGUI(@NotNull ItemStack itemStack) {
        return GUIS.stream()
                .filter(gui -> gui.getPages().stream()
                        .flatMap(page -> page.items().stream())
                        .anyMatch(item -> ItemUtil.isEqual(item.getItemStack(), itemStack)))
                .findFirst();
    }

    public static @NotNull Optional<GUI> findGUI(@NotNull Page page) {
        return GUIS.stream()
                .filter(gui -> gui.getPages().contains(page))
                .findFirst();
    }

    public static @NotNull GUI create(int size, @NotNull String title) {
        return create(size, title, new ItemStack[size]);
    }

    public static @NotNull GUI create(int size, @NotNull String title, @NotNull ItemStack[] contents) {
        Inventory result = Bukkit.createInventory(null, size, title);
        result.setContents(contents);
        return create(result, title);
    }

    public static @NotNull GUI create(@NotNull List<Page> pages) {
        if (pages.isEmpty()) {
            pages.add(new Page(StringUtils.EMPTY, Lists.newArrayList()));
        }
        return new GUI(pages.get(0).title(), pages);
    }

    public static @NotNull GUI create(@NotNull Inventory inventory, @NotNull String title) {
        return new GUI(inventory, title);
    }

    public @NotNull Optional<Page> findPage(int index) {
        return pages.stream()
                .filter(page -> pages.indexOf(page) == index)
                .findFirst();
    }

    public @NotNull Optional<Page> findPage(@NotNull Inventory inventory) {
        return pages.stream()
                .filter(page -> InventoryUtil.isSimilar(page.toInventory(), inventory))
                .findFirst();
    }

    public @NotNull Optional<Page> findPage(@NotNull String title) {
        return pages.stream()
                .filter(page -> page.title().equals(title))
                .findFirst();
    }

    public @NotNull Optional<Item> findItem(@NotNull ItemStack itemStack) {
        return pages.stream()
                .flatMap(page -> page.items().stream())
                .filter(item -> ItemUtil.isEqual(item.getItemStack(), itemStack))
                .findFirst();
    }

    public @NotNull GUI addPage(@NotNull Page page) {
        pages.add(page);
        return this;
    }

    public @NotNull GUI addPage(@NotNull String title, @NotNull List<Item> items) {
        return addPage(new Page(title, items));
    }

    public @NotNull GUI addPage(@NotNull String title, @NotNull ItemStack[] items) {
        return addPage(new Page(title, Item.itemStacksToItems(Lists.newArrayList(items))));
    }

    public @NotNull GUI addPage(@NotNull String title, @NotNull Inventory inventory) {
        return addPage(new Page(title, Item.itemStacksToItems(Lists.newArrayList(inventory.getContents()))));
    }

    public @NotNull GUI addPage(@NotNull String title, int size) {
        return addPage(new Page(title, Item.itemStacksToItems(Lists.newArrayList(new ItemStack[size]))));
    }

    public @NotNull GUI addPage(@NotNull String title, int size, @NotNull ItemStack[] contents) {
        return addPage(new Page(title, Item.itemStacksToItems(Lists.newArrayList(contents))));
    }

    public @NotNull GUI addPage(@NotNull String title, int size, @NotNull Inventory inventory) {
        return addPage(new Page(title, Item.itemStacksToItems(Lists.newArrayList(inventory.getContents()))));
    }

    public @NotNull GUI removePage(@NotNull Page page) {
        pages.remove(page);
        return this;
    }

    public @NotNull GUI removePage(@NotNull String title) {
        pages.removeIf(page -> page.title().equals(title));
        return this;
    }

    public @NotNull GUI removePage(int index) {
        pages.remove(index);
        return this;
    }

    public @NotNull GUI removePage(@NotNull ItemStack itemStack) {
        pages.removeIf(page -> page.items().stream().anyMatch(item -> ItemUtil.isEqual(item.getItemStack(), itemStack)));
        return this;
    }

    public boolean hasPage(@NotNull UUID uuid) {
        return uuidCurrentPageMap.containsKey(uuid);
    }

    public void addToPage(@NotNull UUID uuid) {
        uuidCurrentPageMap.put(uuid, 0);
    }

    public void setPage(@NotNull UUID uuid, int page) {
        uuidCurrentPageMap.put(uuid, page);
    }

    public int getPageIndex(@NotNull UUID uuid) {
        if (!hasPage(uuid)) {
            addToPage(uuid);
        }
        return uuidCurrentPageMap.get(uuid);
    }

    public @NotNull Page getPage(@NotNull UUID uuid) {
        return pages.get(getPageIndex(uuid));
    }

    public int getPreviousPageIndex(@NotNull UUID uuid) {
        return Math.max(uuidCurrentPageMap.get(uuid) - 1, 0);
    }

    public @NotNull Page getPreviousPage(@NotNull UUID uuid) {
        return pages.get(getPreviousPageIndex(uuid));
    }

    public int getNextPageIndex(@NotNull UUID uuid) {
        return Math.min(uuidCurrentPageMap.get(uuid) + 1, pages.size() - 1);
    }

    public @NotNull Page getNextPage(@NotNull UUID uuid) {
        return pages.get(getNextPageIndex(uuid));
    }

    public void setPreviousPageIndex(@NotNull UUID uuid) {
        uuidCurrentPageMap.put(uuid, getPreviousPageIndex(uuid));
    }

    public void setNextPageIndex(@NotNull UUID uuid) {
        uuidCurrentPageMap.put(uuid, getNextPageIndex(uuid));
    }

    public void removeFromPageIndex(@NotNull UUID uuid) {
        uuidCurrentPageMap.remove(uuid);
    }

    public void open(@NotNull Player player) {
        player.openInventory(getPage(player.getUniqueId()).toInventory());
    }

    public void open(@NotNull Player player, int page) {
        player.openInventory(findPage(page)
                .orElse(getPage(player.getUniqueId())).toInventory());
    }

    public void open(@NotNull UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null && player.isOnline()) {
            open(player);
        }
    }

    public void open(@NotNull UUID uuid, int page) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null && player.isOnline()) {
            open(player, page);
        }
    }
}

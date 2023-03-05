package dev.lotnest.minemillion.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GUIHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedInventory == null) {
            return;
        }

        if (clickedItem == null) {
            return;
        }

        GUI.findGUI(clickedInventory)
                .ifPresent(gui -> {
                    event.setCancelled(true);
                    gui.findPage(clickedInventory)
                            .flatMap(page -> page.findItem(clickedItem))
                            .ifPresent(item -> item.getActionOnClick().accept(event));
                });
    }
}

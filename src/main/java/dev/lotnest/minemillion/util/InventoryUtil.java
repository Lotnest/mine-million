package dev.lotnest.minemillion.util;

import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class InventoryUtil {

    private InventoryUtil() {
    }

    public static int getClosestValidSize(int size) {
        if (size < 9) {
            return 9;
        }
        if (size > 54) {
            return 54;
        }
        return Math.round(size / 9F) * 9;
    }

    public static boolean isSimilar(@NotNull Inventory inventory, @NotNull Inventory otherInventory) {
        return inventory.getSize() == otherInventory.getSize() && Arrays.equals(inventory.getContents(), otherInventory.getContents()) &&
                Objects.equals(inventory.getHolder(), otherInventory.getHolder()) && inventory.getType() == otherInventory.getType();
    }
}

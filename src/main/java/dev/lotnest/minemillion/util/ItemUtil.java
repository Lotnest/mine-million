package dev.lotnest.minemillion.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemUtil {

    private ItemUtil() {
    }

    public static int getIndex(@NotNull List<ItemStack> itemStacks, @NotNull ItemStack searchedItem) {
        return itemStacks.indexOf(searchedItem);
    }

    public static int getItemCount(ItemStack[] items) {
        return (int) Arrays.stream(items)
                .filter(notAir -> notAir != null && !notAir.getType().isAir()).count();
    }

    public static @NotNull ItemStack getCustomSkull(@NotNull String base64Texture) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();

        if (propertyMap == null) {
            throw new IllegalStateException("Profile does not contain a property map");
        }

        propertyMap.put("textures", new Property("textures", base64Texture));

        ItemStack skullItem = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta skullMeta = skullItem.getItemMeta();
        assert skullMeta != null;

        Field profileField;
        Method setProfileMethod = null;

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
            try {
                setProfileMethod = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            } catch (NoSuchMethodException ignored) {
            }
        } catch (SecurityException ignored) {
        }

        try {
            if (setProfileMethod == null) {
                profileField = skullMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(skullMeta, profile);
            } else {
                setProfileMethod.setAccessible(true);
                setProfileMethod.invoke(skullMeta, profile);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        skullItem.setItemMeta(skullMeta);
        return skullItem;
    }


    public static boolean isEqual(@NotNull ItemStack itemStack, @NotNull ItemStack otherItemStack) {
        ItemMeta itemMeta1 = itemStack.getItemMeta();
        ItemMeta itemMeta2 = otherItemStack.getItemMeta();

        boolean isMaterialEqual = itemStack.getType().equals(otherItemStack.getType());

        if (itemMeta1 != null && itemMeta2 != null) {
            return isMaterialEqual && itemMeta1.getDisplayName().equals(itemMeta2.getDisplayName());
        }

        return isMaterialEqual;
    }
}
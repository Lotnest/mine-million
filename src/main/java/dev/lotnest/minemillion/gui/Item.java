package dev.lotnest.minemillion.gui;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.language.LanguageProvider;
import dev.lotnest.minemillion.question.Question;
import dev.lotnest.minemillion.util.ColorConstants;
import dev.lotnest.minemillion.util.ItemConstants;
import dev.lotnest.minemillion.util.ItemStackBuilder;
import dev.lotnest.minemillion.util.MessageUtil;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Data
@Builder
@EqualsAndHashCode
public class Item {

    private final @NotNull ItemStack itemStack;
    private Consumer<InventoryClickEvent> actionOnClick;

    public static @NotNull List<Item> itemStacksToItems(@NotNull List<ItemStack> itemStacks) {
        return itemStacks.stream()
                .map(itemStack -> Item.builder()
                        .itemStack(itemStack)
                        .build())
                .collect(Collectors.toList());
    }

    public static @NotNull Item questionToItem(@NotNull Question question, boolean showCorrectAnswer) {
        return Item.builder()
                .itemStack(ItemStackBuilder.itemStack(ItemConstants.QUESTION_ITEM)
                        .name(ColorConstants.RED + question.getText())
                        .lore(question.getAnswers(showCorrectAnswer))
                        .build())
                .actionOnClick(event -> {
                    Player player = (Player) event.getWhoClicked();
                    LanguageProvider languageProvider = MineMillionPlugin.getInstance().getLanguageProvider();

                    player.closeInventory();
                    player.sendMessage(ColorConstants.GREEN + languageProvider.get("question.text") + ": " + ColorConstants.RED + question.getText());
                    player.sendMessage(ColorConstants.GREEN + languageProvider.get("question.answers") + ":");
                    MessageUtil.sendMessage(player, question.getAnswers(showCorrectAnswer));
                })
                .build();
    }
}

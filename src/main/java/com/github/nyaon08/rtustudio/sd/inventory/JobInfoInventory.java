package com.github.nyaon08.rtustudio.sd.inventory;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.configuration.IconConfig;
import com.github.nyaon08.rtustudio.sd.configuration.JobConfig;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.format.ComponentFormatter;
import kr.rtuserver.framework.bukkit.api.inventory.RSInventory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

public class JobInfoInventory extends RSInventory<SDJobCraft> {

    private final IconConfig iconConfig;
    private final JobConfig jobConfig;
    private final JobManager jobManager;

    @Getter
    private Inventory inventory;
    private final Player player;

    private int page = 0;
    private final int maxPage = 1;

    public JobInfoInventory(SDJobCraft plugin, Player player) {
        super(plugin);
        this.jobConfig = plugin.getJobConfig();
        this.iconConfig = plugin.getIconConfig();
        this.jobManager = plugin.getJobManager();
        this.player = player;

        loadPage(0);
    }

    private Inventory createInventory(int page) {
        Component title = ComponentFormatter.parse(
                String.valueOf(jobConfig.getJobInfoInventoryTitle().get(String.valueOf(page)))
        );
        return createInventory(54, title);
    }

    private void loadPage(int page) {
        this.page = page;
        this.inventory = createInventory(page);
        player.openInventory(this.inventory);

        inventory.setItem(8, pageIcon(Navigation.UP));
        inventory.setItem(17, pageIcon(Navigation.UP));
        inventory.setItem(44, pageIcon(Navigation.DOWN));
        inventory.setItem(53, pageIcon(Navigation.DOWN));

        init();
    }

    private void init() {
        Map<List<Integer>, String> slotItemMap = Map.of(
                List.of(0, 1, 2, 3, 4, 9, 10, 11, 12, 13, 18, 19, 20, 21, 22, 27, 28, 29, 30, 31), "info",
                List.of(5, 6, 7, 14, 15, 16), "1_skill",
                List.of(23, 24, 25, 32, 33, 34), "2_skill",
                List.of(41, 42, 43, 50, 51, 52), "3_skill",
                List.of(36, 37, 38, 39, 40, 45, 46, 47, 48, 49), "planned"
        );

        for (Map.Entry<List<Integer>, String> entry : slotItemMap.entrySet()) {
            for (int slot : entry.getKey()) {
                ItemStack itemStack = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.displayName(Component.empty());
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(slot, itemStack);
            }
        }
    }

    private ItemStack pageIcon(Navigation navigation) {
        String name = navigation.name().toLowerCase();
        boolean isAvailable = navigation.check(page, maxPage);
        String available = isAvailable ? "available" : "unavailable";
        String display = message().get(player, "icon.menu.pagination." + name + "." + available);
        display = display.replace("[current]", String.valueOf(page + 1))
                .replace("[max]", String.valueOf(maxPage + 1));
        return iconConfig.get("menu.pagination." + name + "." + available, display);
    }

    private void loadPage(Navigation navigation) {
        if (!navigation.check(page, maxPage)) return;
        loadPage(navigation == Navigation.UP ? 0 : 1);
    }

    @Override
    public boolean onClick(Event<InventoryClickEvent> event, RSInventory.Click click) {
        if (inventory.isEmpty()) return false;
        if (event.isInventory()) return false;

        int slot = click.slot();
        if (slot < 0) return false;

        switch (slot) {
            case 8, 17 -> loadPage(Navigation.UP);
            case 44, 53 -> loadPage(Navigation.DOWN);
            default -> {
            }
        }

        return false;
    }

    @RequiredArgsConstructor
    protected enum Navigation {
        DOWN((page, maxPage) -> page == 0),
        UP((page, maxPage) -> page == 1);

        private final BiPredicate<Integer, Integer> condition;

        public boolean check(int page, int maxPage) {
            return condition.test(page, maxPage);
        }
    }
}

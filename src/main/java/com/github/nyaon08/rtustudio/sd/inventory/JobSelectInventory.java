package com.github.nyaon08.rtustudio.sd.inventory;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.configuration.JobConfig;
import com.github.nyaon08.rtustudio.sd.data.Job;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.format.ComponentFormatter;
import kr.rtuserver.framework.bukkit.api.inventory.RSInventory;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JobSelectInventory extends RSInventory<SDJobCraft> {

    Map<List<Integer>, String> slotItemMap;

    private final JobManager jobManager;

    @Getter
    private final Inventory inventory;
    private final Player player;

    public JobSelectInventory(SDJobCraft plugin, Player player) {
        super(plugin);
        JobConfig jobConfig = plugin.getJobConfig();
        this.slotItemMap = Map.of(
                List.of(0, 1, 2, 9, 10, 11, 18, 19, 20), "farmer",
                List.of(3, 4, 5, 12, 13, 14, 21, 22, 23), "fisher",
                List.of(6, 7, 8, 15, 16, 17, 24, 25, 26), "miner",
                List.of(27, 28, 29, 36, 37, 38, 45, 46, 47), "jeweler",
                List.of(30, 31, 32, 39, 40, 41, 48, 49, 50), "planned",
                List.of(33, 34, 35, 42, 43, 44, 51, 52, 53), "planned"
        );
        this.jobManager = plugin.getJobManager();
        this.player = player;

        Component title = ComponentFormatter.parse(jobConfig.getJobSelectInventoryTitle());
        this.inventory = createInventory(54, title);
        init();
    }

    private void init() {
        for (Map.Entry<List<Integer>, String> entry : slotItemMap.entrySet()) {
            for (int slot : entry.getKey()) {
                ItemStack itemStack = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.displayName(ComponentFormatter.parse(message().get(player, "icon.select." + entry.getValue())));
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(slot, itemStack);
            }
        }
    }

    @Override
    public boolean onClick(Event<InventoryClickEvent> event, RSInventory.Click click) {
        if (inventory.isEmpty() || event.isInventory()) return false;

        int slot = click.slot();
        if (slot < 0) return false;

        for (Map.Entry<List<Integer>, String> entry : slotItemMap.entrySet()) {
            if (entry.getKey().contains(slot)) {
                String job = entry.getValue();
                if (Objects.equals(job, "planned")) {
                    chat().announce(message().get(player, "error.planned-job"));
                    return false;
                }
                jobManager.setJob(player.getUniqueId(), new Job(Job.Type.valueOf(job.toUpperCase()), 0, 0, 0));
                chat().announce(message().get(player, "success.select-job")
                        .replace("[job]", message().get(player, "format.jobs." + job))
                );
                player.closeInventory();
                return false;
            }
        }

        return false;
    }

}

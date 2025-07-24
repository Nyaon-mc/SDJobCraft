package com.github.nyaon08.rtustudio.sd.inventory;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import kr.rtuserver.framework.bukkit.api.format.ComponentFormatter;
import kr.rtuserver.framework.bukkit.api.inventory.RSInventory;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.Inventory;

public class JobSelectInventory extends RSInventory<SDJobCraft> {

    @Getter
    private final Inventory inventory;

    public JobSelectInventory(SDJobCraft plugin) {
        super(plugin);

        Component title = ComponentFormatter.mini("직업선택");
        this.inventory = createInventory(9, title);
    }
}

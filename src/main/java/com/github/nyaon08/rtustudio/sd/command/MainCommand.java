package com.github.nyaon08.rtustudio.sd.command;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.configuration.JobConfig;
import com.github.nyaon08.rtustudio.sd.inventory.JobSelectInventory;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;

import java.util.List;

public class MainCommand extends RSCommand<SDJobCraft> {

    private final JobConfig jobConfig;

    public MainCommand(SDJobCraft plugin) {
        super(plugin, "job");
        this.jobConfig = plugin.getJobConfig();
    }

    @Override
    protected boolean execute(RSCommandData data) {
        player().openInventory(new JobSelectInventory(getPlugin()).getInventory());
        return true;
    }

    @Override
    protected List<String> tabComplete(RSCommandData data) {
        return List.of();
    }

    @Override
    protected void reload(RSCommandData data) {
        jobConfig.reload();
    }

}

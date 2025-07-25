package com.github.nyaon08.rtustudio.sd.command;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.configuration.JobConfig;
import com.github.nyaon08.rtustudio.sd.configuration.LevelingConfig;
import com.github.nyaon08.rtustudio.sd.data.Job;
import com.github.nyaon08.rtustudio.sd.inventory.JobInfoInventory;
import com.github.nyaon08.rtustudio.sd.inventory.JobSelectInventory;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;

import java.util.List;

public class MainCommand extends RSCommand<SDJobCraft> {

    private final JobConfig jobConfig;
    private final LevelingConfig levelingConfig;
    private final JobManager jobManager;

    public MainCommand(SDJobCraft plugin) {
        super(plugin, "job");
        this.jobConfig = plugin.getJobConfig();
        this.levelingConfig = plugin.getLevelingConfig();
        this.jobManager = plugin.getJobManager();

        registerCommand(new CheckJobCommand(plugin));
        registerCommand(new ResetJobCommand(plugin));
        registerCommand(new SetJobCommand(plugin));
    }

    @Override
    protected boolean execute(RSCommandData data) {
        Job job = jobManager.getJob(player().getUniqueId());
        if (job.job() != Job.Type.NONE) {
            chat().announce(message().get(player(), "success.check.self")
                    .replace("[job]", message().get(player(), "format.jobs." + job.job().toString().toLowerCase())));
            player().openInventory(new JobInfoInventory(getPlugin(), player()).getInventory());
            return true;
        }

        player().openInventory(new JobSelectInventory(getPlugin(), player()).getInventory());
        return true;
    }

    @Override
    protected List<String> tabComplete(RSCommandData data) {
        return List.of();
    }

    @Override
    protected void reload(RSCommandData data) {
        jobConfig.reload();
        levelingConfig.reload();
    }

}

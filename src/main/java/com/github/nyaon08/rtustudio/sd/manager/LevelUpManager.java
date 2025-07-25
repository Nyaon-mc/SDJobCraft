package com.github.nyaon08.rtustudio.sd.manager;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.configuration.LevelingConfig;
import com.github.nyaon08.rtustudio.sd.data.Job;
import org.bukkit.entity.Player;

public class LevelUpManager {

    private final SDJobCraft plugin;

    private final LevelingConfig levelingConfig;
    private final JobManager jobManager;

    public LevelUpManager(SDJobCraft plugin) {
        this.plugin = plugin;
        this.levelingConfig = plugin.getLevelingConfig();
        this.jobManager = plugin.getJobManager();
    }

    private void leveUp(Player player) {
        Job job = jobManager.getJob(player.getUniqueId());
    }

}

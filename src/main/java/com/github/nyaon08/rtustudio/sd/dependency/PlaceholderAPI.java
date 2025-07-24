package com.github.nyaon08.rtustudio.sd.dependency;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.integration.RSPlaceholder;
import org.bukkit.OfflinePlayer;

public class PlaceholderAPI extends RSPlaceholder<SDJobCraft> {

    private final JobManager jobManager;

    public PlaceholderAPI(SDJobCraft plugin) {
        super(plugin);
        this.jobManager = plugin.getJobManager();
    }

    @Override
    public String request(OfflinePlayer offlinePlayer, String[] params) {
        return switch (params[0]) {
            case "job" -> String.valueOf(jobManager.getJob(offlinePlayer.getUniqueId()).job());
            default -> "ERROR";
        };
    }

}

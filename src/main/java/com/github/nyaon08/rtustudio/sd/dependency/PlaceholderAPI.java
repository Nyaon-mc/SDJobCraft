package com.github.nyaon08.rtustudio.sd.dependency;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.data.Job;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.integration.RSPlaceholder;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends RSPlaceholder<SDJobCraft> {

    private final JobManager jobManager;

    public PlaceholderAPI(SDJobCraft plugin) {
        super(plugin);
        this.jobManager = plugin.getJobManager();
    }

    @Override
    public String request(OfflinePlayer offlinePlayer, String[] params) {
        switch (params[0]) {
            case "job":
                Job job = jobManager.getJob(offlinePlayer.getUniqueId());
                if (offlinePlayer instanceof Player player) {
                    return message().get(player, "format.jobs." + job.job().toString().toLowerCase());
                }
            default:
                return "ERROR";
        }
    }

}

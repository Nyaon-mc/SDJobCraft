package com.github.nyaon08.rtustudio.sd.listener;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.configuration.JobConfig;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

public class FisherSkillListener extends RSListener<SDJobCraft> {

    private final JobConfig jobConfig;
    private final JobManager jobManager;

    public FisherSkillListener(SDJobCraft plugin) {
        super(plugin);
        this.jobConfig = new JobConfig(plugin);
        this.jobManager = plugin.getJobManager();
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        if (!(event.getCaught() instanceof Item currentItem)) return;

        Player player = event.getPlayer();
        int level = jobManager.getJob(player.getUniqueId()).level();
        int probability = jobManager.getJob(player.getUniqueId()).probability();

        if (level >= 0 && Math.random() < probability) {
        }

        if (level >= 10 && Math.random() < probability) {
        }

        if (level >= 20 && Math.random() < probability) {
        }

        if (level >= 30 && Math.random() < probability) {
        }

        if (level >= 40 && Math.random() < probability) {
        }

    }

}

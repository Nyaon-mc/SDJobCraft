package com.github.nyaon08.rtustudio.sd.listener;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerConnectionListener extends RSListener<SDJobCraft> {

    private final JobManager jobManager;

    public PlayerConnectionListener(SDJobCraft plugin) {
        super(plugin);
        this.jobManager = plugin.getJobManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        jobManager.initPlayer(uuid);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
    }

}

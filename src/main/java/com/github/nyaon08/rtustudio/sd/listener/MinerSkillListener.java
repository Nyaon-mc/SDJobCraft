package com.github.nyaon08.rtustudio.sd.listener;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class MinerSkillListener extends RSListener<SDJobCraft> {

    private final JobManager jobManager;

    public MinerSkillListener(SDJobCraft plugin) {
        super(plugin);
        this.jobManager = plugin.getJobManager();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

    }

}

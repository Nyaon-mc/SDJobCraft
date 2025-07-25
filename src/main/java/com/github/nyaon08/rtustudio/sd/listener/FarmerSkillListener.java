package com.github.nyaon08.rtustudio.sd.listener;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;

public class FarmerSkillListener extends RSListener<SDJobCraft> {

    private final JobManager jobManager;

    public FarmerSkillListener(SDJobCraft plugin) {
        super(plugin);
        this.jobManager = plugin.getJobManager();
    }

}

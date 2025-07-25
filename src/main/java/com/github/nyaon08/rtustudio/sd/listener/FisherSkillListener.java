package com.github.nyaon08.rtustudio.sd.listener;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.configuration.JobConfig;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;

public class FisherSkillListener extends RSListener<SDJobCraft> {

    private final JobConfig jobConfig;
    private final JobManager jobManager;

    public FisherSkillListener(SDJobCraft plugin) {
        super(plugin);
        this.jobConfig = new JobConfig(plugin);
        this.jobManager = plugin.getJobManager();
    }

}

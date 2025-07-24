package com.github.nyaon08.rtustudio.sd.configuration;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import kr.rtuserver.framework.bukkit.api.configuration.RSConfiguration;

public class JobConfig extends RSConfiguration<SDJobCraft> {

    public JobConfig(SDJobCraft plugin) {
        super(plugin, "job.yml", 1);
        setup(this);
    }

    private void init() {

    }

}

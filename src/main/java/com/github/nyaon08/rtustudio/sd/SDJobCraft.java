package com.github.nyaon08.rtustudio.sd;

import com.github.nyaon08.rtustudio.sd.command.MainCommand;
import com.github.nyaon08.rtustudio.sd.configuration.JobConfig;
import com.github.nyaon08.rtustudio.sd.dependency.PlaceholderAPI;
import com.github.nyaon08.rtustudio.sd.listener.PlayerConnectionListener;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.RSPlugin;
import lombok.Getter;

public class SDJobCraft extends RSPlugin {

    @Getter
    private static SDJobCraft instance;

    @Getter
    private JobConfig jobConfig;

    @Getter
    private JobManager jobManager;

    private PlaceholderAPI placeholder;

    @Override
    public void load() {
        instance = this;
    }

    @Override
    public void enable() {
        getConfigurations().getStorage().init("jobs");
        getConfigurations().getStorage().init("points");

        jobConfig = new JobConfig(this);

        jobManager = new JobManager(this);
        registerEvent(new PlayerConnectionListener(this));

        registerCommand(new MainCommand(this), true);

        if (getFramework().isEnabledDependency("PlaceholderAPI")) {
            placeholder = new PlaceholderAPI(this);
            placeholder.register();
        }
    }

    @Override
    public void disable() {
        if (getFramework().isEnabledDependency("PlaceholderAPI")) {
            placeholder.unregister();
        }
    }

}

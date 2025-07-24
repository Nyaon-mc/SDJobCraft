package com.github.nyaon08.rtustudio.sd;

import com.github.nyaon08.rtustudio.sd.command.MainCommand;
import kr.rtuserver.framework.bukkit.api.RSPlugin;
import lombok.Getter;

public class SDJobCraft extends RSPlugin {

    @Getter
    private static SDJobCraft instance;

    @Override
    public void load() {
        instance = this;
    }

    @Override
    public void enable() {
        getConfigurations().getStorage().init("playtime");

        registerCommand(new MainCommand(this), true);

    }

    @Override
    public void disable() {
    }

}

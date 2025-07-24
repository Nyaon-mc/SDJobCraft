package com.github.nyaon08.rtustudio.sd.command;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;

import java.util.List;

public class MainCommand extends RSCommand<SDJobCraft> {

    public MainCommand(SDJobCraft plugin) {
        super(plugin, "playtime");
    }

    @Override
    protected boolean execute(RSCommandData data) {
        return true;
    }

    @Override
    protected List<String> tabComplete(RSCommandData data) {
        return List.of();
    }

    @Override
    protected void reload(RSCommandData data) {
    }

}

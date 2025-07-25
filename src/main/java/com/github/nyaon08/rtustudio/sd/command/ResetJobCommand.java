package com.github.nyaon08.rtustudio.sd.command;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.data.Job;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;
import kr.rtuserver.framework.bukkit.api.configuration.translation.message.MessageTranslation;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

public class ResetJobCommand extends RSCommand<SDJobCraft> {

    private final JobManager jobManager;

    public ResetJobCommand(SDJobCraft plugin) {
        super(plugin, "reset", PermissionDefault.OP);
        this.jobManager = plugin.getJobManager();
    }

    @Override
    protected boolean execute(RSCommandData data) {
        Player target = provider().getPlayer(data.args(1));
        if (target == null) {
            chat().announce(message().getCommon(player(), String.valueOf(MessageTranslation.NOT_FOUND_ONLINE_PLAYER)));
            return true;
        }

        jobManager.setJob(target.getUniqueId(), new Job(Job.Type.NONE, 0, 0, 0));
        chat().announce(message().get(player(), "success.reset.sender").replace("[player]", target.getName()));
        chat().announce(target, message().get(player(), "success.reset.receiver"));
        return true;
    }

    @Override
    protected List<String> tabComplete(RSCommandData data) {
        if (data.length(2)) return provider().getNames();
        return List.of();
    }

}

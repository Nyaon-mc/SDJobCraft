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

public class CheckJobCommand extends RSCommand<SDJobCraft> {

    private final JobManager jobManager;

    public CheckJobCommand(SDJobCraft plugin) {
        super(plugin, "check", PermissionDefault.OP);
        this.jobManager = plugin.getJobManager();
    }

    @Override
    protected boolean execute(RSCommandData data) {
        Player target = provider().getPlayer(data.args(1));
        if (target == null) {
            chat().announce(message().getCommon(player(), String.valueOf(MessageTranslation.NOT_FOUND_ONLINE_PLAYER)));
            return true;
        }

        Job job = jobManager.getJob(target.getUniqueId());
        String jobName = message().get(player(), "format.jobs." + job.job().toString().toLowerCase());

        chat().announce(message().get(player(), "success.check.other")
                .replace("[player]", target.getName())
                .replace("[job]", jobName)
        );
        return true;
    }

    @Override
    protected List<String> tabComplete(RSCommandData data) {
        if (data.length(2)) return provider().getNames();
        return List.of();
    }

}

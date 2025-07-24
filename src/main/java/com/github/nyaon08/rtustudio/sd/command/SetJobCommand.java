package com.github.nyaon08.rtustudio.sd.command;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.data.Job;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;
import kr.rtuserver.framework.bukkit.api.configuration.translation.message.MessageTranslation;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import java.util.*;
import java.util.stream.Collectors;

public class SetJobCommand extends RSCommand<SDJobCraft> {

    private final JobManager jobManager;

    public SetJobCommand(SDJobCraft plugin) {
        super(plugin, "set", PermissionDefault.OP);
        this.jobManager = plugin.getJobManager();
    }

    @Override
    protected boolean execute(RSCommandData data) {
        Player target = provider().getPlayer(data.args(1));
        if (target == null) {
            chat().announce(message().getCommon(player(), String.valueOf(MessageTranslation.NOT_FOUND_ONLINE_PLAYER)));
            return true;
        }

        Map<String, Job.Type> nameToType = Arrays.stream(Job.Type.values())
                .collect(Collectors.toMap(
                        type -> message().get(player(), "format.jobs." + type.toString().toLowerCase()),
                        type -> type,
                        (existing, replacement) -> existing
                ));

        Job.Type type = nameToType.get(data.args(2));
        if (type == null) {
            chat().announce(message().get(player(), "error.invalid-job"));
            return true;
        }

        Job job = new Job(type, 0);
        jobManager.setJob(target.getUniqueId(), job);

        String replaceJobName = message().get(player(), "format.jobs." + type.toString().toLowerCase());
        chat().announce(message().get(player(), "success.set.sender")
                .replace("[player]", target.getName())
                .replace("[job]", replaceJobName));

        chat().announce(message().get(player(), "success.set.receiver")
                .replace("[job]", replaceJobName));

        return true;
    }

    @Override
    protected List<String> tabComplete(RSCommandData data) {
        if (data.length(2)) return provider().getNames();
        if (data.length(3)) {
            return Arrays.stream(Job.Type.values())
                    .map(type -> message().get(player(), "format.jobs." + type.toString().toLowerCase()))
                    .collect(Collectors.toList());
        }
        return List.of();
    }

}

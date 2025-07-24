package com.github.nyaon08.rtustudio.sd.manager;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.data.Job;
import kr.rtuserver.framework.bukkit.api.platform.JSON;
import kr.rtuserver.framework.bukkit.api.storage.Storage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class JobManager {

    private final SDJobCraft plugin;

    @Getter
    private final Map<UUID, Job> jobs = new HashMap<>();

    public void initPlayer(UUID uuid) {
        Storage storage = plugin.getStorage();
        storage.get("jobs", JSON.of("uuid", uuid.toString())).thenAccept(result -> {
            if (result.isEmpty() || result.getFirst().isJsonNull()) {
                storage.add("jobs", JSON.of("uuid", uuid.toString()));
            }
        });
    }

    public void setJob(UUID uuid, Job job) {
        Storage storage = plugin.getStorage();
        storage.set("jobs", JSON.of("uuid", uuid.toString()),
                JSON.of("job", job.job()).append("level", job.level()));
        jobs.put(uuid, job);
    }

    public Job getJob(UUID uuid) {
        if (jobs.containsKey(uuid)) return jobs.get(uuid);
        Storage storage = plugin.getStorage();
        return storage.get("jobs", JSON.of("uuid", uuid.toString())).thenApply(result -> {
            String currentJob = result.getFirst().get("job").getAsString();
            int currentLevel = result.getFirst().get("level").getAsInt();
            Job job = new Job(currentJob, currentLevel);
            jobs.put(uuid, job);
            return job;
        }).join();
    }

    public void addLevel(UUID uuid, int level) {
        Storage storage = plugin.getStorage();
        storage.get("jobs", JSON.of("uuid", uuid.toString())).thenAccept(result -> {
            int currentLevel = result.getFirst().get("level").getAsInt();
            storage.set("jobs", JSON.of("uuid", uuid.toString()), JSON.of("level", currentLevel + level));
        });
    }

}

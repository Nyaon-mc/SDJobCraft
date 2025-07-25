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
                storage.add("jobs", JSON.of("uuid", uuid.toString())
                        .append("job", String.valueOf(Job.Type.NONE))
                        .append("level", 0)
                        .append("ep", 0)
                        .append("probability", 0));
            }
        });
    }

    public void setJob(UUID uuid, Job job) {
        Storage storage = plugin.getStorage();
        storage.set("jobs", JSON.of("uuid", uuid.toString()),
                JSON.of("job", String.valueOf(job.job())).append("level", job.level()));
        jobs.put(uuid, job);
    }

    public Job getJob(UUID uuid) {
        if (jobs.containsKey(uuid)) return jobs.get(uuid);
        Storage storage = plugin.getStorage();
        return storage.get("jobs", JSON.of("uuid", uuid.toString())).thenApply(result -> {
            if (result.isEmpty() || result.getFirst().isJsonNull()) return new Job(Job.Type.NONE, 0, 0, 0);
            String currentJob = result.getFirst().get("job").getAsString();
            int currentLevel = result.getFirst().get("level").getAsInt();
            int currentEp = result.getFirst().get("ep").getAsInt();
            int currentProbability = result.getFirst().get("probability").getAsInt();
            Job job = new Job(Job.Type.valueOf(currentJob), currentLevel, currentEp, currentProbability);
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

    public void addEp(UUID uuid, int ep) {
        Storage storage = plugin.getStorage();
        storage.get("jobs", JSON.of("uuid", uuid.toString())).thenAccept(result -> {
            int currentEp = result.getFirst().get("ep").getAsInt();
            storage.set("jobs", JSON.of("uuid", uuid.toString()), JSON.of("ep", currentEp + ep));
        });
    }

    public void removeEp(UUID uuid, int ep) {
        Storage storage = plugin.getStorage();
        storage.get("jobs", JSON.of("uuid", uuid.toString())).thenAccept(result -> {
            int currentEp = result.getFirst().get("ep").getAsInt();
            storage.set("jobs", JSON.of("uuid", uuid.toString()), JSON.of("ep", currentEp - ep));
        });
    }

    public void addProbability(UUID uuid, int probability) {
        Storage storage = plugin.getStorage();
        storage.get("jobs", JSON.of("uuid", uuid.toString())).thenAccept(result -> {
            int currentProbability = result.getFirst().get("probability").getAsInt();
            storage.set("jobs", JSON.of("uuid", uuid.toString()), JSON.of("probability", currentProbability + probability));
        });
    }

    public int getProbability(UUID uuid) {
        Storage storage = plugin.getStorage();
        return storage.get("jobs", JSON.of("uuid", uuid.toString())).thenApply(result -> {
            if (result.isEmpty() || result.getFirst().isJsonNull()) return 0;
            return result.getFirst().get("probability").getAsInt() * 10;
        }).join();
    }

}

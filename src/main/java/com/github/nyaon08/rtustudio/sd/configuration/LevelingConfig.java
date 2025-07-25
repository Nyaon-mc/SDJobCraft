package com.github.nyaon08.rtustudio.sd.configuration;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.data.Leveling;
import kr.rtuserver.framework.bukkit.api.configuration.RSConfiguration;
import kr.rtuserver.framework.yaml.configuration.ConfigurationSection;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LevelingConfig extends RSConfiguration<SDJobCraft> {

    private final Map<String, Leveling> leveling = new HashMap<>();

    public LevelingConfig(SDJobCraft plugin) {
        super(plugin, "leveling.yml", 1);
        setup(this);
    }

    private void init() {
        ConfigurationSection section = getConfigurationSection("level_up_ep_cost");
        for (String key : section.getKeys(false)) {
            ConfigurationSection section2 = section.getConfigurationSection(key);
            for (String key2 : section2.getKeys(false)) {
                ConfigurationSection section3 = section2.getConfigurationSection(key2);
                Leveling leveling = new Leveling(
                        Integer.parseInt(key2),
                        section3.getInt("chance"),
                        section3.getInt("ep_cost")
                );
                this.leveling.put(key, leveling);
            }
        }
    }

}

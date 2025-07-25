package com.github.nyaon08.rtustudio.sd;

import com.github.nyaon08.rtustudio.sd.command.MainCommand;
import com.github.nyaon08.rtustudio.sd.configuration.IconConfig;
import com.github.nyaon08.rtustudio.sd.configuration.JobConfig;
import com.github.nyaon08.rtustudio.sd.configuration.LevelingConfig;
import com.github.nyaon08.rtustudio.sd.dependency.PlaceholderAPI;
import com.github.nyaon08.rtustudio.sd.listener.FarmerSkillListener;
import com.github.nyaon08.rtustudio.sd.listener.MinerSkillListener;
import com.github.nyaon08.rtustudio.sd.listener.PlayerConnectionListener;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import com.github.nyaon08.rtustudio.sd.manager.LevelUpManager;
import com.github.nyaon08.rtustudio.sd.payment.Payment;
import com.github.nyaon08.rtustudio.sd.payment.VaultPayment;
import kr.rtuserver.framework.bukkit.api.RSPlugin;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class SDJobCraft extends RSPlugin {

    @Getter
    private static SDJobCraft instance;

    @Getter
    private IconConfig iconConfig;

    @Getter
    private JobConfig jobConfig;

    @Getter
    private LevelingConfig levelingConfig;

    @Getter
    private JobManager jobManager;

    @Getter
    private LevelUpManager levelUpManager;

    @Getter
    private Economy economy;

    @Getter
    private LuckPerms luckPerms;

    @Getter
    private Payment payment;

    private PlaceholderAPI placeholder;

    @Override
    public void load() {
        instance = this;
    }

    @Override
    public void enable() {
        getConfigurations().getStorage().init("jobs");

        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        RegisteredServiceProvider<LuckPerms> permProvider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (permProvider != null) {
            luckPerms = permProvider.getProvider();
        }

        payment = new VaultPayment(this);

        iconConfig = new IconConfig(this);
        jobConfig = new JobConfig(this);
        levelingConfig = new LevelingConfig(this);

        jobManager = new JobManager(this);
        levelUpManager = new LevelUpManager(this);

        registerCommand(new MainCommand(this), true);
        registerEvent(new PlayerConnectionListener(this));
        registerEvent(new FarmerSkillListener(this));
        registerEvent(new MinerSkillListener(this));

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

package com.github.nyaon08.rtustudio.sd;

import com.github.nyaon08.rtustudio.sd.command.MainCommand;
import com.github.nyaon08.rtustudio.sd.configuration.IconConfig;
import com.github.nyaon08.rtustudio.sd.configuration.JobConfig;
import com.github.nyaon08.rtustudio.sd.configuration.LevelingConfig;
import com.github.nyaon08.rtustudio.sd.dependency.PlaceholderAPI;
import com.github.nyaon08.rtustudio.sd.listener.PlayerConnectionListener;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import com.github.nyaon08.rtustudio.sd.payment.Payment;
import com.github.nyaon08.rtustudio.sd.payment.VaultPayment;
import kr.rtuserver.framework.bukkit.api.RSPlugin;
import lombok.Getter;
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
    private Economy economy;

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

        RegisteredServiceProvider<Economy> provider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (provider != null) {
            economy = provider.getProvider();
        }

        payment = new VaultPayment(this);

        iconConfig = new IconConfig(this);
        jobConfig = new JobConfig(this);
        levelingConfig = new LevelingConfig(this);

        jobManager = new JobManager(this);

        registerCommand(new MainCommand(this), true);
        registerEvent(new PlayerConnectionListener(this));

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

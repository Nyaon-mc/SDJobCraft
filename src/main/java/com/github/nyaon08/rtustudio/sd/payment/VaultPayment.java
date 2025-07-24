package com.github.nyaon08.rtustudio.sd.payment;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class VaultPayment implements Payment {

    private final Economy economy;

    public VaultPayment(SDJobCraft plugin) {
        this.economy = plugin.getEconomy();
    }

    @Override
    public boolean deposit(Player player, long price) {
        return economy.depositPlayer(player, price).transactionSuccess();
    }

    @Override
    public boolean withdraw(Player player, long price) {
        return economy.withdrawPlayer(player, price).transactionSuccess();
    }

}

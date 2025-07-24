package com.github.nyaon08.rtustudio.sd.payment;

import org.bukkit.entity.Player;

public interface Payment {

    boolean deposit(Player player, long price);

    boolean withdraw(Player player, long price);

}

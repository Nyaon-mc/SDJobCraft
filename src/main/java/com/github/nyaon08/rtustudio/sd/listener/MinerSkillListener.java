package com.github.nyaon08.rtustudio.sd.listener;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.configuration.JobConfig;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.core.scheduler.Scheduler;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import kr.rtuserver.framework.bukkit.api.registry.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MinerSkillListener extends RSListener<SDJobCraft> {

    private final JobConfig jobConfig;
    private final JobManager jobManager;

    private final Map<UUID, Boolean> cooldown = new HashMap<>();

    public MinerSkillListener(SDJobCraft plugin) {
        super(plugin);
        jobConfig = new JobConfig(plugin);
        this.jobManager = plugin.getJobManager();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        int level = jobManager.getJob(player.getUniqueId()).level();
        int probability = jobManager.getJob(player.getUniqueId()).probability();

        if (jobConfig.getFarmWorld().contains(block.getWorld().getName())) {
            if (level >= 0 && Math.random() < probability) {
                ItemStack itemStack = CustomItems.from(jobConfig.getMinerCompressedItem());
                if (itemStack == null) return;
                player.getInventory().addItem(itemStack);
            }

            if (level >= 10 && Math.random() < probability) {
                harvestFiveOresInFront(player, block);
            }
        }

        if (jobConfig.getGlobalWorld().contains(player.getWorld().getName())) {
            if (level >= 20 && Math.random() < probability) {
                if (!cooldown.getOrDefault(player.getUniqueId(), false)) {
                    cooldown.put(player.getUniqueId(), true);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 2));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 20, 2));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 20, 2));

                    Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                        cooldown.put(player.getUniqueId(), false);
                    }, 15);
                }
            }
        }

        if (jobConfig.getIrisWorld().contains(block.getWorld().getName())) {
            if (level >= 30 && Math.random() < probability) {
                mine3x3(player, block);
            }

            if (level >= 40 && Math.random() < probability) {
                ItemStack itemStack1 = CustomItems.from(jobConfig.getMinerSpecialItem());
                ItemStack itemStack2 = CustomItems.from(jobConfig.getMinerLifeCoinItem());

                if (itemStack1 == null || itemStack2 == null) return;
                player.getInventory().addItem(itemStack1);
                player.getInventory().addItem(itemStack2);
            }
        }

    }

    private void harvestFiveOresInFront(Player player, Block block) {
        BlockFace face = player.getFacing();
        for (int i = 0; i < 5; i++) {
            Block targetBlock = block.getRelative(face, i);
            if (targetBlock.getType().toString().contains("ORE")) {
                targetBlock.breakNaturally(player.getInventory().getItemInMainHand());
            }
        }
    }

    private void mine3x3(Player player, Block block) {
        BlockFace face = player.getFacing();
        Block targetBlock = block.getRelative(face, 1);

        Location location = targetBlock.getLocation();
        int cx = location.getBlockX();
        int cy = location.getBlockY();
        int cz = location.getBlockZ();

        targetBlock.breakNaturally(player.getInventory().getItemInMainHand());

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                Block b = targetBlock.getWorld().getBlockAt(cx + dx, cy, cz + dz);
                if (b.getType().isBlock() && !b.getType().isAir()) {
                    b.breakNaturally(player.getInventory().getItemInMainHand());
                }
            }
        }
    }

}

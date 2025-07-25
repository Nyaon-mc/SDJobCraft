package com.github.nyaon08.rtustudio.sd.listener;

import com.github.nyaon08.rtustudio.sd.SDJobCraft;
import com.github.nyaon08.rtustudio.sd.configuration.JobConfig;
import com.github.nyaon08.rtustudio.sd.manager.JobManager;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import kr.rtuserver.framework.bukkit.api.registry.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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

public class FarmerSkillListener extends RSListener<SDJobCraft> {

    private final JobConfig jobConfig;
    private final JobManager jobManager;

    private final Map<UUID, Boolean> cooldown = new HashMap<>();

    public FarmerSkillListener(SDJobCraft plugin) {
        super(plugin);
        this.jobConfig = new JobConfig(plugin);
        this.jobManager = plugin.getJobManager();
    }

    @EventHandler
    public void onCropHarvest(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (!isCrop(block.getType())) return;

        int level = jobManager.getJob(player.getUniqueId()).level();
        int probability = jobManager.getJob(player.getUniqueId()).probability();

        if (level >= 0 && Math.random() < probability) {
            ItemStack itemStack = CustomItems.from(jobConfig.getFarmerCompressedItem());
            if (itemStack == null) return;
            player.getInventory().addItem(itemStack);
        }

        if (level >= 10 && Math.random() < probability) {
            if (!player.getInventory().getItemInMainHand().getType().toString().contains("HOE")) return;
            harvestFiveCropsInFront(player, block);
        }

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

        if (level >= 30 && Math.random() < probability) {
            // TODO: 자동줍기 미차감
        }

        if (level >= 40 && Math.random() < probability) {
            ItemStack itemStack = CustomItems.from(jobConfig.getFarmerOrganicItem());
            if (itemStack == null) return;
            player.getInventory().addItem(itemStack);
        }
    }

    private boolean isCrop(Material type) {
        return switch (type) {
            case Material.WHEAT, Material.SWEET_BERRIES, Material.CARROTS, Material.POTATOES, Material.BEETROOTS,
                 Material.MELON_STEM, Material.PUMPKIN_STEM -> true;
            default -> false;
        };
    }

    private void harvestFiveCropsInFront(Player player, Block block) {
        BlockFace face = player.getFacing();
        for (int i = 0; i < 5; i++) {
            Block targetBlock = block.getRelative(face, i);
            if (isCrop(targetBlock.getType())) {
                targetBlock.breakNaturally(player.getInventory().getItemInMainHand());
            }
        }
    }

}

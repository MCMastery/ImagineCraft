package net.imaginecraft.plugin.listeners;

import net.imaginecraft.plugin.Claim;
import net.imaginecraft.plugin.ClaimManager;
import net.imaginecraft.plugin.Player;
import net.imaginecraft.plugin.listeners.internal.CancellableEvent;
import net.imaginecraft.plugin.listeners.internal.blockbreak.BlockBreakEvent;
import net.imaginecraft.plugin.listeners.internal.blockbreak.BlockBreakListener;
import net.imaginecraft.plugin.listeners.internal.blockplace.BlockPlaceEvent;
import net.imaginecraft.plugin.listeners.internal.blockplace.BlockPlaceListener;
import net.imaginecraft.plugin.listeners.internal.bucketempty.BucketEmptyEvent;
import net.imaginecraft.plugin.listeners.internal.bucketempty.BucketEmptyListener;
import net.imaginecraft.plugin.listeners.internal.bucketfill.BucketFillEvent;
import net.imaginecraft.plugin.listeners.internal.bucketfill.BucketFillListener;
import net.imaginecraft.plugin.listeners.internal.entitybreakhanging.EntityBreakHangingEvent;
import net.imaginecraft.plugin.listeners.internal.entitybreakhanging.EntityBreakHangingListener;
import net.imaginecraft.plugin.listeners.internal.entitydamageentity.EntityDamageEntity;
import net.imaginecraft.plugin.listeners.internal.entitydamageentity.EntityDamageEntityListener;
import net.imaginecraft.plugin.listeners.internal.explosion.ExplosionEvent;
import net.imaginecraft.plugin.listeners.internal.explosion.ExplosionListener;
import net.imaginecraft.plugin.listeners.internal.liquidflow.BlockMoveEvent;
import net.imaginecraft.plugin.listeners.internal.liquidflow.BlockMoveListener;
import net.imaginecraft.plugin.listeners.internal.playerinteract.PlayerInteractEvent;
import net.imaginecraft.plugin.listeners.internal.playerinteract.PlayerInteractListener;
import net.imaginecraft.plugin.listeners.internal.playerinteractentity.PlayerInteractWithEntityEvent;
import net.imaginecraft.plugin.listeners.internal.playerinteractentity.PlayerInteractWithEntityListener;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;

public class ClaimProtector implements BlockPlaceListener,
        BlockBreakListener,
        BucketEmptyListener,
        BucketFillListener,
        BlockMoveListener,
        ExplosionListener,
        PlayerInteractListener,
        EntityDamageEntityListener,
        EntityBreakHangingListener,
        PlayerInteractWithEntityListener {

    private boolean check(Location location) {
        Claim claim = ClaimManager.getClaimContaining(location);
        return claim != null;
    }
    private void checkAndProtect(CancellableEvent evt, Location location, Player player) {
        Claim claim = ClaimManager.getClaimContaining(location);
        if (claim != null) {
            if (player != null)
                player.sendFormattedMessage("&cThis land is owned by " + Player.getPlayerName(claim.getOwner()) + "!");
            evt.setCancelled(true);
        }
    }



    @Override
    public void onBlockPlace(BlockPlaceEvent evt) {
        checkAndProtect(evt, evt.getBlock().getLocation(), evt.getPlayer());
    }

    @Override
    public void onBucketEmpty(BucketEmptyEvent evt) {
        checkAndProtect(evt, evt.getBlock().getLocation(), evt.getPlayer());
    }

    @Override
    public void onBucketFill(BucketFillEvent evt) {
        checkAndProtect(evt, evt.getBlock().getLocation(), evt.getPlayer());
    }

    @Override
    public void onBlockBreak(BlockBreakEvent evt) {
        checkAndProtect(evt, evt.getBlock().getLocation(), evt.getPlayer());
    }

    @Override
    public void onBlockMove(BlockMoveEvent evt) {
        checkAndProtect(evt, evt.getTo().getLocation(), null);
    }

    @Override
    public void onExplosion(ExplosionEvent evt) {
        checkAndProtect(evt, evt.getLocation(), null);
        for (Block block : new ArrayList<>(evt.getAffectedBlocks()))
            if (check(block.getLocation()))
                evt.getAffectedBlocks().remove(block);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent evt) {
        checkAndProtect(evt, evt.getBlock().getLocation(), evt.getPlayer());
    }

    @Override
    public void onEntityDamageEntity(EntityDamageEntity evt) {
        if (evt.getDamager() instanceof org.bukkit.entity.Player)
            checkAndProtect(evt, evt.getEntity().getLocation(), Player.getPlayer((org.bukkit.entity.Player) evt.getDamager()));
    }

    @Override
    public void onEntityBreakHanging(EntityBreakHangingEvent evt) {
        if (evt.getEntity() instanceof org.bukkit.entity.Player)
            checkAndProtect(evt, evt.getHanging().getLocation(), Player.getPlayer((org.bukkit.entity.Player) evt.getEntity()));
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractWithEntityEvent evt) {
        checkAndProtect(evt, evt.getEntity().getLocation(), evt.getPlayer());
    }
}

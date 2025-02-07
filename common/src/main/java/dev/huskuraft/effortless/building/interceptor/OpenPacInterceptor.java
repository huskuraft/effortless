package dev.huskuraft.effortless.building.interceptor;

import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.World;
import dev.huskuraft.effortless.api.platform.ClientEntrance;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.plugin.openpac.OpenPacChunkClaimsManager;
import dev.huskuraft.effortless.api.plugin.openpac.OpenPacClientPlugin;
import dev.huskuraft.effortless.api.plugin.openpac.OpenPacPlugin;

public final class OpenPacInterceptor implements BuildInterceptor {

    private final OpenPacChunkClaimsManager openPacChunkClaimsManager;

    public OpenPacInterceptor(
            OpenPacChunkClaimsManager openPacChunkClaimsManager
    ) {
        this.openPacChunkClaimsManager = openPacChunkClaimsManager;
    }

    public OpenPacInterceptor(
            Entrance entrance
    ) {
        if (entrance instanceof ClientEntrance) {
            var plugin = entrance.findPlugin(OpenPacClientPlugin.class);
            this.openPacChunkClaimsManager = plugin.map(OpenPacClientPlugin::getClaimManager).orElse(null);
        } else {
            var plugin = entrance.findPlugin(OpenPacPlugin.class);
            this.openPacChunkClaimsManager = plugin.map(openPacPlugin -> openPacPlugin.getServerClaimManager(entrance.getServer())).orElse(null);
        }
    }

    public OpenPacChunkClaimsManager getChunkClaimsManager() {
        return openPacChunkClaimsManager;
    }

    @Override
    public boolean isEnabled() {
        return openPacChunkClaimsManager != null;
    }

    @Override
    public boolean allowInteraction(Player player, World world, BlockPosition blockPosition) {
        if (!isEnabled()) {
            return true;
        }
        var claim = getChunkClaimsManager().get(world.getDimensionId().location(), blockPosition);
        return claim == null || claim.getPlayerId().equals(player.getId());
    }
}

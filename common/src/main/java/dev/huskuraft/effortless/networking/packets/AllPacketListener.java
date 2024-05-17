package dev.huskuraft.effortless.networking.packets;

import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.networking.PacketListener;
import dev.huskuraft.effortless.networking.packets.player.PlayerBuildPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerBuildPreviewPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerBuildTooltipPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerCommandPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerOperatorCheckPacket;
import dev.huskuraft.effortless.networking.packets.player.PlayerSettingsPacket;
import dev.huskuraft.effortless.networking.packets.session.SessionConfigPacket;
import dev.huskuraft.effortless.networking.packets.session.SessionPacket;

public interface AllPacketListener extends PacketListener {

    void handle(PlayerCommandPacket packet, Player player);

    void handle(PlayerBuildPacket packet, Player player);

    void handle(PlayerSettingsPacket packet, Player player);

    void handle(PlayerBuildPreviewPacket packet, Player player);

    void handle(PlayerOperatorCheckPacket packet, Player player);

    void handle(PlayerBuildTooltipPacket packet, Player player);

    void handle(SessionPacket packet, Player player);

    void handle(SessionConfigPacket packet, Player player);

}

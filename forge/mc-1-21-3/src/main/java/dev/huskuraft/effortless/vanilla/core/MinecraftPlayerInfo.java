package dev.huskuraft.effortless.vanilla.core;

import dev.huskuraft.effortless.api.core.PlayerInfo;
import dev.huskuraft.effortless.api.core.PlayerProfile;
import dev.huskuraft.effortless.api.core.PlayerSkin;
import dev.huskuraft.effortless.api.text.Text;

public record MinecraftPlayerInfo(
        net.minecraft.client.multiplayer.PlayerInfo refs
) implements PlayerInfo {

    @Override
    public PlayerProfile getProfile() {
        return new MinecraftPlayerProfile(refs.getProfile());
    }

    @Override
    public Text getDisplayName() {
        return MinecraftText.ofNullable(refs.getTabListDisplayName());
    }

    @Override
    public PlayerSkin getSkin() {
        var skin = refs.getSkin();
        return new PlayerSkin(
                MinecraftResourceLocation.ofNullable(skin.texture()),
                MinecraftResourceLocation.ofNullable(skin.capeTexture()),
                MinecraftResourceLocation.ofNullable(skin.elytraTexture()),
                switch (skin.model()) {
                    case SLIM -> PlayerSkin.Model.SLIM;
                    case WIDE -> PlayerSkin.Model.WIDE;
                }
        );
    }
}

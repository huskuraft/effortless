package dev.huskuraft.effortless.screen.settings;

import dev.huskuraft.effortless.EffortlessClient;
import dev.huskuraft.effortless.api.gui.AbstractPanelScreen;
import dev.huskuraft.effortless.api.gui.button.Button;
import dev.huskuraft.effortless.api.gui.text.TextWidget;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.text.ChatFormatting;
import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.networking.packets.player.PlayerOperatorCheckPacket;
import dev.huskuraft.effortless.screen.general.EffortlessGeneralSettingsScreen;
import dev.huskuraft.effortless.screen.pattern.EffortlessPatternSettingsScreen;
import dev.huskuraft.effortless.screen.preview.EffortlessRenderSettingsScreen;
import dev.huskuraft.effortless.screen.transformer.EffortlessTransformerPresetsScreen;

public class EffortlessSettingsScreen extends AbstractPanelScreen {

    public EffortlessSettingsScreen(Entrance entrance) {
        super(entrance, Text.translate("effortless.settings.title"), AbstractPanelScreen.PANEL_WIDTH, AbstractPanelScreen.PANEL_TITLE_HEIGHT_1 + AbstractPanelScreen.PANEL_BUTTON_ROW_HEIGHT_5);
    }

    @Override
    protected EffortlessClient getEntrance() {
        return (EffortlessClient) super.getEntrance();
    }

    @Override
    public void onCreate() {
        addWidget(new TextWidget(getEntrance(), getLeft() + getWidth() / 2, getTop() + AbstractPanelScreen.PANEL_TITLE_HEIGHT_1 - 10, getScreenTitle().withStyle(ChatFormatting.DARK_GRAY), TextWidget.Gravity.CENTER));

        addWidget(Button.builder(getEntrance(), Text.translate("effortless.general_settings.title"), button -> {
            if (!getEntrance().getSessionManager().isSessionValid()) {
                getEntrance().getClient().execute(() -> {
                    new EffortlessSessionStatusScreen(getEntrance()).attach();
                });
            } else {
                getEntrance().getChannel().sendPacket(new PlayerOperatorCheckPacket(getEntrance().getClient().getPlayer().getId()), (packet) -> {
                    if (packet.isOperator()) {
                        getEntrance().getClient().execute(() -> {
                            new EffortlessGeneralSettingsScreen(getEntrance()).attach();
                        });
                    } else {
                        getEntrance().getClient().execute(() -> {
                            new EffortlessNotAnOperatorScreen(getEntrance()).attach();
                        });
                    }
                });
            }
        }).setBoundsGrid(getLeft(), getTop(), getWidth(), getHeight(), 4f, 0f, 1f).build());
        addWidget(Button.builder(getEntrance(), Text.translate("effortless.render_settings.title"), button -> {
            new EffortlessRenderSettingsScreen(getEntrance()).attach();
        }).setBoundsGrid(getLeft(), getTop(), getWidth(), getHeight(), 3f, 0f, 1f).build());
        addWidget(Button.builder(getEntrance(), Text.translate("effortless.pattern_settings.title"), button -> {
            new EffortlessPatternSettingsScreen(getEntrance()).attach();
        }).setBoundsGrid(getLeft(), getTop(), getWidth(), getHeight(), 2f, 0f, 1f).build());
        addWidget(Button.builder(getEntrance(), Text.translate("effortless.transformer_presets.title"), button -> {
            new EffortlessTransformerPresetsScreen(getEntrance()).attach();
        }).setBoundsGrid(getLeft(), getTop(), getWidth(), getHeight(), 1f, 0f, 1f).build());

        addWidget(Button.builder(getEntrance(), Text.translate("effortless.button.done"), button -> {
            detach();
        }).setBoundsGrid(getLeft(), getTop(), getWidth(), getHeight(), 0f, 0f, 1f).build());

    }

}

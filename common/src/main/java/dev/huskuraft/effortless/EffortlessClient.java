package dev.huskuraft.effortless;

import com.google.auto.service.AutoService;

import dev.huskuraft.effortless.api.events.ClientEventRegistry;
import dev.huskuraft.effortless.api.events.EventRegister;
import dev.huskuraft.effortless.api.platform.ClientEntrance;

@AutoService(ClientEntrance.class)
public class EffortlessClient implements ClientEntrance {

    private final ClientEventRegistry eventRegistry = (ClientEventRegistry) EventRegister.getClient();
    private final EffortlessClientNetworkChannel channel = new EffortlessClientNetworkChannel(this);
    private final EffortlessClientStructureBuilder structureBuilder = new EffortlessClientStructureBuilder(this);
    private final EffortlessClientManager clientManager = new EffortlessClientManager(this);
    private final EffortlessClientConfigStorage configStorage = new EffortlessClientConfigStorage(this);

    public static EffortlessClient getInstance() {
        return (EffortlessClient) ClientEntrance.getInstance();
    }

    public ClientEventRegistry getEventRegistry() {
        return eventRegistry;
    }

    public EffortlessClientNetworkChannel getChannel() {
        return channel;
    }

    public EffortlessClientStructureBuilder getStructureBuilder() {
        return structureBuilder;
    }

    @Override
    public EffortlessClientManager getClientManager() {
        return clientManager;
    }

    public EffortlessClientConfigStorage getConfigStorage() {
        return configStorage;
    }

    @Override
    public String getId() {
        return Effortless.MOD_ID;
    }

}

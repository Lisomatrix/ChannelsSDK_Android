package pt.lisomatrix.channelssdk.model;

import java.util.HashMap;
import java.util.Map;

import pt.lisomatrix.channelssdk.network.model.Channels;

public class InitialPresenceStatus {

    private final String channelID;
    private final Map<String, ClientPresenceStatus> clientPresences;

    public InitialPresenceStatus(Channels.InitialPresenceStatus status) {
        this.channelID = status.getChannelID();

        clientPresences = new HashMap<>();

        for (Map.Entry<String, Channels.ClientStatus> keyVal : status.getClientStatusMap().entrySet()) {
            clientPresences.put(keyVal.getKey(), new ClientPresenceStatus(keyVal.getValue()));
        }
    }

    public String getChannelID() {
        return channelID;
    }

    public Map<String, ClientPresenceStatus> getClientPresences() {
        return clientPresences;
    }
}

package pt.lisomatrix.channelssdk.model;

import pt.lisomatrix.channelssdk.network.model.Channels;

public class ClientLeave {
    private final String channelID;
    private final String clientID;

    public ClientLeave(Channels.ClientLeave clientLeave) {
        this.channelID = clientLeave.getChannelID();
        this.clientID = clientLeave.getClientID();
    }

    public String getChannelID() {
        return channelID;
    }

    public String getClientID() {
        return clientID;
    }
}

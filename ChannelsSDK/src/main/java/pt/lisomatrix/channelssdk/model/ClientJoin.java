package pt.lisomatrix.channelssdk.model;

import pt.lisomatrix.channelssdk.network.model.Channels;

public class ClientJoin {

    private final String channelID;
    private final String clientID;

    public ClientJoin(Channels.ClientJoin clientJoin) {
        this.channelID = clientJoin.getChannelID();
        this.clientID = clientJoin.getClientID();
    }

    public String getChannelID() {
        return channelID;
    }

    public String getClientID() {
        return clientID;
    }
}

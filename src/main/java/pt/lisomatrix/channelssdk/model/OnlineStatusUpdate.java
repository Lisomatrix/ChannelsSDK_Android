package pt.lisomatrix.channelssdk.model;

import pt.lisomatrix.channelssdk.network.model.Channels;

public class OnlineStatusUpdate {

    private final String channelID;
    private final String clientID;
    private final boolean status;
    private final long timestamp;

    public OnlineStatusUpdate(Channels.OnlineStatusUpdate onlineStatusUpdate) {
        this.channelID = onlineStatusUpdate.getChannelID();
        this.clientID = onlineStatusUpdate.getClientID();
        this.status = onlineStatusUpdate.getStatus();
        this.timestamp = onlineStatusUpdate.getTimestamp();
    }

    public String getChannelID() {
        return channelID;
    }

    public String getClientID() {
        return clientID;
    }

    public boolean isStatus() {
        return status;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

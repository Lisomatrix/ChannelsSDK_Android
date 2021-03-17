package pt.lisomatrix.channelssdk.model;

import pt.lisomatrix.channelssdk.network.model.Channels;

public class ClientPresenceStatus {

    private final boolean status;
    private final long timestamp;

    public ClientPresenceStatus(Channels.OnlineStatusUpdate update) {
        this.status = update.getStatus();
        this.timestamp = update.getTimestamp();
    }

    public ClientPresenceStatus(Channels.ClientStatus update) {
        this.status = update.getStatus();
        this.timestamp = update.getTimestamp();
    }

    public ClientPresenceStatus(boolean status, long timestamp) {
        this.status = status;
        this.timestamp = timestamp;
    }

    public boolean isStatus() {
        return status;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

package pt.lisomatrix.channelssdk.model;

import pt.lisomatrix.channelssdk.network.model.Channels;

public class ChannelEvent {

    private String senderID;
    private String eventType;
    private String payload;
    private String channelID;
    private long timestamp;

    public ChannelEvent(){}

    public ChannelEvent(Channels.ChannelEvent channelEvent) {
        setChannelID(channelEvent.getChannelID());
        setSenderID(channelEvent.getSenderID());
        setEventType(channelEvent.getEventType());
        setPayload(channelEvent.getPayload());
        setTimestamp(channelEvent.getTimestamp());
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

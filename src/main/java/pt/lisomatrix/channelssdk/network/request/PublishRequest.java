package pt.lisomatrix.channelssdk.network.request;

public class PublishRequest {

    private String payload;
    private String eventType;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}

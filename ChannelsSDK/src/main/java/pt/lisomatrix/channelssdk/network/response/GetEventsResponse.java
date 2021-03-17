package pt.lisomatrix.channelssdk.network.response;

import java.util.List;

import pt.lisomatrix.channelssdk.model.ChannelEvent;

public class GetEventsResponse {

    public List<ChannelEvent> events;

    public List<ChannelEvent> getEvents() {
        return events;
    }

    public void setEvents(List<ChannelEvent> events) {
        this.events = events;
    }
}

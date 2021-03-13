package pt.lisomatrix.channelssdk.network.callback;

import java.util.List;

import pt.lisomatrix.channelssdk.model.ChannelEvent;

public interface GetChannelEventsCallback {

    void onSuccess(List<ChannelEvent> events);
    void onError(Throwable throwable);
}

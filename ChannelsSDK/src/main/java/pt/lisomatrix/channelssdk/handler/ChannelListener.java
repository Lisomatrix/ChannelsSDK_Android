package pt.lisomatrix.channelssdk.handler;

import pt.lisomatrix.channelssdk.model.ChannelEvent;
import pt.lisomatrix.channelssdk.model.RequestAcknowledge;
import pt.lisomatrix.channelssdk.network.model.Channels;

public interface ChannelListener {

    void onPublishAcknowledge(RequestAcknowledge publishAck);

    void onSubscribed();

    void onChannelEvent(ChannelEvent event);

    void onRemoved();
}

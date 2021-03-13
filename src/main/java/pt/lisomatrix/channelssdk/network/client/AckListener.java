package pt.lisomatrix.channelssdk.network.client;

import pt.lisomatrix.channelssdk.network.model.Channels;

public interface AckListener {

    void onAckReceived(Channels.PublishAck publishAck);
}

package pt.lisomatrix.channelssdk.network.client;

import pt.lisomatrix.channelssdk.network.model.Channels;

public interface ChannelEventListener {

    void onChannelEvent(Channels.ChannelEvent channelEvent);

    void onJoinChannel(Channels.ClientJoin clientJoin);
    void onLeaveChannel(Channels.ClientLeave clientLeave);

    void onChannelRemoved(String channelID);
    void onChannelAdded(String channelID);

    void onChannelInitialPresenceStatus(Channels.InitialPresenceStatus initialPresenceStatus);
    void onChannelOnlineStatusUpdate(Channels.OnlineStatusUpdate onlineStatusUpdate);
}

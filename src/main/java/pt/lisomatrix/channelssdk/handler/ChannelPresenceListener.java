package pt.lisomatrix.channelssdk.handler;

import pt.lisomatrix.channelssdk.model.ClientJoin;
import pt.lisomatrix.channelssdk.model.ClientLeave;
import pt.lisomatrix.channelssdk.model.InitialPresenceStatus;
import pt.lisomatrix.channelssdk.model.OnlineStatusUpdate;
import pt.lisomatrix.channelssdk.network.model.Channels;

public interface ChannelPresenceListener {

    void onClientJoinChannel(ClientJoin clientJoin);

    void onClientLeaveChannel(ClientLeave clientLeave);

    void onOnlineStatusUpdate(OnlineStatusUpdate onlineStatusUpdate);

    void onInitialStatusUpdate();
}

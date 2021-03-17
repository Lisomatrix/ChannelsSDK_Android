package pt.lisomatrix.channelssdk.handler;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

import pt.lisomatrix.channelssdk.ChannelsSDK;
import pt.lisomatrix.channelssdk.network.client.AckListener;
import pt.lisomatrix.channelssdk.network.client.ChannelEventListener;
import pt.lisomatrix.channelssdk.network.client.ChannelsListener;
import pt.lisomatrix.channelssdk.network.client.ChannelsWebSocketListener;
import pt.lisomatrix.channelssdk.network.model.Channels;

public class ChannelsHandler implements AckListener, ChannelEventListener {

    private static final String AUTHORIZATION = "Authorization";
    private static final String APP_ID = "AppID";

    private static ChannelsHandler m_instance;

    private final ChannelsWebSocketListener m_webSocket;

    private final ConcurrentHashMap<String, Channel> m_channels = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, String> m_waitingAck = new ConcurrentHashMap<>();

    private final AtomicInteger m_requestID =  new AtomicInteger(0);

    private ChannelsListener m_channelsListener;

    private ChannelsHandler() {

        ChannelsSDK channelsSDK = ChannelsSDK.getInstance();

        HashMap<String, String> headers = new HashMap<>();
        headers.put(APP_ID, channelsSDK.getAppID());
        headers.put(AUTHORIZATION, channelsSDK.getToken());

        this.m_webSocket = ChannelsWebSocketListener.create(
                channelsSDK.getURL(),
                headers
        );
        m_webSocket.setAckListener(this);
        m_webSocket.setEventListener(this);
    }

    public void setChannelsListener(ChannelsListener channelsListener) {
        this.m_channelsListener = channelsListener;
    }

    public int getNextRequestID() {
        return m_requestID.getAndIncrement();
    }

    public void sendPublish(int requestID, String channelID, Channels.NewEvent newEvent) {
        if (requestID != -1) {
            m_waitingAck.put(requestID, channelID);
        }

        m_webSocket.sendNewEvent(newEvent);
    }

    public void sendSubscribe(int requestID, String channelID, Channels.NewEvent newEvent) {

        if (requestID != -1) {
            m_waitingAck.put(requestID, channelID);
        }

        m_webSocket.sendNewEvent(newEvent);
    }

    @Nullable
    public Channel getChannel(String channelID) {
        if (m_channels.containsKey(channelID)) {
            return m_channels.get(channelID);
        }

        return null;
    }

    public void registerChannel(Channel channel) {
        m_channels.put(channel.getChannelInfo().getId(), channel);
    }

    public static ChannelsHandler getInstance() {
        if (m_instance == null) {
            m_instance = new ChannelsHandler();
        }

        return m_instance;
    }

    @Override
    public void onChannelEvent(Channels.ChannelEvent channelEvent) {
        if (m_channels.containsKey(channelEvent.getChannelID())) {
            Channel channel = m_channels.get(channelEvent.getChannelID());

            if (channel != null)
                channel.onChannelEvent(channelEvent);
        }
    }

    @Override
    public void onJoinChannel(Channels.ClientJoin clientJoin) {
        if (m_channels.containsKey(clientJoin.getChannelID())) {
            Channel channel = m_channels.get(clientJoin.getChannelID());

            if (channel != null)
                channel.onJoinChannel(clientJoin);
        }
    }

    @Override
    public void onLeaveChannel(Channels.ClientLeave clientLeave) {
        if (m_channels.containsKey(clientLeave.getChannelID())) {
            Channel channel = m_channels.get(clientLeave.getChannelID());

            if (channel != null)
                channel.onLeaveChannel(clientLeave);
        }
    }

    @Override
    public void onChannelRemoved(String channelID) {
        m_channelsListener.onChannelRemoved(channelID);

        if (m_channels.containsKey(channelID)) {
            Channel channel = m_channels.get(channelID);

            if (channel != null)
                channel.onRemoved();

            m_channels.remove(channelID);
        }
    }

    @Override
    public void onChannelAdded(String channelID) {
        m_channelsListener.onChannelAdded(channelID);
    }

    @Override
    public void onChannelInitialPresenceStatus(Channels.InitialPresenceStatus initialPresenceStatus) {
        if (m_channels.containsKey(initialPresenceStatus.getChannelID())) {
            Channel channel = m_channels.get(initialPresenceStatus.getChannelID());

            if (channel != null)
                channel.onInitialStatusUpdate(initialPresenceStatus);
        }
    }

    @Override
    public void onChannelOnlineStatusUpdate(Channels.OnlineStatusUpdate onlineStatusUpdate) {
        if (m_channels.containsKey(onlineStatusUpdate.getChannelID())) {
            Channel channel = m_channels.get(onlineStatusUpdate.getChannelID());

            if (channel != null)
                channel.onStatusUpdate(onlineStatusUpdate);
        }
    }

    @Override
    public void onAckReceived(Channels.PublishAck publishAck) {
        if (m_waitingAck.containsKey(publishAck.getReplyTo())) {
            String channelID = m_waitingAck.get(publishAck.getReplyTo());

            if (channelID == null) return;

            Channel channel = m_channels.get(channelID);

            if (channel != null) {
                channel.onAck(publishAck);
            }
        }
    }
}

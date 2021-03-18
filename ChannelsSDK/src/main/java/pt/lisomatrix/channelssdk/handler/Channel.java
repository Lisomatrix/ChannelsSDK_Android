package pt.lisomatrix.channelssdk.handler;

import com.google.protobuf.ByteString;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import pt.lisomatrix.channelssdk.ChannelsSDK;
import pt.lisomatrix.channelssdk.model.ChannelEvent;
import pt.lisomatrix.channelssdk.model.ChannelInfo;
import pt.lisomatrix.channelssdk.model.ClientJoin;
import pt.lisomatrix.channelssdk.model.ClientLeave;
import pt.lisomatrix.channelssdk.model.ClientPresenceStatus;
import pt.lisomatrix.channelssdk.model.InitialPresenceStatus;
import pt.lisomatrix.channelssdk.model.OnlineStatusUpdate;
import pt.lisomatrix.channelssdk.model.RequestAcknowledge;
import pt.lisomatrix.channelssdk.network.callback.GetChannelEventsCallback;
import pt.lisomatrix.channelssdk.network.model.Channels;

public class Channel {

    private final ChannelInfo m_channelInfo;


    private boolean m_isSubscribed = false;

    private ChannelListener m_listener;
    private ChannelPresenceListener m_presenceListener;

    private final HashMap<String, ClientPresenceStatus> m_presenceStatus = new HashMap<>();

    private Channel(ChannelInfo channelInfo) {
        this.m_channelInfo = channelInfo;
    }

    public void setPresenceListener(ChannelPresenceListener presenceListener) {
        this.m_presenceListener = presenceListener;
    }

    public Map<String, ClientPresenceStatus> getPresences() {
        return Collections.unmodifiableMap(m_presenceStatus);
    }

    public void subscribe(ChannelListener channelListener) {

        this.m_listener = channelListener;

        Channels.SubscribeRequest request = createSubscriptionRequest();

        Channels.NewEvent newEvent =
                createNewEvent(
                        Channels.NewEvent.NewEventType.SUBSCRIBE,
                        request.toByteString()
                );

        ChannelsHandler.getInstance().sendSubscribe(request.getID(), m_channelInfo.getId(), newEvent);
    }

    public void publish(String eventType, String payload, boolean shouldACK) {
        Channels.PublishRequest request = createNewPublishRequest(shouldACK, eventType, payload);

        Channels.NewEvent newEvent =
                createNewEvent(Channels.NewEvent.NewEventType.PUBLISH,
                        request.toByteString()
                );

        ChannelsHandler.getInstance().sendPublish(request.getID(), m_channelInfo.getId(), newEvent);
    }

    public void onRemoved() {
        if (m_listener != null)
            m_listener.onRemoved();
    }

    public void onJoinChannel(Channels.ClientJoin clientJoin) {
        m_presenceStatus.put(clientJoin.getClientID(), new ClientPresenceStatus(false, 0));

        if (m_presenceListener != null)
            m_presenceListener.onClientJoinChannel(new ClientJoin(clientJoin));

    }

    public void onLeaveChannel(Channels.ClientLeave clientLeave) {
        m_presenceStatus.remove(clientLeave.getClientID());

        if (m_presenceListener != null)
            m_presenceListener.onClientLeaveChannel(new ClientLeave(clientLeave));
    }

    public void onInitialStatusUpdate(Channels.InitialPresenceStatus initialPresenceStatus) {
        for (Map.Entry<String, Channels.ClientStatus> keyVal : initialPresenceStatus.getClientStatusMap().entrySet()) {
            m_presenceStatus.put(keyVal.getKey(), new ClientPresenceStatus(keyVal.getValue()));
        }

        if (m_presenceListener != null)
            m_presenceListener.onInitialStatusUpdate();
    }

    public void onStatusUpdate(Channels.OnlineStatusUpdate onlineStatusUpdate) {
        m_presenceStatus.put(onlineStatusUpdate.getClientID(), new ClientPresenceStatus(onlineStatusUpdate));

        if (m_presenceListener != null)
            m_presenceListener.onOnlineStatusUpdate(new OnlineStatusUpdate(onlineStatusUpdate));
    }

    public void onChannelEvent(Channels.ChannelEvent event) {
        if (m_listener != null) {
            m_listener.onChannelEvent(new ChannelEvent(event));
        }
    }

    public void onAck(Channels.PublishAck ack) {

        if (!m_isSubscribed) {
            m_isSubscribed = true;

            if (m_listener != null) {
                m_listener.onSubscribed();
            }

        } else if (m_listener != null) {
            m_listener.onPublishAcknowledge(new RequestAcknowledge(ack));
        }
    }

    public void getLastEvents(long amount, GetChannelEventsCallback callback) {
        ChannelsSDK
                .getInstance()
                .getChannelService()
                .getLastChannelEvents(m_channelInfo.getId(), amount, callback);
    }

    public ChannelInfo getChannelInfo() {
        return m_channelInfo;
    }

    private Channels.PublishRequest createNewPublishRequest(boolean shouldNotify, String eventType, String payload) {
        Channels.PublishRequest.Builder request = Channels.PublishRequest
                .newBuilder()
                .setPayload(payload)
                .setEventType(eventType)
                .setChannelID(m_channelInfo.getId())
                .setID(0);

        if (shouldNotify) {
            request.setID(ChannelsHandler.getInstance().getNextRequestID());
        }

        return request.build();
    }

    private Channels.SubscribeRequest createSubscriptionRequest() {
        return Channels.SubscribeRequest
                .newBuilder()
                .setChannelID(m_channelInfo.getId())
                .setID(ChannelsHandler.getInstance().getNextRequestID())
                .build();
    }

    private Channels.NewEvent createNewEvent(Channels.NewEvent.NewEventType type, ByteString payload) {
        return Channels.NewEvent
                .newBuilder()
                .setType(type)
                .setPayload(payload)
                .build();
    }

    public static Channel fromChannelInfo(ChannelInfo channelInfo) {

        Channel channel = ChannelsHandler.getInstance().getChannel(channelInfo.getId());

        if (channel != null) {
            return channel;
        }

        channel = new Channel(channelInfo);
        ChannelsHandler.getInstance().registerChannel(channel);

        return channel;
    }
}

package pt.lisomatrix.channelssdk.network.client;

import android.os.Environment;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;
import com.squareup.tape2.ObjectQueue;
import com.squareup.tape2.QueueFile;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import pt.lisomatrix.channelssdk.ChannelsSDK;
import pt.lisomatrix.channelssdk.network.ApiClient;
import pt.lisomatrix.channelssdk.network.converter.NewEventsConverter;
import pt.lisomatrix.channelssdk.network.model.Channels;

public class ChannelsWebSocketListener extends WebSocketListener {

    private static final String TAG = "ChannelsWebSocket";
    private static final String EVENTS_QUEUE_NAME = "events_queue.eve";
    private static final int NORMAL_CLOSURE_STATUS = 1000;

    private boolean m_isConnecting = false;

    private boolean m_isConnected = false;

    private String m_url;
    private Map<String, String> m_headers;

    private AckListener m_ackListener;
    private ChannelEventListener m_eventListener;

    //private ObjectQueue<Channels.NewEvent> m_eventQueueFile;
    private final Queue<Channels.NewEvent> m_eventQueueFile = new LinkedList<>();
    private WebSocket m_ws;

    private ChannelsWebSocketListener()  {}

    public boolean isConnected() {
        return m_isConnected;
    }

    public void setAckListener(AckListener ackListener) {
        this.m_ackListener = ackListener;
    }

    public void setEventListener(ChannelEventListener eventListener) {
        this.m_eventListener = eventListener;
    }

    public void sendNewEvent(Channels.NewEvent newEvent) {
        if (m_ws == null) {
            m_eventQueueFile.add(newEvent);
        } else {
            m_ws.send(ByteString.of(newEvent.toByteArray()));
        }
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        Log.d(TAG, "Connection open!");
        m_isConnecting = false;
        m_isConnected = false;
        this.m_ws = webSocket;

        for (Channels.NewEvent newEvent : m_eventQueueFile) {
            m_ws.send(ByteString.of(newEvent.toByteArray()));
        }
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        Log.d(TAG, "Receiving : " + text);
    }
    @Override
    public void onMessage(@NotNull WebSocket webSocket, ByteString bytes) {
        try {
            Channels.NewEvent newEvent = Channels.NewEvent.parseFrom(bytes.toByteArray());

            Log.d(TAG, newEvent.toString());

            if (newEvent.getType() == Channels.NewEvent.NewEventType.ACK) {
                if (m_ackListener != null) {
                    m_ackListener.onAckReceived(Channels.PublishAck.parseFrom(newEvent.getPayload()));
                }
            } else if (newEvent.getType() == Channels.NewEvent.NewEventType.PUBLISH) {
                if (m_eventListener != null) {
                    Channels.ChannelEvent event = Channels.ChannelEvent.parseFrom(newEvent.getPayload());

                    Log.d(TAG, event.toString());

                    m_eventListener.onChannelEvent(event);
                }
            } else if (newEvent.getType() == Channels.NewEvent.NewEventType.JOIN_CHANNEL) {

                if (m_eventListener != null) {
                    Channels.ClientJoin clientJoin = Channels.ClientJoin.parseFrom(newEvent.getPayload());

                    Log.d(TAG, clientJoin.toString());

                    m_eventListener.onJoinChannel(clientJoin);
                }

            } else if (newEvent.getType() == Channels.NewEvent.NewEventType.LEAVE_CHANNEL) {

                if (m_eventListener != null) {
                    Channels.ClientLeave clientLeave = Channels.ClientLeave.parseFrom(newEvent.getPayload());

                    Log.d(TAG, clientLeave.toString());

                    m_eventListener.onLeaveChannel(clientLeave);
                }

            } else if (newEvent.getType() == Channels.NewEvent.NewEventType.REMOVE_CHANNEL) {

                if (m_eventListener != null) {
                    m_eventListener.onChannelRemoved(newEvent.getPayload().toStringUtf8());
                }

            } else if (newEvent.getType() == Channels.NewEvent.NewEventType.INITIAL_ONLINE_STATUS) {

                if (m_eventListener != null) {
                    Channels.InitialPresenceStatus initialPresenceStatus = Channels.InitialPresenceStatus.parseFrom(newEvent.getPayload());
                    Log.d(TAG, initialPresenceStatus.toString());
                    m_eventListener.onChannelInitialPresenceStatus(initialPresenceStatus);
                }

            } else if (newEvent.getType() == Channels.NewEvent.NewEventType.ONLINE_STATUS) {

                if (m_eventListener != null) {
                    Channels.OnlineStatusUpdate onlineStatusUpdate = Channels.OnlineStatusUpdate.parseFrom(newEvent.getPayload());
                    Log.d(TAG, onlineStatusUpdate.toString());

                    m_eventListener.onChannelOnlineStatusUpdate(onlineStatusUpdate);
                }

            } else if (newEvent.getType() == Channels.NewEvent.NewEventType.NEW_CHANNEL) {
                if (m_eventListener != null) {
                    m_eventListener.onChannelAdded(newEvent.getPayload().toStringUtf8());
                }
            }

        } catch (InvalidProtocolBufferException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, @NotNull String reason) {
        Log.d(TAG, "Connection closed!");
        m_isConnecting = false;
        m_isConnected = false;
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        Log.d(TAG, "Closing : " + code + " / " + reason);
    }
    @Override
    public void onFailure(@NotNull WebSocket webSocket, Throwable t, Response response) {
        Log.e(TAG, "Connection Failure!");
        Log.d(TAG, "Error : " + t.getMessage());
        m_isConnecting = false;
        m_isConnected = false;
        connect(m_url, m_headers);
    }

    private void connect(String url, Map<String, String> headers) {
        Log.d(TAG, "Creating a connection!");
        this.m_url = url;
        m_headers = headers;
        Request.Builder builder = new Request
                .Builder()
                .url(url);

        for (Map.Entry<String, String> header : headers.entrySet())
            builder.addHeader(header.getKey(), header.getValue());

        m_isConnecting = true;
        OkHttpClient client = ApiClient.getClient();
        WebSocket ws = client.newWebSocket(builder.build(), this);

        client.dispatcher().executorService().shutdown();
    }

    public static ChannelsWebSocketListener create(String url, HashMap<String, String> headers) {
        ChannelsWebSocketListener listener = new ChannelsWebSocketListener();
        listener.connect(url, headers);

        return listener;
    }
}

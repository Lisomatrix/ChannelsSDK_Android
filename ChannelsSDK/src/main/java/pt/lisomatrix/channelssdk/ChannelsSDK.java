package pt.lisomatrix.channelssdk;

import pt.lisomatrix.channelssdk.handler.Channel;
import pt.lisomatrix.channelssdk.handler.ChannelsHandler;
import pt.lisomatrix.channelssdk.network.ApiClient;
import pt.lisomatrix.channelssdk.network.callback.GetChannelEventsCallback;
import pt.lisomatrix.channelssdk.network.channel.ChannelService;
import pt.lisomatrix.channelssdk.network.client.ChannelsListener;
import pt.lisomatrix.channelssdk.network.client.ClientService;

public class ChannelsSDK {

    public static void initialize(String url, String appID) {
        if (m_instance == null) {
            m_instance = new ChannelsSDK(url, appID);
        }
    }

    public static void connect(String token) {
        m_instance.setToken(token);
        ChannelsHandler.init();
    }

    private static ChannelsSDK m_instance;

    public static ChannelsSDK getInstance() {
        return m_instance;
    }

    private final String m_appID;
    private String m_token;
    private final String m_url;

    private final ChannelService m_channelService;
    private final ClientService m_clientService;

    private ChannelsSDK(String url, String appID) {
        this.m_appID = appID;
        this.m_url = url + "/optimized";

        this.m_channelService = new ChannelService();
        this.m_clientService = new ClientService();
        ApiClient.init(url);
    }

    private void setToken(String token) {
        this.m_token = token;
    }

    public ClientService getClientService() {
        return m_clientService;
    }

    public ChannelService getChannelService() {
        return m_channelService;
    }

    public String getURL() {
        return m_url;
    }

    public String getToken() {
        return this.m_token;
    }

    public String getAppID() {
        return this.m_appID;
    }

    public static void setChannelsListener(ChannelsListener channelsListener) {
        ChannelsHandler.getInstance().setChannelsListener(channelsListener);
    }

    public static Channel getChannel(String channelID) {
        return ChannelsHandler.getInstance().getChannel(channelID);
    }

    public static void getChannelLastEvents(String channelID, long amount, GetChannelEventsCallback channelEventsCallback) {
        m_instance.getChannelService().getLastChannelEvents(channelID, amount, channelEventsCallback);
    }
}

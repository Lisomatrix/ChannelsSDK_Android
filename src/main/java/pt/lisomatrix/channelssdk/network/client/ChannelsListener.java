package pt.lisomatrix.channelssdk.network.client;

public interface ChannelsListener {

    void onChannelRemoved(String channelID);
    void onChannelAdded(String channelID);
}

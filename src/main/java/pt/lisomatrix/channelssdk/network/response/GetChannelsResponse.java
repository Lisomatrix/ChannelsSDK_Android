package pt.lisomatrix.channelssdk.network.response;

import java.util.List;

import pt.lisomatrix.channelssdk.model.ChannelInfo;

public class GetChannelsResponse {

    private List<ChannelInfo> channels;

    public List<ChannelInfo> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelInfo> channels) {
        this.channels = channels;
    }
}

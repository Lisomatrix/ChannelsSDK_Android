package pt.lisomatrix.channelssdk.network.callback;

import java.util.List;

import pt.lisomatrix.channelssdk.model.ChannelInfo;

public interface GetChannelsCallback {
    void onSuccess(List<ChannelInfo> channelInfos);
    void onError(Throwable throwable);
}

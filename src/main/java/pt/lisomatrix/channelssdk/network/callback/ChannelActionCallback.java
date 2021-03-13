package pt.lisomatrix.channelssdk.network.callback;

public interface ChannelActionCallback {

    void onSuccess();
    void onFailed();
    void onError(Throwable throwable);
}

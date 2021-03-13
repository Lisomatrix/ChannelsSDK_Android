package pt.lisomatrix.channelssdk.network.channel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import pt.lisomatrix.channelssdk.network.ApiClient;
import pt.lisomatrix.channelssdk.network.callback.ChannelActionCallback;
import pt.lisomatrix.channelssdk.network.callback.GetChannelEventsCallback;
import pt.lisomatrix.channelssdk.network.callback.GetChannelsCallback;
import pt.lisomatrix.channelssdk.network.request.CreateChannelRequest;
import pt.lisomatrix.channelssdk.network.request.PublishRequest;
import pt.lisomatrix.channelssdk.network.response.GetChannelsResponse;
import pt.lisomatrix.channelssdk.network.response.GetEventsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelService {

    private final ChannelAPI m_api;

    public ChannelService() {
        this.m_api = ApiClient.createService(ChannelAPI.class);
    }

    public void publishToChannel(String channelID, PublishRequest request, ChannelActionCallback callback) {
        m_api
                .publishToChannel(channelID, request)
                .enqueue(new Callback<Response<Void>>() {
                    @Override
                    public void onResponse(@NotNull Call<Response<Void>> call, @NotNull Response<Response<Void>> response) {
                        if (response.code() == 200) {
                            callback.onSuccess();
                        } else {
                            callback.onFailed();
                        }

                    }

                    @Override
                    public void onFailure(@NotNull Call<Response<Void>> call, @NotNull Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void createChannel(CreateChannelRequest request, ChannelActionCallback callback) {
        m_api
                .createChannel(request)
                .enqueue(new Callback<Response<Void>>() {
                    @Override
                    public void onResponse(@NotNull Call<Response<Void>> call, @NotNull Response<Response<Void>> response) {
                        if (response.code() == 200) {
                            callback.onSuccess();
                        } else {
                            callback.onFailed();
                        }

                    }

                    @Override
                    public void onFailure(@NotNull Call<Response<Void>> call, @NotNull Throwable t) {
                        callback.onError(t);
                    }
                });

    }

    public void deleteChannel(String channelID, ChannelActionCallback callback) {
        m_api
                .deleteChannel(channelID)
                .enqueue(new Callback<Response<Void>>() {
                    @Override
                    public void onResponse(@NotNull Call<Response<Void>> call, @NotNull Response<Response<Void>> response) {
                        if (response.code() == 200) {
                            callback.onSuccess();
                        } else {
                            callback.onFailed();
                        }

                    }

                    @Override
                    public void onFailure(@NotNull Call<Response<Void>> call, @NotNull Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void closeChannel(String channelID, ChannelActionCallback callback) {
        m_api
                .closeChannel(channelID)
                .enqueue(new Callback<Response<Void>>() {
                    @Override
                    public void onResponse(@NotNull Call<Response<Void>> call, @NotNull Response<Response<Void>> response) {
                        if (response.code() == 200) {
                            callback.onSuccess();
                        } else {
                            callback.onFailed();
                        }

                    }

                    @Override
                    public void onFailure(@NotNull Call<Response<Void>> call, @NotNull Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void openChannel(String channelID, ChannelActionCallback callback) {
        m_api
                .openChannel(channelID)
                .enqueue(new Callback<Response<Void>>() {
                    @Override
                    public void onResponse(@NotNull Call<Response<Void>> call, @NotNull Response<Response<Void>> response) {
                        if (response.code() == 200) {
                            callback.onSuccess();
                        } else {
                            callback.onFailed();
                        }

                    }

                    @Override
                    public void onFailure(@NotNull Call<Response<Void>> call, @NotNull Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void joinChannel(String channelID, String clientID, ChannelActionCallback callback) {
        m_api
                .joinChannel(channelID, clientID)
                .enqueue(new Callback<Response<Void>>() {
                    @Override
                    public void onResponse(@NotNull Call<Response<Void>> call, @NotNull Response<Response<Void>> response) {
                        if (response.code() == 200) {
                            callback.onSuccess();
                        } else {
                            callback.onFailed();
                        }

                    }

                    @Override
                    public void onFailure(@NotNull Call<Response<Void>> call, @NotNull Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void leaveChannel(String channelID, String clientID, ChannelActionCallback callback) {
        m_api
                .leaveChannel(channelID, clientID)
                .enqueue(new Callback<Response<Void>>() {
                    @Override
                    public void onResponse(@NotNull Call<Response<Void>> call, @NotNull Response<Response<Void>> response) {
                        if (response.code() == 200) {
                            callback.onSuccess();
                        } else {
                            callback.onFailed();
                        }

                    }

                    @Override
                    public void onFailure(@NotNull Call<Response<Void>> call, @NotNull Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void getPublicChannels(GetChannelsCallback channelsCallback) {
        m_api
                .getPublicChannels()
                .enqueue(new Callback<GetChannelsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<GetChannelsResponse> call, @NotNull Response<GetChannelsResponse> response) {
                        if (response.code() == 200 && response.body() != null)
                            channelsCallback.onSuccess(response.body().getChannels());
                        else
                            channelsCallback.onSuccess(new ArrayList<>());
                    }

                    @Override
                    public void onFailure(@NotNull Call<GetChannelsResponse> call, @NotNull Throwable t) {
                        channelsCallback.onError(t);
                    }
                });
    }

    public void getPrivateChannels(GetChannelsCallback channelsCallback) {
        m_api
                .getPrivateChannels()
                .enqueue(new Callback<GetChannelsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<GetChannelsResponse> call, @NotNull Response<GetChannelsResponse> response) {
                        if (response.code() == 200 && response.body() != null)
                            channelsCallback.onSuccess(response.body().getChannels());
                        else
                            channelsCallback.onSuccess(new ArrayList<>());
                    }

                    @Override
                    public void onFailure(@NotNull Call<GetChannelsResponse> call, @NotNull Throwable t) {
                        channelsCallback.onError(t);
                    }
                });
    }

    public void getLastChannelEvents(String channelID, long amount, GetChannelEventsCallback callback) {
        m_api
                .getLastChannelEvents(channelID, amount)
                .enqueue(new Callback<GetEventsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<GetEventsResponse> call, @NotNull Response<GetEventsResponse> response) {
                        if (response.code() == 200 && response.body() != null)
                            callback.onSuccess(response.body().getEvents());
                        else
                            callback.onSuccess(new ArrayList<>());
                    }

                    @Override
                    public void onFailure(@NotNull Call<GetEventsResponse> call, @NotNull Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void getLastChannelEventsSince(String channelID, long timestamp, long amount, GetChannelEventsCallback callback) {
        m_api
                .getLastChannelEventsSince(channelID, timestamp, amount)
                .enqueue(new Callback<GetEventsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<GetEventsResponse> call, @NotNull Response<GetEventsResponse> response) {
                        if (response.code() == 200 && response.body() != null)
                            callback.onSuccess(response.body().getEvents());
                        else
                            callback.onSuccess(new ArrayList<>());
                    }

                    @Override
                    public void onFailure(@NotNull Call<GetEventsResponse> call, @NotNull Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void getChannelEventsSince(String channelID, long timestamp, GetChannelEventsCallback callback) {
        m_api
                .getChannelEventsSince(channelID, timestamp)
                .enqueue(new Callback<GetEventsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<GetEventsResponse> call, @NotNull Response<GetEventsResponse> response) {
                        if (response.code() == 200 && response.body() != null)
                            callback.onSuccess(response.body().getEvents());
                        else
                            callback.onSuccess(new ArrayList<>());
                    }

                    @Override
                    public void onFailure(@NotNull Call<GetEventsResponse> call, @NotNull Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void getChannelEventsBetween(String channelID, long sinceTimestamp, long toTimestamp, GetChannelEventsCallback callback) {
        m_api
                .getChannelEventsBetween(channelID, sinceTimestamp, toTimestamp)
                .enqueue(new Callback<GetEventsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<GetEventsResponse> call, @NotNull Response<GetEventsResponse> response) {
                        if (response.code() == 200 && response.body() != null)
                            callback.onSuccess(response.body().getEvents());
                        else
                            callback.onSuccess(new ArrayList<>());
                    }

                    @Override
                    public void onFailure(@NotNull Call<GetEventsResponse> call, @NotNull Throwable t) {
                        callback.onError(t);
                    }
                });
    }
}

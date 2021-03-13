package pt.lisomatrix.channelssdk.network.channel;

import pt.lisomatrix.channelssdk.network.request.CreateChannelRequest;
import pt.lisomatrix.channelssdk.network.request.PublishRequest;
import pt.lisomatrix.channelssdk.network.response.GetChannelsResponse;
import pt.lisomatrix.channelssdk.network.response.GetEventsResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChannelAPI {

    @GET("/channel/open")
    Call<GetChannelsResponse> getPublicChannels();

    @GET("/channel/closed")
    Call<GetChannelsResponse> getPrivateChannels();

    @GET("/last/{channelID}/{amount}")
    Call<GetEventsResponse> getLastChannelEvents(@Path("channelID") String channelID, @Path("amount") long amount);

    @GET("/last/{channelID}/{amount}/last/{timestamp}")
    Call<GetEventsResponse> getLastChannelEventsSince(@Path("channelID") String channelID, @Path("amount") long amount, @Path("timestamp") long timestamp);

    @GET("/c/{channelID}/sync/{timestamp}")
    Call<GetEventsResponse> getChannelEventsSince(@Path("channelID") String channelID, @Path("timestamp") long timestamp);

    @GET("/sync/{channelID}/{sinceTimestamp}/to/{toTimestamp}")
    Call<GetEventsResponse> getChannelEventsBetween(@Path("channelID") String channelID, @Path("sinceTimestamp") long sinceTimestamp, @Path("toTimestamp") long toTimestamp);

    @POST("/channel/{channelID}/publish")
    Call<Response<Void>> publishToChannel(@Path("channelID") String channelID, @Body PublishRequest publishRequest);

    @POST("/channel")
    Call<Response<Void>> createChannel(@Body CreateChannelRequest request);

    @DELETE("/channel/{channelID}")
    Call<Response<Void>> deleteChannel(@Path("channelID") String channelID);

    @POST("/channel/{channelID}/close")
    Call<Response<Void>> closeChannel(@Path("channelID") String channelID);

    @POST("/channel/{channelID}/open")
    Call<Response<Void>> openChannel(@Path("channelID") String channelID);

    @POST("/channel/{channelID}/join/{clientID}")
    Call<Response<Void>> joinChannel(@Path("channelID") String channelID, @Path("clientID") String clientID);

    @POST("/channel/{channelID}/leave/{clientID}")
    Call<Response<Void>> leaveChannel(@Path("channelID") String channelID, @Path("clientID") String clientID);
}

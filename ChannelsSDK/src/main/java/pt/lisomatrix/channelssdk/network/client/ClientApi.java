package pt.lisomatrix.channelssdk.network.client;

import pt.lisomatrix.channelssdk.network.request.CreateClientRequest;
import pt.lisomatrix.channelssdk.network.request.UpdateClientRequest;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClientApi {

    /*
    @POST("/client")
    Single<Response<Void>> createClient(@Body CreateClientRequest request);

    @DELETE("/client/{clientID}")
    Single<Response<Void>> deleteClient(@Path("clientID") String clientID);

    @PUT("/client/{clientID}")
    Single<Response<Void>> updateClient(@Path("clientID") String clientID, @Body UpdateClientRequest request);
    */
}

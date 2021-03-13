package pt.lisomatrix.channelssdk.network.client;

import pt.lisomatrix.channelssdk.network.ApiClient;
import pt.lisomatrix.channelssdk.network.request.CreateClientRequest;
import pt.lisomatrix.channelssdk.network.request.UpdateClientRequest;

public class ClientService {

    private final ClientApi m_api;

    public ClientService() {
        this.m_api = ApiClient.createService(ClientApi.class);
    }
/*
    public Single<Boolean> createClient(CreateClientRequest request) {
        return m_api.createClient(request)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .map(response -> response.code() == 200)
                .onErrorReturnItem(false);
    }

    public Single<Boolean> updateClient(String clientID, UpdateClientRequest request) {
        return m_api.updateClient(clientID, request)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .map(response -> response.code() == 200)
                .onErrorReturnItem(false);
    }

    public Single<Boolean> deleteClient(String clientID) {
        return m_api.deleteClient(clientID)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .map(response -> response.code() == 200)
                .onErrorReturnItem(false);
    }*/
}

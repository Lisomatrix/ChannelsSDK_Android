package pt.lisomatrix.channelssdk.network;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import pt.lisomatrix.channelssdk.ChannelsSDK;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * All services use this class
 * This class provides a single instance of a http client pre-configured
 */
public class ApiClient {

    private static String API_BASE_URL = "http://192.168.1.2:8090";

    private static final HttpLoggingInterceptor m_interceptor = new HttpLoggingInterceptor();

    private static final Retrofit.Builder m_Builder = new Retrofit
            .Builder()
            .client(getClient())
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static void init(String wsURL) {
        String url = wsURL.replace("ws://", "http://");
        url = wsURL.replace("wss://", "https://");
        API_BASE_URL = url;
    }

    public static OkHttpClient getClient() {
        m_interceptor.level(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient
                .Builder()
                .addInterceptor(m_interceptor)
                .addInterceptor(chain -> {
                    ChannelsSDK sdk = ChannelsSDK.getInstance();

                    Request request = chain
                            .request()
                            .newBuilder()
                            .addHeader("Authorization", sdk.getToken())
                            .addHeader("AppID", sdk.getAppID())
                            .addHeader("Content-Type", "application/json")
                            .build();

                    return chain.proceed(request);
                })
                .build();
    }

    /**
     * Create the API Service re-using the same HttpClient
     *
     * @param serviceClass the API class type to be created
     * @param <S> the API class type to be created
     * @return instantiated API
     */
    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = m_Builder.build();

        return retrofit.create(serviceClass);
    }
}

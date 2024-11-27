package nl.kringlooptilburg.businessservice.config;

import nl.kringlooptilburg.businessservice.services.KVKApiService;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    private final String baseUrl;

    public RetrofitConfig(@Value("${api.url.kvk}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Bean
    public KVKApiService kvkApiService() {
        // Configureer OkHttpClient (je kunt ook timeouts, interceptors etc. toevoegen)
        OkHttpClient client = new OkHttpClient.Builder().build();

        // Configureer Retrofit met de juiste converter (in dit geval Gson)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) // Voeg Gson toe voor JSON-conversie
                .build();

        // Retourneer de implementatie van de KvkApiService
        return retrofit.create(KVKApiService.class);
    }
}


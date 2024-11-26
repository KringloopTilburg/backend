package nl.kringlooptilburg.businessservice.config;

import nl.kringlooptilburg.businessservice.services.impl.BusinessServiceImpl;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RetrofitConfig {

    @Value("${api.url.kvk}")
    private final String baseUrl;

    public RetrofitConfig(@Value("${api.url.kvk}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Bean
    public BusinessServiceImpl BusinessService() {
        // Configureer OkHttpClient (je kunt ook timeouts, interceptors etc. toevoegen)
        OkHttpClient client = new OkHttpClient.Builder().build();

        // Configureer Retrofit met de juiste converter (in dit geval Gson)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) // Voeg Gson toe voor JSON-conversie
                .build();

        // Retourneer de implementatie van de KvkApiService
        return retrofit.create(BusinessServiceImpl.class);
    }
}


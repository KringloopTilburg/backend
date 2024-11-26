package nl.kringlooptilburg.businessservice.services;

import nl.kringlooptilburg.businessservice.domain.entities.Business;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;
import java.util.Optional;

public interface KVKService {
    @GET("${api.url.kvk}{kvkNumber}")
    Call<Void> getKVKNumber(@Path("kvkNumber") String kvkNumber);

    boolean validateKVKNumber(String KVKNumber);

}

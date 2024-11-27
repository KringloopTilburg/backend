package nl.kringlooptilburg.businessservice.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface KVKApiService {
    @GET("{kvkNumber}")
    Call<Void> getKVKNumber(@Path("kvkNumber") String kvkNumber);

    boolean validateKVKNumber(String KVKNumber);

}

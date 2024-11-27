package nl.kringlooptilburg.businessservice.services.impl;

import nl.kringlooptilburg.businessservice.services.KVKApiService;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

@Service
public class KVKServiceImpl {

    private final KVKApiService kvkApiService;

    public KVKServiceImpl(KVKApiService kvkApiService){
        this.kvkApiService = kvkApiService;
    }

    public boolean validateKVKNumber(String KVKNumber) {
        Call<Void> call = kvkApiService.getKVKNumber(KVKNumber);
        try {
            // Voer de aanroep uit en haal de response op
            Response<Void> response = call.execute();

            // Controleer of de response status 200 OK is
            if (response.isSuccessful()) {
                return true;  // KvK-nummer is geldig
            } else {
                return false; // KvK-nummer is ongeldig (bijvoorbeeld 404 of andere fout)
            }
        } catch (Exception e) {
            // In geval van een fout bij de aanroep (bijv. netwerkfout), retourneer false
            return false;
        }
    }
}

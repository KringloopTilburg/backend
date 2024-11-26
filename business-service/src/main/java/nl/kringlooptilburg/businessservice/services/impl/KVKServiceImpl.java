package nl.kringlooptilburg.businessservice.services.impl;

import nl.kringlooptilburg.businessservice.domain.entities.Business;
import nl.kringlooptilburg.businessservice.repositories.BusinessRepository;
import nl.kringlooptilburg.businessservice.services.BusinessService;
import nl.kringlooptilburg.businessservice.services.KVKService;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class KVKServiceImpl implements KVKService {
    private final KVKService kvkService;
    OkHttpClient client = new OkHttpClient();

    public KVKServiceImpl(KVKService kvkService) {
        this.kvkService = kvkService;
    }


    @Override
    public Call<Void> getKVKNumber(String kvkNumber) {
        return kvkService.getKVKNumber(kvkNumber);
    }

    @Override
    public boolean validateKVKNumber(String KVKNumber) {
        Call<Void> call = getKVKNumber(KVKNumber);
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

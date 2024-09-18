package kringlooptilburg.nl.productimageservice.services;

import kringlooptilburg.nl.productimageservice.domain.entities.Photo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface PhotoService {

    Photo addPhoto(String originalFilename, byte[] image, Integer productId);

    List<Photo> findAll();

    Optional<Photo> getPhoto(String id);

    void deletePhoto(String id);

    List<Photo> findByProductIds(List<Integer> productIds);
}

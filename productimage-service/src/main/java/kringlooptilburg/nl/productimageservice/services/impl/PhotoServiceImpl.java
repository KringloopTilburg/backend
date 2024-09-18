package kringlooptilburg.nl.productimageservice.services.impl;

import kringlooptilburg.nl.productimageservice.domain.entities.Photo;
import kringlooptilburg.nl.productimageservice.repository.PhotoRepository;
import kringlooptilburg.nl.productimageservice.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public Photo addPhoto(String originalFilename, byte[] image, Integer productId){
        Photo photo = new Photo();
        photo.setTitle(originalFilename);
        photo.setId(UUID.randomUUID().toString());
        photo.setBase64(Base64.getEncoder().encodeToString(image));
        photo.setProductId(productId);
        return photoRepository.save(photo);
    }

    @Override
    public void deletePhoto(String id) {
        photoRepository.delete(photoRepository.findById(id).get());
    }

    @Override
    public List<Photo> findByProductIds(List<Integer> productIds) {
        List<Photo> photos = new ArrayList<>();
        for (Integer productId : productIds) {
            Photo photo = photoRepository.findSinglePhotoByProductId(productId);
            if (photo != null) {
                photos.add(photo);
            }
        }
        return photos;
    }

    @Override
    public List<Photo> findAll() {
        return photoRepository.findAll();
    }

    @Override
    public Optional<Photo> getPhoto(String id) {
        return photoRepository.findById(id);
    }
}

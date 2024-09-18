package kringlooptilburg.nl.productimageservice.controllers;

import kringlooptilburg.nl.productimageservice.domain.dto.PhotoDto;
import kringlooptilburg.nl.productimageservice.domain.dto.ProductIdsRequest;
import kringlooptilburg.nl.productimageservice.domain.entities.Photo;
import kringlooptilburg.nl.productimageservice.mappers.Mapper;
import kringlooptilburg.nl.productimageservice.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productimage-service")
public class PhotoController {
    private final PhotoService photoService;
    private final Mapper<Photo, PhotoDto> productMapper;

    @Autowired
    public PhotoController(PhotoService photoService, Mapper<Photo, PhotoDto> productMapper) {
        this.photoService = photoService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ResponseEntity<String> addPhotoToProduct(@RequestPart("images") MultipartFile[] images, @RequestPart String productId) {
        try {
            for(var image : images){
                photoService.addPhoto(image.getOriginalFilename(), image.getBytes(), Integer.valueOf(productId));
            }

            return new ResponseEntity<>("Photos uploaded.", HttpStatus.CREATED);
        }
        catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    @PostMapping("/products/images")
    public ResponseEntity<List<PhotoDto>> getImagesForProducts(@RequestBody ProductIdsRequest request) {
        var productIds = request.getProductIds();

        var photos = photoService.findByProductIds(productIds);

        var photoDtos = mapToDtoList(photos);

        return ResponseEntity.ok().body(photoDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PhotoDto> deletePhoto(@PathVariable String id) {
        photoService.deletePhoto(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhotoDto> getPhoto(@PathVariable String id) {
        Optional<Photo> foundPhoto = photoService.getPhoto(id);
        return foundPhoto.map(photoScylla -> {
            PhotoDto photoDto = productMapper.mapTo(photoScylla);
            return new ResponseEntity<>(photoDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private List<PhotoDto> mapToDtoList(List<Photo> photos) {
        return photos.stream()
                .map(productMapper::mapTo)
                .collect(Collectors.toList());
    }
}

package kringlooptilburg.nl.productimageservice.mappers.impl;

import kringlooptilburg.nl.productimageservice.domain.dto.PhotoDto;
import kringlooptilburg.nl.productimageservice.domain.entities.Photo;
import kringlooptilburg.nl.productimageservice.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PhotoMapperImpl implements Mapper<Photo, PhotoDto> {
    private ModelMapper modelMapper;

    public PhotoMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PhotoDto mapTo(Photo photo) {
        return modelMapper.map(photo, PhotoDto.class);
    }

    @Override
    public Photo mapFrom(PhotoDto photoDto) {
        return modelMapper.map(photoDto, Photo.class);
    }
}

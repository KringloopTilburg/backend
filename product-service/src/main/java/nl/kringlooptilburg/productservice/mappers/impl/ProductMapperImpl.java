package nl.kringlooptilburg.productservice.mappers.impl;

import java.util.Optional;
import java.util.UUID;
import nl.kringlooptilburg.productservice.domain.dto.ProductDto;
import nl.kringlooptilburg.productservice.domain.entities.ColorEntity;
import nl.kringlooptilburg.productservice.domain.entities.ProductEntity;
import nl.kringlooptilburg.productservice.domain.entities.enums.Color;
import nl.kringlooptilburg.productservice.mappers.Mapper;
import nl.kringlooptilburg.productservice.services.ColorService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductMapperImpl implements Mapper<ProductEntity, ProductDto> {

    private final ModelMapper modelMapper;

    public ProductMapperImpl(ModelMapper modelMapper, ColorService colorService) {
        this.modelMapper = modelMapper;

        Converter<Set<Color>, Set<UUID>> colorToEntityConverter = context -> context.getSource().stream()
                .map(colorService::findOneByColor)
                .map(ColorEntity::getColorId)
                .collect(Collectors.toSet());

        Converter<Set<UUID>, Set<Color>> entityToColorConverter = context -> context.getSource().stream()
                .map(colorService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ColorEntity::getColor)
                .map(Color::valueOf)
                .collect(Collectors.toSet());

        modelMapper.typeMap(ProductDto.class, ProductEntity.class).addMappings(mapper -> mapper.using(colorToEntityConverter).map(ProductDto::getColors, ProductEntity::setColors));
        modelMapper.typeMap(ProductEntity.class, ProductDto.class).addMappings(mapper -> mapper.using(entityToColorConverter).map(ProductEntity::getColors, ProductDto::setColors));
    }

    @Override
    public ProductDto mapTo(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDto.class);
    }

    @Override
    public ProductEntity mapFrom(ProductDto productDto) {
        return modelMapper.map(productDto, ProductEntity.class);
    }
}

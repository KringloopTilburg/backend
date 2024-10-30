package nl.kringlooptilburg.productservice.services;

import java.util.UUID;
import nl.kringlooptilburg.productservice.domain.entities.ColorEntity;
import nl.kringlooptilburg.productservice.domain.entities.enums.Color;

import java.util.Optional;

public interface ColorService {
    ColorEntity findOneByColor(Color color);
    Optional<ColorEntity> findById(UUID colorId);
}

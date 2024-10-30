package nl.kringlooptilburg.productservice;

import java.util.UUID;
import java.util.stream.Collectors;
import nl.kringlooptilburg.productservice.domain.entities.ColorEntity;
import nl.kringlooptilburg.productservice.domain.entities.ProductEntity;
import nl.kringlooptilburg.productservice.domain.entities.enums.*;

import java.util.HashSet;
import java.util.Set;

public class TestDataUtil {
    private TestDataUtil(){
    }

    public static ProductEntity createTestProductEntityA(){
        Set<ColorEntity> colors = new HashSet<>();
        Set<UUID> emptyProductSet = new HashSet<>();
        colors.add(new ColorEntity(UUID.randomUUID(), Color.BLACK.name(), emptyProductSet));
        colors.add(new ColorEntity(UUID.randomUUID(), Color.GREY.name(), emptyProductSet));

        return ProductEntity.builder()
                .productId(UUID.randomUUID())
                .name("Grey Ripped Jeans")
                .description("Good condition, size L, grey ripped Jeans.")
                .price(30.0)
                .brand(Brand.ADIDAS.name())
                .category("Jeans")
                .size("L")
                .material(Material.ACRYLIC.name())
                .productCondition(ProductCondition.GOOD.name())
                .colors(colors.stream().map(ColorEntity::getColorId).collect(Collectors.toSet()))
                .audience(Audience.MALE.name())
                .build();
    }

    public static ProductEntity createTestProductEntityB(){
        Set<UUID> colors = new HashSet<>();
        Set<ProductEntity> emptyProductSet = new HashSet<>();
        colors.add(UUID.randomUUID());
        colors.add(UUID.randomUUID());

        return ProductEntity.builder()
                .productId(UUID.randomUUID())
                .name("Colourful Jacket")
                .description("New, size M, colourful jacket.")
                .price(20.0)
                .brand(Brand.ZARA.name())
                .category("Outerwear")
                .size("M")
                .material(Material.COTTON.name())
                .productCondition(ProductCondition.NEW.name())
                .colors(colors)
                .audience(Audience.FEMALE.name())
                .build();
    }

    public static String createExampleProductJson() {
        return "{\n" +
                "  \"productId\": 1,\n" +
                "  \"name\": \"Grey Ripped Jeans\",\n" +
                "  \"description\": \"Good condition, size L, grey ripped Jeans.\",\n" +
                "  \"price\": 30.0,\n" +
                "  \"brand\": \"ADIDAS\",\n" +
                "  \"category\": \"Jeans\",\n" +
                "  \"size\": \"L\",\n" +
                "  \"material\": \"ACRYLIC\",\n" +
                "  \"productCondition\": \"GOOD\",\n" +
                "  \"colors\": [\"RED\", \"BLUE\", \"GREEN\"],\n" +
                "  \"audience\": \"MALE\"\n" +
                "}";
    }

}

package nl.kringlooptilburg.productservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.kringlooptilburg.productservice.domain.entities.enums.Color;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Integer productId;

    private Integer businessId;

    private String name;

    private String description;

    private Double price;

    private String brand;

    private String category;

    private String size;

    private String material;

    private String productCondition;

    private Set<Color> colors;

    private String audience;

}

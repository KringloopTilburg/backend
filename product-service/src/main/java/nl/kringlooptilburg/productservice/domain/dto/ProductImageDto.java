package nl.kringlooptilburg.productservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageDto implements Serializable {
    private String productId;
    private String title;
    private byte[] image;
}

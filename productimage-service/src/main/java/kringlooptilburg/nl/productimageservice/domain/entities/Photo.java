package kringlooptilburg.nl.productimageservice.domain.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("photo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo {
    @Id
    private String id;
    private String title;
    private String base64;
    private Integer productId;
}

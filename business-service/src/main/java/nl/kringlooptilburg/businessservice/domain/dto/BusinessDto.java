package nl.kringlooptilburg.businessservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessDto {
    private Integer businessId;
    private String name;
    private String kvkNumber;
}

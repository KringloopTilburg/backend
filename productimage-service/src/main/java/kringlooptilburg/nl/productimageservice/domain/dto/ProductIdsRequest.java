package kringlooptilburg.nl.productimageservice.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductIdsRequest {
    private List<Integer> productIds;
}

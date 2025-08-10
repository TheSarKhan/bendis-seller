package com.sarkhan.backend.bendisseller.dto.product;

import java.util.List;

public record ProductResponseForSearchByName(String name,
                                             List<ProductResponseForGroupOfProduct> products) {
}

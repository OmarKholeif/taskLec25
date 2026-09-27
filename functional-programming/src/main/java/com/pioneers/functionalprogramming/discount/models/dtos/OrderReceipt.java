package com.pioneers.functionalprogramming.discount.models.dtos;

import java.math.BigDecimal;
import java.util.List;

public record OrderReceipt(List<ProductItem> productItems) {
    public BigDecimal totalPrice() {
        return productItems.stream()
                .map(ProductItem::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

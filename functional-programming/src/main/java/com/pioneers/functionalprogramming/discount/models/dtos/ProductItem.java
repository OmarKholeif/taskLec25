package com.pioneers.functionalprogramming.discount.models.dtos;

import com.pioneers.functionalprogramming.discount.utils.Category;
import com.pioneers.functionalprogramming.discount.utils.SubCategory;

import java.math.BigDecimal;

public record ProductItem(Category category, SubCategory subCategory, String productCode, int quantity, BigDecimal price) {

    public BigDecimal totalPrice() {
        return price.multiply(new BigDecimal(quantity));
    }
}

package com.pioneers.functionalprogramming.discount.rules;

import com.pioneers.functionalprogramming.discount.models.dtos.OrderReceipt;
import com.pioneers.functionalprogramming.discount.models.dtos.ProductItem;
import com.pioneers.functionalprogramming.discount.utils.Category;
import com.pioneers.functionalprogramming.discount.utils.SubCategory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Order(value = 1)
@Component
public class LaptopRule implements DiscountRule {
    @Override
    public BigDecimal applyDiscount(final OrderReceipt orderReceipt, final BigDecimal totalPrice) {
        final boolean isDiscountMatched = orderReceipt.productItems()
                .stream()
                .anyMatch(LaptopRule::isProductLaptop);

        if (!isDiscountMatched) {
            return totalPrice;
        }

        return totalPrice.subtract(BigDecimal.valueOf(500));
    }

    private static boolean isProductLaptop(final ProductItem item) {
        return Category.ELECTRONICS.equals(item.category()) && SubCategory.LAPTOP.equals(item.subCategory());
    }
}

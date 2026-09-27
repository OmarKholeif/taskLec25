package com.pioneers.functionalprogramming.discount.rules;

import com.pioneers.functionalprogramming.discount.models.dtos.OrderReceipt;
import com.pioneers.functionalprogramming.discount.models.dtos.ProductItem;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Order(value = 2)
@Component
public class QuantityRule implements DiscountRule {
    @Override
    public BigDecimal applyDiscount(final OrderReceipt orderReceipt, final BigDecimal totalPrice) {
        final boolean isDiscountMatched = orderReceipt.productItems()
                .stream()
                .anyMatch(QuantityRule::isProductMoreFiveItems);

        if (!isDiscountMatched) {
            return totalPrice;
        }

        return totalPrice.multiply(BigDecimal.valueOf(0.95));
    }

    private static boolean isProductMoreFiveItems(final ProductItem productItem) {
        return productItem.quantity() > 5;
    }
}

package com.pioneers.functionalprogramming.discount.rules;

import com.pioneers.functionalprogramming.discount.models.dtos.OrderReceipt;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Order(value = 3)
@Component
public class HighPriceRule implements DiscountRule {

    private static final BigDecimal FIFTY_THOUSANDS = BigDecimal.valueOf(50000);

    @Override
    public BigDecimal applyDiscount(final OrderReceipt orderReceipt, final BigDecimal totalPrice) {
        if (isPriceLessAmount(totalPrice, FIFTY_THOUSANDS)) {
            return totalPrice;
        }

        return totalPrice.multiply(BigDecimal.valueOf(0.9));
    }

    private static boolean isPriceLessAmount(final BigDecimal price, final BigDecimal amount) {
        return price.compareTo(amount) < 0;
    }
}

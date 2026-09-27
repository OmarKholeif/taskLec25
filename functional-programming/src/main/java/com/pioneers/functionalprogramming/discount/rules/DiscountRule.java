package com.pioneers.functionalprogramming.discount.rules;

import com.pioneers.functionalprogramming.discount.models.dtos.OrderReceipt;

import java.math.BigDecimal;

public interface DiscountRule {

    BigDecimal applyDiscount(OrderReceipt orderReceipt, BigDecimal totalPrice);
}

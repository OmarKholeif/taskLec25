package com.pioneers.functionalprogramming.discount.services;

import com.pioneers.functionalprogramming.discount.models.dtos.OrderReceipt;

import java.math.BigDecimal;

public interface DiscountService {

    BigDecimal applyDiscount(OrderReceipt orderReceipt);
}

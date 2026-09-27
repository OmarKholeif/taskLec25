package com.pioneers.functionalprogramming.discount.services;

import com.pioneers.functionalprogramming.discount.models.dtos.OrderReceipt;
import com.pioneers.functionalprogramming.discount.rules.DiscountEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountEngine discountEngine;

    @Override
    public BigDecimal applyDiscount(final OrderReceipt orderReceipt) {
        final BigDecimal priceAfterDiscount = discountEngine.applyDiscount(orderReceipt);

        log.debug("applyDiscount(), Price after discount = [{}]", priceAfterDiscount);
        return priceAfterDiscount;
    }
}

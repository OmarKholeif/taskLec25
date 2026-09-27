package com.pioneers.functionalprogramming.discount.services;

import com.pioneers.functionalprogramming.discount.models.dtos.OrderReceipt;
import com.pioneers.functionalprogramming.discount.models.dtos.ProductItem;
import com.pioneers.functionalprogramming.discount.rules.DiscountEngine;
import com.pioneers.functionalprogramming.discount.utils.Category;
import com.pioneers.functionalprogramming.discount.utils.SubCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountServiceImplTest {

    @Mock
    private DiscountEngine discountEngine;

    @InjectMocks
    private DiscountServiceImpl discountService;

    @Test
    void testApplyDiscount_whenLaptopRuleApplied_then500DiscountApplied() {
        // Arrange
        final List<ProductItem> productItems = List.of(
                new ProductItem(Category.ELECTRONICS, SubCategory.LAPTOP, "LP123", 1, BigDecimal.valueOf(40000)),
                new ProductItem(Category.CLOTHES, SubCategory.T_SHIRT, "TS123", 1, BigDecimal.valueOf(300))
        );

        final OrderReceipt orderReceipt = new OrderReceipt(productItems);

        final BigDecimal totalPrice = orderReceipt.totalPrice();

        when(discountEngine.applyDiscount(orderReceipt))
                .thenReturn(totalPrice.subtract(BigDecimal.valueOf(500)));

        // Action
        final BigDecimal priceAfterDiscount = discountService.applyDiscount(orderReceipt);

        // Assert
        assertEquals(BigDecimal.valueOf(39800), priceAfterDiscount);
        verify(discountEngine, times(1)).applyDiscount(orderReceipt);
        assertDoesNotThrow(() -> RuntimeException.class);
    }

    @Test
    void testApplyDiscount_whenLaptopRuleAndQuantityApplied_then500DiscountAnd5PercentApplied() {
        // Arrange
        final List<ProductItem> productItems = List.of(
                new ProductItem(Category.ELECTRONICS, SubCategory.LAPTOP, "LP123", 1, BigDecimal.valueOf(40000)),
                new ProductItem(Category.CLOTHES, SubCategory.T_SHIRT, "TS123", 1, BigDecimal.valueOf(300)),
                new ProductItem(Category.CLOTHES, SubCategory.PANT, "PT123", 6, BigDecimal.valueOf(500))
        );

        final OrderReceipt orderReceipt = new OrderReceipt(productItems);

        final BigDecimal totalPrice = orderReceipt.totalPrice();
        final BigDecimal laptopDiscount = totalPrice.subtract(BigDecimal.valueOf(500));
        final BigDecimal quantityDiscount = laptopDiscount.multiply(BigDecimal.valueOf(0.95));

        when(discountEngine.applyDiscount(orderReceipt))
                .thenReturn(quantityDiscount);

        // Action
        final BigDecimal priceAfterDiscount = discountService.applyDiscount(orderReceipt);

        // Assert
        assertEquals(quantityDiscount, priceAfterDiscount);
        verify(discountEngine).applyDiscount(orderReceipt);
    }

    @Test
    void testApplyDiscount_whenLaptopRuleAndQuantityAndHighPriceApplied_then500DiscountAnd5PercentAnd10PercentApplied()
    {
        // Arrange
        final List<ProductItem> productItems = List.of(
                new ProductItem(Category.ELECTRONICS, SubCategory.LAPTOP, "LP123", 1, BigDecimal.valueOf(40000)),
                new ProductItem(Category.CLOTHES, SubCategory.T_SHIRT, "TS123", 1, BigDecimal.valueOf(300)),
                new ProductItem(Category.CLOTHES, SubCategory.PANT, "PT123", 6, BigDecimal.valueOf(500)),
                new ProductItem(Category.ELECTRIC_SETS, SubCategory.REFRIGERAROR, "RF123", 1, BigDecimal.valueOf(40000))
        );

        final OrderReceipt orderReceipt = new OrderReceipt(productItems);

        final BigDecimal totalPrice = orderReceipt.totalPrice();
        final BigDecimal laptopDiscount = totalPrice.subtract(BigDecimal.valueOf(500));
        final BigDecimal quantityDiscount = laptopDiscount.multiply(BigDecimal.valueOf(0.95));
        final BigDecimal highPriceDiscount = quantityDiscount.multiply(BigDecimal.valueOf(0.9));

        when(discountEngine.applyDiscount(orderReceipt))
                .thenReturn(highPriceDiscount);

        // Action
        final BigDecimal priceAfterDiscount = discountService.applyDiscount(orderReceipt);

        // Assert
        assertEquals(highPriceDiscount, priceAfterDiscount);
        verify(discountEngine, times(1)).applyDiscount(orderReceipt);
    }
}

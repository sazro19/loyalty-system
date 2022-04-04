package com.es.loyalty.facades;

import com.es.loyalty.DTO.calculation.CalculationResponse;

import java.math.BigDecimal;

public interface CalculationFacade {
    CalculationResponse calculateWithEmail(final BigDecimal price, String email);

    CalculationResponse calculateWithCoupon(final BigDecimal price, final String code);
}

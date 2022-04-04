package com.es.loyalty.services.priceCalculation;

import com.es.loyalty.DTO.calculation.CalculationInfo;

import java.math.BigDecimal;

public interface PriceCalculationService {
    BigDecimal calcPrice(final CalculationInfo calculationInfo);
}

package com.es.loyalty.validators.calculation;

import com.es.loyalty.DTO.calculation.AbstractCalculation;
import com.es.loyalty.constants.LoyaltyConstants;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Component
public class CalculationValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return AbstractCalculation.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        validatePrice(o, errors);
    }

    protected void validatePrice(Object o, Errors errors) {
        final AbstractCalculation calc = ((AbstractCalculation) o);

        if (calc.getPrice() == null || calc.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            errors.reject(LoyaltyConstants.PRICE, LoyaltyConstants.INVALID_OR_EMPTY_PRICE);
        }
    }
}

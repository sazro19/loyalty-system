package com.es.loyalty.validators.calculation;

import com.es.loyalty.DTO.calculation.CalculationWithCouponRequest;
import com.es.loyalty.constants.LoyaltyConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

@Component
public class CalculationCouponValidator extends CalculationValidator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CalculationWithCouponRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        validatePrice(o, errors);

        validateCode(o, errors);
    }

    protected void validateCode(Object o, Errors errors) {
        final CalculationWithCouponRequest calc = ((CalculationWithCouponRequest) o);

        if (StringUtils.isEmpty(calc.getCode())) {
            errors.reject(LoyaltyConstants.CODE, LoyaltyConstants.EMPTY_CODE);
        }
    }
}

package com.es.loyalty.validators.calculation;

import com.es.loyalty.DTO.calculation.CalculationWithBonusRequest;
import com.es.loyalty.constants.LoyaltyConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import java.util.regex.Pattern;

@Component
public class CalculationBonusValidator extends CalculationValidator {

    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    @Override
    public boolean supports(Class<?> aClass) {
        return CalculationWithBonusRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        validatePrice(o, errors);

        validateEmail(o, errors);
    }

    protected void validateEmail(Object o, Errors errors) {
        final CalculationWithBonusRequest calc = ((CalculationWithBonusRequest) o);

        if (StringUtils.isEmpty(calc.getEmail())) {
            errors.reject(LoyaltyConstants.EMAIL, LoyaltyConstants.EMPTY_EMAIL);
        } else if (!Pattern.matches(EMAIL_PATTERN, calc.getEmail())) {
            errors.reject(LoyaltyConstants.EMAIL, LoyaltyConstants.INVALID_EMAIL);
        }
    }
}

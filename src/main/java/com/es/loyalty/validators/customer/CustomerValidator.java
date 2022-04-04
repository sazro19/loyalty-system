package com.es.loyalty.validators.customer;

import com.es.loyalty.DTO.customer.CustomerInfo;
import com.es.loyalty.constants.LoyaltyConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CustomerValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomerInfo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        validateCustomer(o, errors);
    }

    protected void validateCustomer(Object o, Errors errors) {
        final CustomerInfo customerInfo = ((CustomerInfo) o);

        if (StringUtils.isEmpty(customerInfo.getEmail())) {
            errors.reject(LoyaltyConstants.EMAIL, LoyaltyConstants.EMPTY_EMAIL);
        }

        if (customerInfo.getBonus() == null || customerInfo.getBonus() < 0) {
            errors.reject(LoyaltyConstants.BONUS, LoyaltyConstants.INVALID_OR_EMPTY_BONUS);
        }
    }
}

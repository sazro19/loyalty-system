package com.es.loyalty.facades.impl;

import com.es.loyalty.DTO.calculation.CalculationInfo;
import com.es.loyalty.DTO.calculation.CalculationResponse;
import com.es.loyalty.constants.LoyaltyConstants;
import com.es.loyalty.facades.CalculationFacade;
import com.es.loyalty.model.coupon.Coupon;
import com.es.loyalty.model.coupon.CouponDao;
import com.es.loyalty.model.customer.Customer;
import com.es.loyalty.model.customer.CustomerDao;
import com.es.loyalty.services.priceCalculation.PriceCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class CalculationFacadeImpl implements CalculationFacade {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private PriceCalculationService priceCalculationService;

    @Override
    public CalculationResponse calculateWithEmail(final BigDecimal price, final String email) {
        if (StringUtils.isEmpty(email)) {
            throw new IllegalArgumentException(LoyaltyConstants.EMPTY_EMAIL);
        }

        final Optional<Customer> optionalCustomer = customerDao.get(email);

        final CalculationInfo calculationInfo = new CalculationInfo();
        calculationInfo.setPrice(price);
        calculationInfo.setCustomer(optionalCustomer.orElseThrow(() -> {
            throw new RuntimeException(String.format(LoyaltyConstants.CUSTOMER_DOES_NOT_EXIST, email));
        }));

        return commonCalculation(calculationInfo);
    }

    @Override
    public CalculationResponse calculateWithCoupon(final BigDecimal price, final String code) {
        if (StringUtils.isEmpty(code)) {
            throw new IllegalArgumentException(LoyaltyConstants.EMPTY_CODE);
        }

        final Optional<Coupon> optionalCoupon = couponDao.get(code);

        final CalculationInfo calculationInfo = new CalculationInfo();
        calculationInfo.setPrice(price);
        calculationInfo.setCoupon(optionalCoupon.orElseThrow(() -> {
            throw new RuntimeException(String.format(LoyaltyConstants.COUPON_DOES_NOT_EXIST, code));
        }));

        return commonCalculation(calculationInfo);
    }

    private CalculationResponse commonCalculation(final CalculationInfo calculationInfo) {
        final CalculationResponse response = new CalculationResponse();
        try {
            final BigDecimal calculatedPrice = priceCalculationService.calcPrice(calculationInfo);

            response.setSuccess(true);
            response.setPrice(calculatedPrice);
        } catch (final RuntimeException e) {
            response.setSuccess(false);
            response.setErrorReason(e.getMessage());
        }

        return response;
    }
}

package com.es.loyalty.services.priceCalculation.impl;

import com.es.loyalty.DTO.calculation.CalculationInfo;
import com.es.loyalty.model.coupon.Coupon;
import com.es.loyalty.model.customer.Customer;
import com.es.loyalty.services.priceCalculation.PriceCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@PropertySource("classpath:/config/price.properties")
public class PriceCalculationServiceImpl implements PriceCalculationService {
    @Autowired
    private Environment environment;

    private static final String ONE_BONUS_PRICE_PROPERTY = "price.bonus";

    @Override
    public BigDecimal calcPrice(final CalculationInfo calculationInfo) {
        if (calculationInfo == null) {
            throw new IllegalArgumentException("Cannot calc price with null data");
        }

        final BigDecimal price = calculationInfo.getPrice();
        if (price == null || price.compareTo(BigDecimal.ONE) <= 0) {
            throw new IllegalArgumentException("Price cannot be null or non positive");
        }

        BigDecimal resultPrice = new BigDecimal(price.toString());

        final Customer customer = calculationInfo.getCustomer();
        if (customer != null && customer.getBonus() != null) {
            resultPrice = calcWithBonus(resultPrice, calculationInfo.getCustomer());
        }

        final Coupon coupon = calculationInfo.getCoupon();
        if (coupon != null && coupon.getOff() != null) {
            resultPrice = calcWithCoupon(resultPrice, coupon);
        }

        return resultPrice;
    }

    private BigDecimal calcWithBonus(final BigDecimal price, final Customer customer) {
        final BigDecimal calculatedPrice = new BigDecimal(price.toString());

        final Integer bonus = customer.getBonus();
        if (bonus != null && bonus > 0) {
            final BigDecimal oneBonusPrice = new BigDecimal(environment.getProperty(ONE_BONUS_PRICE_PROPERTY));
            final BigDecimal priceToSubtract = oneBonusPrice.multiply(new BigDecimal(bonus));

            return calculatedPrice.subtract(priceToSubtract);
        }

        return calculatedPrice;
    }

    private BigDecimal calcWithCoupon(final BigDecimal price, final Coupon coupon) {
        final BigDecimal calculatedPrice = new BigDecimal(price.toString());

        final BigDecimal off = coupon.getOff();
        if (off != null) {
            return calculatedPrice.subtract(off.multiply(calculatedPrice));
        }

        return calculatedPrice;
    }
}

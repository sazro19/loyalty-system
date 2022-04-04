package com.es.loyalty.DTO.calculation;

import com.es.loyalty.model.coupon.Coupon;
import com.es.loyalty.model.customer.Customer;

public class CalculationInfo extends AbstractCalculation {

    private Customer customer;

    private Coupon coupon;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}

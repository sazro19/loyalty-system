package com.es.loyalty.services.coupon;

import com.es.loyalty.model.coupon.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    Coupon createCoupon(final Coupon coupon);

    List<Coupon> findAll();

    Optional<Coupon> getCouponById(final Long id);
}

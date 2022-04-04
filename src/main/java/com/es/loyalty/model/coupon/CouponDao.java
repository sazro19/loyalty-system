package com.es.loyalty.model.coupon;

import java.util.List;
import java.util.Optional;

public interface CouponDao {
    Optional<Coupon> get(final Long key);

    Optional<Coupon> get(final String code);

    void save(final Coupon coupon);

    List<Coupon> findAll();
}

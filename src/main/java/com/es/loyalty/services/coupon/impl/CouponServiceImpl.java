package com.es.loyalty.services.coupon.impl;

import com.es.loyalty.model.coupon.Coupon;
import com.es.loyalty.model.coupon.CouponDao;
import com.es.loyalty.services.coupon.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDao couponDao;

    @Override
    public Coupon createCoupon(final Coupon coupon) {
        if (coupon != null) {
            couponDao.save(coupon);

            return coupon;
        }

        return null;
    }

    @Override
    public List<Coupon> findAll() {
        return couponDao.findAll();
    }

    @Override
    public Optional<Coupon> getCouponById(final Long id) {
        if (id != null) {
            return couponDao.get(id);
        }

        return Optional.empty();
    }
}

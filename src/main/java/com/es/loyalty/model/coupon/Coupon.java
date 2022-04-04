package com.es.loyalty.model.coupon;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Coupon implements Serializable {
    private Long id;

    private String code;

    private BigDecimal off;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getOff() {
        return off;
    }

    public void setOff(BigDecimal off) {
        this.off = off;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return Objects.equals(id, coupon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

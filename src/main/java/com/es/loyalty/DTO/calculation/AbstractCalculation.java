package com.es.loyalty.DTO.calculation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

public abstract class AbstractCalculation {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

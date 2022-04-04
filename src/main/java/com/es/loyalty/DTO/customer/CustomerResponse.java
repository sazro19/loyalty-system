package com.es.loyalty.DTO.customer;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CustomerResponse extends AbstractCustomerInfo {

    protected boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String errorReason;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }
}

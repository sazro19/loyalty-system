package com.es.loyalty.DTO.calculation;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CalculationResponse extends AbstractCalculation {

    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorReason;

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

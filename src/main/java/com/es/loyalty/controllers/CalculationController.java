package com.es.loyalty.controllers;

import com.es.loyalty.DTO.calculation.CalculationResponse;
import com.es.loyalty.DTO.calculation.CalculationWithBonusRequest;
import com.es.loyalty.DTO.calculation.CalculationWithCouponRequest;
import com.es.loyalty.facades.CalculationFacade;
import com.es.loyalty.validators.calculation.CalculationBonusValidator;
import com.es.loyalty.validators.calculation.CalculationCouponValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/calculate")
public class CalculationController {

    @Autowired
    private CalculationBonusValidator calculationBonusValidator;

    @Autowired
    private CalculationCouponValidator calculationCouponValidator;

    @Autowired
    private CalculationFacade calculationFacade;

    @ResponseBody
    @PostMapping(value = "/bonus")
    public CalculationResponse calculatePriceWithBonus(@RequestBody final CalculationWithBonusRequest request,
                                                       final BindingResult bindingResult) {
        calculationBonusValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors()) {
            return handleFailedRequest(bindingResult);
        }

        CalculationResponse response = new CalculationResponse();
        try {
            response = calculationFacade.calculateWithEmail(request.getPrice(), request.getEmail());
        } catch (final RuntimeException e) {
            response.setSuccess(false);
            response.setErrorReason(e.getMessage());
        }

        return response;
    }

    @ResponseBody
    @PostMapping(value = "/coupon")
    public CalculationResponse calculatePriceWithCoupon(@RequestBody final CalculationWithCouponRequest request,
                                                        final BindingResult bindingResult) {
        calculationCouponValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors()) {
            return handleFailedRequest(bindingResult);
        }

        CalculationResponse response = new CalculationResponse();
        try {
            response = calculationFacade.calculateWithCoupon(request.getPrice(), request.getCode());
        } catch (final RuntimeException e) {
            response.setSuccess(false);
            response.setErrorReason(e.getMessage());
        }

        return response;
    }

    private CalculationResponse handleFailedRequest(final BindingResult bindingResult) {
        final CalculationResponse response = new CalculationResponse();
        response.setSuccess(false);

        response.setErrorReason(bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", ")));

        return response;
    }
}

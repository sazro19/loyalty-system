package com.es.loyalty.controllers;

import com.es.loyalty.DTO.customer.CustomerInfo;
import com.es.loyalty.DTO.customer.CustomerResponse;
import com.es.loyalty.facades.CustomerFacade;
import com.es.loyalty.model.customer.Customer;
import com.es.loyalty.validators.customer.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerValidator customerValidator;

    @Autowired
    private CustomerFacade customerFacade;

    @ResponseBody
    @GetMapping
    public List<Customer> findAllCustomers() {
        return customerFacade.findAllCustomers();
    }

    @ResponseBody
    @PostMapping(value = "/save")
    public CustomerResponse saveCustomer(@RequestBody final CustomerInfo request,
                                         final BindingResult bindingResult) {
        customerValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors()) {
            return handleFailedRequest(bindingResult);
        }

        CustomerResponse response = new CustomerResponse();
        try {
            response = customerFacade.saveCustomer(request);
            response.setSuccess(true);
        } catch (final RuntimeException e) {
            response.setSuccess(false);
            response.setEmail(request.getEmail());
        }

        return response;
    }

    private CustomerResponse handleFailedRequest(final BindingResult bindingResult) {
        final CustomerResponse response = new CustomerResponse();
        response.setSuccess(false);

        response.setErrorReason(bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", ")));

        return response;
    }
}

package com.es.loyalty.facades;

import com.es.loyalty.DTO.customer.CustomerInfo;
import com.es.loyalty.DTO.customer.CustomerResponse;
import com.es.loyalty.model.customer.Customer;

import java.util.List;

public interface CustomerFacade {
    CustomerResponse saveCustomer(final CustomerInfo customerInfo);

    List<Customer> findAllCustomers();
}

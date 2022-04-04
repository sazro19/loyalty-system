package com.es.loyalty.services.customer;

import com.es.loyalty.model.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer createCustomer(final Customer customer);

    List<Customer> findAll();

    Optional<Customer> getCustomerById(final Long id);

    void updateBonus(Customer customer);
}

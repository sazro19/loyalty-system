package com.es.loyalty.model.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    Optional<Customer> get(String email);

    Optional<Customer> get(Long id);

    void save(Customer customer);

    List<Customer> findAll();

    void updateBonus(final Customer customer);
}

package com.es.loyalty.services.customer.impl;

import com.es.loyalty.model.customer.Customer;
import com.es.loyalty.model.customer.CustomerDao;
import com.es.loyalty.services.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public Customer createCustomer(final Customer customer) {
        if (customer != null) {
            customerDao.save(customer);

            return customer;
        }

        return null;
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(final Long id) {
        if (id != null) {
            return customerDao.get(id);
        }

        return Optional.empty();
    }

    @Override
    public void updateBonus(final Customer customer) {
        if (customer != null) {
            final Integer bonus = customer.getBonus();
            if (bonus != null && bonus >= 0) {
                customerDao.updateBonus(customer);
            }
        }
    }
}

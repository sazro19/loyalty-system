package com.es.loyalty.facades.impl;

import com.es.loyalty.DTO.customer.CustomerInfo;
import com.es.loyalty.DTO.customer.CustomerResponse;
import com.es.loyalty.constants.LoyaltyConstants;
import com.es.loyalty.facades.CustomerFacade;
import com.es.loyalty.model.customer.Customer;
import com.es.loyalty.model.customer.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class CustomerFacadeImpl implements CustomerFacade {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public CustomerResponse saveCustomer(final CustomerInfo customerInfo) {
        final String email = customerInfo.getEmail();
        final Integer bonus = customerInfo.getBonus();

        if (StringUtils.isEmpty(email)) {
            throw new IllegalArgumentException(LoyaltyConstants.EMPTY_EMAIL);
        }
        if (bonus == null || bonus < 0) {
            throw new IllegalArgumentException(LoyaltyConstants.INVALID_OR_EMPTY_BONUS);
        }

        final Customer customer = new Customer();
        customer.setEmail(email);
        customer.setBonus(bonus);

        customerDao.save(customer);

        return successCustomerResponse(customer);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerDao.findAll();
    }

    private CustomerResponse successCustomerResponse(final Customer customer) {
        final CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setSuccess(true);
        customerResponse.setEmail(customer.getEmail());

        return customerResponse;
    }
}

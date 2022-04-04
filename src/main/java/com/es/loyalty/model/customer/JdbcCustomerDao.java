package com.es.loyalty.model.customer;

import com.es.loyalty.model.JdbcLoyaltyInsertClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@PropertySource("classpath:/config/errors.properties")
public class JdbcCustomerDao implements CustomerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResultSetExtractor<List<Customer>> customerExtractor;

    @Autowired
    private JdbcLoyaltyInsertClass jdbcLoyaltyInsertClass;

    @Autowired
    private Environment environment;

    private static final String SELECT_ONE_BY_EMAIL_SQL_QUERY = "SELECT id, email, bonus " +
            "FROM customers " +
            "WHERE email = ?";

    private static final String SELECT_ONE_BY_ID_SQL_QUERY = "SELECT id, email, bonus " +
            "FROM customers " +
            "WHERE id = ?";

    private static final String SELECT_ALL_SQL_QUERY = "SELECT id, email, bonus " +
            "FROM customers";

    private static final String UPDATE_CUSTOMER_BONUS_SQL_QUERY = "UPDATE customers SET bonus = ? " +
            "WHERE id = ?";

    private static final String UPDATE_CUSTOMER_QUERY = "UPDATE customers SET email=:email, bonus=:bonus " +
            "WHERE id=:id";

    @Override
    public Optional<Customer> get(String email) {
        List<Customer> result = jdbcTemplate.query(SELECT_ONE_BY_EMAIL_SQL_QUERY, new Object[]{email}, customerExtractor);

        return getResultCustomer(result);
    }

    @Override
    public Optional<Customer> get(Long id) {
        List<Customer> result = jdbcTemplate.query(SELECT_ONE_BY_ID_SQL_QUERY, new Object[]{id}, customerExtractor);

        return getResultCustomer(result);
    }

    @Override
    public void save(Customer customer) {
        final Optional<Customer> optionalCustomer = get(customer.getEmail());
        if (!optionalCustomer.isPresent()) {
            insertNewCustomer(customer);
        } else {
            customer.setId(optionalCustomer.get().getId());
            update(customer);
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL_QUERY, customerExtractor);
    }

    @Override
    public void updateBonus(final Customer customer) {
        jdbcTemplate.update(UPDATE_CUSTOMER_BONUS_SQL_QUERY, customer.getBonus().toString(), customer.getId());
    }

    private Optional<Customer> getResultCustomer(List<Customer> customers) {
        if (customers.size() == 0) {
            return Optional.empty();
        }

        Customer resultCustomer = customers.get(0);
        return Optional.of(resultCustomer);
    }

    private void update(final Customer customer) {
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        parameterJdbcTemplate.update(UPDATE_CUSTOMER_QUERY, new BeanPropertySqlParameterSource(customer));
    }

    private void insertNewCustomer(final Customer customer) {
        Long newId = jdbcLoyaltyInsertClass.insertAndReturnGeneratedKey("customers",
                new BeanPropertySqlParameterSource(customer), "id").longValue();
        customer.setId(newId);
    }
}

package com.es.loyalty.model.customer.extractorConfig;

import com.es.loyalty.model.customer.Customer;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.List;

@Configuration
public class CustomerExtractorConfig {

    @Bean
    public ResultSetExtractor<List<Customer>> customerExtractor(JdbcTemplateMapperFactory jdbcTemplateMapperFactory) {
        return jdbcTemplateMapperFactory.addKeys("id")
                .newResultSetExtractor(Customer.class);
    }
}

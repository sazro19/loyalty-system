package com.es.loyalty.model.coupon.extractorConfig;

import com.es.loyalty.model.coupon.Coupon;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.List;

@Configuration
public class CouponExtractorConfig {

    @Bean
    public ResultSetExtractor<List<Coupon>> getCouponResultSetExtractor(JdbcTemplateMapperFactory jdbcTemplateMapperFactory) {
        return jdbcTemplateMapperFactory.addKeys("id")
                .newResultSetExtractor(Coupon.class);
    }

    @Bean
    public JdbcTemplateMapperFactory jdbcTemplateMapperFactory() {
        return JdbcTemplateMapperFactory.newInstance();
    }
}

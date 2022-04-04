package com.es.loyalty.model.coupon;

import com.es.loyalty.model.JdbcLoyaltyInsertClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
public class JdbcCouponDao implements CouponDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResultSetExtractor<List<Coupon>> resultSetExtractor;

    @Autowired
    private JdbcLoyaltyInsertClass jdbcLoyaltyInsertClass;

    private static final String SELECT_COUPON_BY_ID_QUERY = "SELECT id, code, off " +
            "FROM coupons " +
            "WHERE id = ?";

    private static final String SELECT_COUPON_BY_CODE_QUERY = "SELECT id, code, off " +
            "FROM coupons " +
            "WHERE code = ?";

    private static final String SELECT_ALL_QUERY = "SELECT id, code, off " +
            "FROM coupons";

    private static final String UPDATE_COUPON_QUERY = "UPDATE coupons SET code=:code, off=:off " +
            "WHERE id=:id";

    private static final String COUPONS_TABLE_NAME = "coupons";

    @Override
    @Transactional(readOnly = true)
    public Optional<Coupon> get(final Long id) {
        List<Coupon> result = jdbcTemplate.query(SELECT_COUPON_BY_ID_QUERY, resultSetExtractor, id);

        if (result != null && !result.isEmpty()) {
            return Optional.of(result.get(0));
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Coupon> get(final String code) {
        List<Coupon> result = jdbcTemplate.query(SELECT_COUPON_BY_CODE_QUERY, resultSetExtractor, code);

        if (result != null && !result.isEmpty()) {
            return Optional.of(result.get(0));
        }
        return Optional.empty();
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public void save(final Coupon coupon) {
        if (coupon.getId() == null) {
            insertNewCoupon(coupon);
        }
        update(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Coupon> findAll() {
        List<Coupon> result = jdbcTemplate.query(SELECT_ALL_QUERY, resultSetExtractor);

        return result;
    }

    private void update(final Coupon coupon) {
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        parameterJdbcTemplate.update(UPDATE_COUPON_QUERY, new BeanPropertySqlParameterSource(coupon));
    }

    private void insertNewCoupon(Coupon coupon) {
        Long newId = jdbcLoyaltyInsertClass.insertAndReturnGeneratedKey(COUPONS_TABLE_NAME,
                new BeanPropertySqlParameterSource(coupon), "id").longValue();
        coupon.setId(newId);
    }
}

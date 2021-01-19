package com.patres.proxyframework.app.dao;

import com.patres.proxyframework.app.model.Payment;
import com.patres.proxyframework.framework.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentDaoImpl implements PaymentDao {

    private static final Logger logger = LoggerFactory.getLogger(PaymentDaoImpl.class);

    @Override
    @Transactional
    public void create(Payment payment) {
        logger.debug("Starting create()...");
        update(payment);
        logger.debug("Ending create()...");
    }

    @Override
    @Transactional
    public void update(Payment payment) {
        logger.debug("Starting update()...");

        logger.debug("Ending update()...");
    }

}

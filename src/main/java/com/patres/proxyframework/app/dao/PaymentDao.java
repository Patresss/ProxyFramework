package com.patres.proxyframework.app.dao;

import com.patres.proxyframework.app.model.Payment;

public interface PaymentDao {

    void create(Payment payment);

    void update(Payment payment);

}

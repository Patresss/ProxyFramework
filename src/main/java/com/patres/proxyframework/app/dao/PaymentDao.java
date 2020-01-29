package com.patres.proxyframework.app.dao;

import com.patres.proxyframework.app.model.Payment;
import com.patres.proxyframework.framework.annotation.ProxyFrameTransactional;

public interface PaymentDao {

    void create(Payment payment);
    void update(Payment payment);

}

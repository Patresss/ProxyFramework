package com.patres.proxyframework.app;

import com.patres.proxyframework.app.dao.PaymentDao;
import com.patres.proxyframework.app.dao.PaymentDaoImpl;
import com.patres.proxyframework.app.model.Payment;
import com.patres.proxyframework.framework.handler.ProxyFrameInvocationHandler;

import java.lang.reflect.Proxy;

public class MainApp {

    public static void main(String[] args) {
        PaymentDao paymentDaoProxy = (PaymentDao) Proxy.newProxyInstance(
                MainApp.class.getClassLoader(),
                new Class[]{PaymentDao.class},
                new ProxyFrameInvocationHandler(new PaymentDaoImpl())
        );
        Payment payment = new Payment();
        paymentDaoProxy.update(payment);
        paymentDaoProxy.update(payment);
    }
}

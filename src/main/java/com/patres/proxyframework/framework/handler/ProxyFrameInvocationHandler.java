package com.patres.proxyframework.framework.handler;

import com.patres.proxyframework.framework.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProxyFrameInvocationHandler implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(ProxyFrameInvocationHandler.class);
    private final Object original;

    public ProxyFrameInvocationHandler(Object original) {
        this.original = original;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        if (hasProxyFrameTransactionalAnnotation(method)) {
            return invokeTransaction(method, args);
        }
        return method.invoke(original, args);
    }

    private Object invokeTransaction(Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        logger.info("Invoked method:  {} with arguments: {}", method.getName(), args);
        try {
            Object result = method.invoke(original, args);
            logger.info("Finished method: {} with arguments: {}", method.getName(), args);
            return result;
        } catch (Exception e) {
            logger.info("Error - rollback", e);
            throw e;
        }
    }

    private boolean hasProxyFrameTransactionalAnnotation(final Method method) throws NoSuchMethodException {
        return original.getClass().getMethod(method.getName(), method.getParameterTypes()).getAnnotation(Transactional.class) != null;
    }

}

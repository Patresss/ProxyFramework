package com.patres.proxyframework.framework.handler;

import com.patres.proxyframework.framework.annotation.ProxyFrameCacheable;
import com.patres.proxyframework.framework.annotation.ProxyFrameTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

public class ProxyFrameInvocationHandler implements InvocationHandler {

    private final static Logger logger = LoggerFactory.getLogger(ProxyFrameInvocationHandler.class);
    private final Object original;

    private final Map<List<Object>, Object> cacheContainers = new HashMap<>();

    public ProxyFrameInvocationHandler(Object original) {
        this.original = original;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        if (hasProxyFrameCacheableAnnotation(method)) {
            return invokeCacheable(method, args);
        }
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

    private Object invokeCacheable(Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        List<Object> keyCache = createKeyCache(method, args);
        return cacheContainers.computeIfAbsent(keyCache, uncheckedInvoke(method, args));
    }

    private Function<List<Object>, Object> uncheckedInvoke(Method method, Object[] args) {
        return k -> {
            try {
                return method.invoke(original, args);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }


    private List<Object> createKeyCache(final Method method, final Object[] args) {
        final List<Object> key = new ArrayList<>();
        key.add(method);
        key.addAll(Arrays.asList(args));
        return key;
    }

    private boolean hasProxyFrameTransactionalAnnotation(final Method method) throws NoSuchMethodException {
        return original.getClass().getMethod(method.getName(), method.getParameterTypes()).getAnnotation(ProxyFrameTransactional.class) != null;
    }

    private boolean hasProxyFrameCacheableAnnotation(final Method method) throws NoSuchMethodException {
        return original.getClass().getMethod(method.getName(), method.getParameterTypes()).getAnnotation(ProxyFrameCacheable.class) != null;
    }


}

package com.patres.proxyframework.framework.utils;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Unchecked {


    @FunctionalInterface
    public interface CheckedFunction<T, R> {
        R apply(T t);
    }


    public static <T, R> Function<T, R> function(CheckedFunction<T, R> checkedFunction) {
        return t -> {
            try {
                return checkedFunction.apply(t);
            } catch (Exception e) {
                return sneakyThrow(e);
            }
        };
    }
    public static <T> T sneakyThrow(Exception e) {
        throw new RuntimeException(e);
    }
}

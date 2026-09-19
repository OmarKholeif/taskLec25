package com.pioneers.functionalprogramming.utils;

@FunctionalInterface
public interface ToIntBiIntFunction<T> {
    T apply(T element1, T element2);
}

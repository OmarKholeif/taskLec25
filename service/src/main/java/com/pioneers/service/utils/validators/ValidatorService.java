package com.pioneers.service.utils.validators;

@FunctionalInterface
public interface ValidatorService<T> {

    void validate(T t);
}

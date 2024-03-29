package com.machineCoding.parkingLot.utils;

import com.machineCoding.parkingLot.exceptions.ValidationException;

import java.util.Objects;

public class ValidationUtil {
    public static void ensureTrue(Boolean bool, String message) {
        if(!bool)
            throw new ValidationException(message);
    }

    public static void ensureNotNull(Object o, String name) {
        if (Objects.isNull(o)) {
            throw new ValidationException(name + " cannot be null!");
        }
    }
}

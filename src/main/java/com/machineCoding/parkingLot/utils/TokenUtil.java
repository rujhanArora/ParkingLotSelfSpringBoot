package com.machineCoding.parkingLot.utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class TokenUtil {
    private static int DEFAULT_RANDOM_TOKEN_LENGTH  = 6;

    public static String generateRandomToken(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String generateRandomTokenDefaultLength() {
        return RandomStringUtils.randomAlphanumeric(DEFAULT_RANDOM_TOKEN_LENGTH);
    }
}

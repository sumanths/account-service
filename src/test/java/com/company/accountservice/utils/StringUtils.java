package com.company.accountservice.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class StringUtils {

    public static String randomString() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}

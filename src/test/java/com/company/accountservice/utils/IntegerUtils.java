package com.company.accountservice.utils;

import java.util.concurrent.ThreadLocalRandom;

public class IntegerUtils {

    public static Integer randomInteger() {
        return ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
    }
}

package com.hubert.ovhmailcreator.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Random;
import java.util.stream.IntStream;

public class Hashing {
    public static String randomString(Integer size) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < size; i++) {
            text.append((char) (random.nextInt(26) + (byte) 'a'));
        }

        return text.toString();
    }
}

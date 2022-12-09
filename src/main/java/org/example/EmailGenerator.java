package org.example;

import com.github.javafaker.Faker;

public final class EmailGenerator {
    public static String generate() {
        Faker faker = new Faker();
        return faker.internet().emailAddress();
    }
}

package com.lab9.utils;

import java.util.Locale;

import com.github.javafaker.Faker;

/**
 * Centralized random test data generator.
 */
public final class TestDataFactory {

    private static final Faker FAKER = new Faker(new Locale("en"));

    private TestDataFactory() {
    }

    public static String randomFirstName() {
        return normalizeText(FAKER.name().firstName(), "John");
    }

    public static String randomLastName() {
        return normalizeText(FAKER.name().lastName(), "Doe");
    }

    public static String randomPostalCode() {
        return FAKER.number().digits(5);
    }

    public static String randomEmail() {
        return FAKER.internet().emailAddress();
    }

    public static CheckoutData randomCheckoutData() {
        return new CheckoutData(randomFirstName(), randomLastName(), randomPostalCode());
    }

    private static String normalizeText(String rawValue, String fallback) {
        String normalized = rawValue.replaceAll("[^A-Za-z]", "");
        if (normalized.isBlank()) {
            return fallback;
        }
        return normalized;
    }

    public static final class CheckoutData {
        private final String firstName;
        private final String lastName;
        private final String postalCode;

        public CheckoutData(String firstName, String lastName, String postalCode) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.postalCode = postalCode;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getPostalCode() {
            return postalCode;
        }

        @Override
        public String toString() {
            return "CheckoutData{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
        }
    }
}

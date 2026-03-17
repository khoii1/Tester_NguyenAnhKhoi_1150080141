package com.lab9.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab9.models.UserData;

/**
 * Utility for reading JSON test data files.
 */
public final class JsonReader {

    private static final String DEFAULT_USERS_JSON = "testdata/users.json";

    private JsonReader() {
    }

    /**
     * Reads all login users from testdata/users.json.
     *
     * @return list of user test cases
     */
    public static List<UserData> readUsers() {
        return readUsers(DEFAULT_USERS_JSON);
    }

    /**
     * Reads all login users from a classpath JSON path.
     *
     * @param classpathLocation resource location in classpath
     * @return list of user test cases
     */
    public static List<UserData> readUsers(String classpathLocation) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream inputStream = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(classpathLocation)) {

            if (inputStream == null) {
                throw new IllegalArgumentException("Cannot find JSON resource: " + classpathLocation);
            }

            UserData[] users = mapper.readValue(inputStream, UserData[].class);
            return Arrays.asList(users);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to read users from JSON: " + classpathLocation, ex);
        }
    }
}

package com.lab9.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton configuration reader that loads environment-specific properties.
 *
 * <p>Credential fields (username/password) are read from <strong>environment variables
 * first</strong> (APP_USERNAME, APP_PASSWORD) so that real values never need to be
 * committed to the repository. When the env var is absent (local dev without .env),
 * the reader falls back to the properties file value.
 */
public final class ConfigReader {

    private static volatile ConfigReader instance;

    private final Properties properties;
    private final String env;

    private ConfigReader(String env) {
        this.env = env;
        this.properties = new Properties();
        loadConfig();
    }

    public static ConfigReader getInstance() {
        String activeEnv = System.getProperty("env", "dev").trim().toLowerCase();

        if (instance == null || !instance.env.equals(activeEnv)) {
            synchronized (ConfigReader.class) {
                if (instance == null || !instance.env.equals(activeEnv)) {
                    instance = new ConfigReader(activeEnv);
                }
            }
        }

        return instance;
    }

    public String getBaseUrl() {
        return getRequired("base.url");
    }

    public int getExplicitWait() {
        return Integer.parseInt(getRequired("explicit.wait"));
    }

    public int getRetryCount() {
        return Integer.parseInt(getRequired("retry.count"));
    }

    /**
     * Tự động đọc file .env thủ công (vì Java mặc định không tự đọc .env)
     */
    private String getFromDotEnv(String key) {
        java.io.File envFile = new java.io.File(".env");
        if (envFile.exists()) {
            try (java.util.Scanner scanner = new java.util.Scanner(envFile)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (line.startsWith(key + "=")) {
                        return line.substring(key.length() + 1).trim();
                    }
                }
            } catch (java.io.FileNotFoundException e) {
                // ignore
            }
        }
        return null;
    }

    /**
     * Returns the standard login username.
     * Priority: env var {@code APP_USERNAME} → .env file → properties file.
     */
    public String getStandardUsername() {
        String value = System.getenv("APP_USERNAME");
        if (value == null || value.isBlank()) value = getFromDotEnv("APP_USERNAME");
        if (value == null || value.isBlank()) value = properties.getProperty("login.standard.username", "");
        return value;
    }

    /**
     * Returns the standard login password.
     * Priority: env var {@code APP_PASSWORD} → .env file → properties file.
     */
    public String getStandardPassword() {
        String value = System.getenv("APP_PASSWORD");
        if (value == null || value.isBlank()) value = getFromDotEnv("APP_PASSWORD");
        if (value == null || value.isBlank()) value = properties.getProperty("login.standard.password", "");
        return value;
    }

    public String getLockedOutUsername() {
        return getRequired("login.locked.username");
    }

    public String getInvalidPassword() {
        return getRequired("login.invalid.password");
    }

    private void loadConfig() {
        String fileName = switch (env) {
            case "dev"     -> "config-dev.properties";
            case "staging" -> "config-staging.properties";
            default -> throw new IllegalArgumentException("Unsupported environment: " + env);
        };

        System.out.println("Dang dung moi truong: " + env);

        try (InputStream inputStream = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(fileName)) {

            if (inputStream == null) {
                throw new IllegalArgumentException("Cannot find config file: " + fileName);
            }

            properties.load(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load configuration from " + fileName, ex);
        }
    }

    private String getRequired(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required config key: " + key);
        }
        return value.trim();
    }
}

package com.lab9.dataproviders;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.lab9.models.UserData;
import com.lab9.utils.JsonReader;

/**
 * Data providers backed by users.json.
 */
public final class UserDataProvider {

    private UserDataProvider() {
    }

    @DataProvider(name = "jsonUsers")
    public static Object[][] jsonUsers() {
        List<UserData> users = JsonReader.readUsers();
        return users.stream()
            .map(user -> new Object[] { user })
            .toArray(Object[][]::new);
    }
}

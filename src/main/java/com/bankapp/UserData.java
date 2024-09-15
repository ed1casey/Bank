package com.bankapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserData {
    private static List<User> users = new ArrayList<>();
    private static final String DATA_FILE = "users.json";
    private static Gson gson = new Gson();

    public static void loadUsers() {
        try (Reader reader = new FileReader(DATA_FILE)) {
            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            users = gson.fromJson(reader, userListType);

            if (users == null) {
                users = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            users = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveUsers() {
        try (Writer writer = new FileWriter(DATA_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public static User findUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static boolean userExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}


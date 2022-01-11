package com.revature.dao;

import com.revature.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static ArrayList<User> users;

    public UserDAO(){
        users = new ArrayList<User>();

        users.add(new User());
        users.add(new User());
        users.add(new User());
    }

    public List<User> getAllUsers() {
        return users;
    }
}

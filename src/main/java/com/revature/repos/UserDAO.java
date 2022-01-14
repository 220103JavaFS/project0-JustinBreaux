package com.revature.repos;

import com.revature.models.Admin;
import com.revature.models.Customer;
import com.revature.models.User;

import java.util.ArrayList;

public class UserDAO {

    private static ArrayList<User> users;

    public UserDAO(){
        users = new ArrayList<User>();

        users.add(new Customer());
        users.add(new Customer(
                "Adam",
                "ADAWG",
                "adam@host.com",
                2
        ));
        users.add(new Admin());
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }
}

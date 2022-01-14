package com.revature.services;

import com.revature.repos.UserDAO;
import com.revature.models.User;

import java.util.ArrayList;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public ArrayList<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}

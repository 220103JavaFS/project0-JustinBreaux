package com.revature.services;

import com.revature.repos.UserDAO;
import com.revature.models.User;

import java.util.List;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}

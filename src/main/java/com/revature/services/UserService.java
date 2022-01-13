package com.revature.services;

import com.revature.dao.UserDAO;
import com.revature.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public ArrayList<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}

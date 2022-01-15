package com.revature.services;

import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;
import com.revature.models.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;

public class UserService {

    private UserDAO userDAO = new UserDAOImpl();

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    String pw = BCrypt.hashpw("PaSSworrd", BCrypt.gensalt());


}

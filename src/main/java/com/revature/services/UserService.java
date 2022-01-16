package com.revature.services;

import com.revature.repos.LoginDAO;
import com.revature.repos.LoginDAOImpl;
import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;
import com.revature.models.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;

public class UserService {

    private UserDAO userDAO = new UserDAOImpl();
    private LoginDAO loginDAO = new LoginDAOImpl();

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean setPassword(String userEmail, String userPassword){
        String hashedPW = BCrypt.hashpw(userPassword, BCrypt.gensalt());
        return userDAO.setPassword(userEmail, hashedPW);
    }

    public boolean login(String userEmail, String password){
         return BCrypt.checkpw(password, loginDAO.getPassword(userEmail));
    }


}

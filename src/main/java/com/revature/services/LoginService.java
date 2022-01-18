package com.revature.services;

import com.revature.repos.LoginDAO;
import com.revature.repos.LoginDAOImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class LoginService {
    LoginDAO loginDAO = new LoginDAOImpl();

    public boolean setPassword(String userEmail, String userPassword){
        String hashedPW = BCrypt.hashpw(userPassword, BCrypt.gensalt());
        return loginDAO.setPassword(userEmail, hashedPW);
    }

    public boolean login(String userEmail, String password){
        return BCrypt.checkpw(password, loginDAO.getPassword(userEmail));
    }
}

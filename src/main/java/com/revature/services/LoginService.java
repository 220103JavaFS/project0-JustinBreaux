package com.revature.services;

import com.revature.models.Admin;
import com.revature.models.Player;
import com.revature.repos.LoginDAO;
import com.revature.repos.LoginDAOImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class LoginService {
    private LoginDAO loginDAO = new LoginDAOImpl();

    public boolean setPassword(String userEmail, String userPassword){
        String hashedPW = BCrypt.hashpw(userPassword, BCrypt.gensalt());
        return loginDAO.setPassword(userEmail, hashedPW);
    }

    public Player getPlayerByEmail(String email){
        return loginDAO.getPlayerByEmail(email);
    }

    public Admin getAdminByEmail(String email){
        return loginDAO.getAdminByEmail(email);
    }

    public boolean getAdminStatus(String email){
        return loginDAO.checkAdminStatus(email);
    }

    public boolean login(String userEmail, String password){
        return BCrypt.checkpw(password, loginDAO.getPassword(userEmail));
    }
}

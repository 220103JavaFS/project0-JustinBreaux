package com.revature.services;

import com.revature.models.Admin;
import com.revature.models.Player;
import com.revature.repos.LoginDAO;
import com.revature.repos.LoginDAOImpl;
import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;

public class UserService {

    private UserDAO userDAO = new UserDAOImpl();
    private LoginDAO loginDAO = new LoginDAOImpl();

    public List<Player> getAllPlayers() {
        return userDAO.getAllPlayers();
    }

    public Player getPlayerByUsername(String username){
        return userDAO.getPlayerByUsername(username);
    }

    public Player getPlayerByEmail(String email){
        return userDAO.getPlayerByEmail(email);
    }

    public Admin getAdminByEmail(String email){
        return userDAO.getAdminByEmail(email);
    }

    public boolean getAdminStatus(String email){
        return userDAO.checkAdminStatus(email);
    }


}

package com.revature.services;

import com.revature.models.Player;
import com.revature.repos.LoginDAO;
import com.revature.repos.LoginDAOImpl;
import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;
import com.revature.models.User;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;

public class UserService {

    private UserDAO userDAO = new UserDAOImpl();
    private LoginDAO loginDAO = new LoginDAOImpl();

    public List<Player> getAllPlayers() {
        return userDAO.getAllPlayers();
    }

    public Player getPlayer(String username){
        return userDAO.getPlayer(username);
    }

    public boolean setPassword(String userEmail, String userPassword){
        String hashedPW = BCrypt.hashpw(userPassword, BCrypt.gensalt());
        return userDAO.setPassword(userEmail, hashedPW);
    }

    public String login(String userEmail, String password){
         if(BCrypt.checkpw(password, loginDAO.getPassword(userEmail))){
             if(userDAO.checkAdminStatus(userEmail)){
                 return "admin";
             }else{
                 return "player";
             }
         }else{
             return "failed";
         }
    }


}

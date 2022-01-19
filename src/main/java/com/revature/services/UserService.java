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

    public List<Player> getAllPlayers() {
        return userDAO.getAllPlayers();
    }

    public Player getPlayerByUsername(String username){
        return userDAO.getPlayerByUsername(username);
    }

    public boolean deletePlayer(Player player){
        return userDAO.deletePlayer(player);
    }


}

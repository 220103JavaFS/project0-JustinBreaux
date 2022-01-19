package com.revature.services;

import com.revature.models.Player;
import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;

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
        return userDAO.deletePlayer(player.getEmail());
    }

    public boolean addTokens(Player player, int tokens){
        return userDAO.addTokens(player.getEmail(), tokens);
    }


}

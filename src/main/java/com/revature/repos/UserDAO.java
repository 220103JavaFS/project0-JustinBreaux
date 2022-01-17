package com.revature.repos;

import com.revature.models.Player;

import java.util.List;

public interface UserDAO {
    public List<Player> getAllPlayers();
    public Player getPlayer(String username);
    public boolean setPassword(String userEmail, String userPassword);
    public boolean checkAdminStatus(String userEmail);
}

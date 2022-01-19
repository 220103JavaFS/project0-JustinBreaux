package com.revature.repos;

import com.revature.models.Admin;
import com.revature.models.Player;

import java.util.List;

public interface UserDAO {
    public List<Player> getAllPlayers();
    public Player getPlayerByUsername(String username);
    public boolean deletePlayer(Player player);
}

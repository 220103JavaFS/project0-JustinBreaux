package com.revature.repos;

import com.revature.models.Admin;
import com.revature.models.Player;

import java.util.List;

public interface UserDAO {
    public List<Player> getAllPlayers();
    public Player getPlayerByUsername(String username);
    public Player getPlayerByEmail(String email);
    public Admin getAdminByEmail(String email);
    public boolean checkAdminStatus(String userEmail);
    public boolean deletePlayer(Player player);
}

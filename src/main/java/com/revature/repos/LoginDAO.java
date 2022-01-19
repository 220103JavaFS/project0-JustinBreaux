package com.revature.repos;

import com.revature.models.Admin;
import com.revature.models.Player;

public interface LoginDAO {

    String getPassword(String email);
    public Player getPlayerByEmail(String email);
    public Admin getAdminByEmail(String email);
    public boolean checkAdminStatus(String userEmail);
    public boolean setPassword(String userEmail, String userPassword);
}

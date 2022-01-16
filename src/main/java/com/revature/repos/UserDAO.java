package com.revature.repos;

import com.revature.models.User;

import java.util.List;

public interface UserDAO {
    public List<User> getAllUsers();
    public boolean setPassword(String userEmail, String userPassword);
    public boolean checkAdminStatus(String userEmail);
}

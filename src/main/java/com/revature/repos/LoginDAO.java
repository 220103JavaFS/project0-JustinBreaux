package com.revature.repos;

public interface LoginDAO {

    String getPassword(String email);
    public boolean setPassword(String userEmail, String userPassword);
}

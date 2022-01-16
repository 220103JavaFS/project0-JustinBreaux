package com.revature.repos;

public interface LoginDAO {

    String getPassword(String username);
    boolean login(String username, String password);
}

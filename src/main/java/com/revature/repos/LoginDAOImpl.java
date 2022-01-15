package com.revature.repos;

import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginDAOImpl implements LoginDAO{
    @Override
    public String getPassword(String username) {
        try(Connection conn = ConnectionUtil.getConnection()){

        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}

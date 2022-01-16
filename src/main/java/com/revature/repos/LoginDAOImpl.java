package com.revature.repos;

import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOImpl implements LoginDAO{
    @Override
    public String getPassword(String userEmail) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT user_password FROM logins WHERE email = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, userEmail);

            ResultSet result = statement.executeQuery();

            if(result.next()){
                return result.getString("user_password");
            }else{
                return null;
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }
}

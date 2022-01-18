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
            String sql = "SELECT user_password FROM logins WHERE user_email = ?;";

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
    public boolean setPassword(String userEmail, String userPassword) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE logins SET user_email = ?, user_password = ? WHERE user_email = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, userEmail);
            statement.setString(2, userPassword);
            statement.setString(3, userEmail);

            statement.execute();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}

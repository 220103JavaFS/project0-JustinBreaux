package com.revature.repos;

import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO{

    public List<User> getAllUsers() {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM users;";

            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(sql);

            List<User> list = new ArrayList<>();

            while(result.next()){
                User user = new User();
                user.setUserId(result.getInt("user_id"));
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));
                user.setAdminStatus(result.getBoolean("admin_status"));

                list.add(user);
            }

            return list;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return new ArrayList<User>();
    }

    @Override
    public boolean setPassword(String userEmail, String userPassword) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO logins(email, user_password) VALUES (?, ?);";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, userEmail);
            statement.setString(2, userPassword);

            statement.execute();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}

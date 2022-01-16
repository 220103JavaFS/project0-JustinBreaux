package com.revature.repos;

import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO{

    public List<User> getAllUsers() {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM players UNION SELECT * FROM admins;";

            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(sql);

            List<User> list = new ArrayList<>();

            while(result.next()){
                User user = new User();
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));

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
            String sql = "INSERT INTO logins(user_email, user_password) VALUES (?, ?);";

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

    @Override
    public boolean checkAdminStatus(String userEmail) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT admin_email FROM admins WHERE admin_email = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userEmail);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                if(result.wasNull()){
                    return false;
                }else{
                    return true;
                }
            }else{
                return false;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}

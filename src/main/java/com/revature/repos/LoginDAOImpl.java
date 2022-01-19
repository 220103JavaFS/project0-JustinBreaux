package com.revature.repos;

import com.revature.models.Admin;
import com.revature.models.Player;
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
    public Player getPlayerByEmail(String email) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM players WHERE player_email = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            Player player = new Player();
            if(result.next()){
                player.setEmail(result.getString("player_email"));
                player.setFirstName(result.getString("player_first_name"));
                player.setLastName(result.getString("player_last_name"));
                player.setUsername(result.getString("player_name"));
                player.setTokenBalance(result.getInt("token_balance"));
                player.setTicketBalance(result.getInt("ticket_balance"));
                player.setTier(result.getInt("tier"));
            }else{
                return null;
            }
            return player;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Admin getAdminByEmail(String email) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM admins WHERE admin_email = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            Admin admin = new Admin();
            if(result.next()){
                admin.setEmail(result.getString("admin_email"));
                admin.setFirstName(result.getString("admin_first_name"));
                admin.setLastName(result.getString("admin_last_name"));
            }else{
                return null;
            }
            return admin;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean checkAdminStatus(String userEmail) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT admin_email FROM admins WHERE admin_email = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userEmail);
            ResultSet result = statement.executeQuery();
            return result.next();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
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

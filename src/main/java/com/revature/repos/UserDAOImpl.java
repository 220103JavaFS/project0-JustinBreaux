package com.revature.repos;

import com.revature.models.Admin;
import com.revature.models.Player;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO{

    public List<Player> getAllPlayers() {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM players;";

            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(sql);

            List<Player> list = new ArrayList<>();

            while(result.next()){
                Player player = new Player();
                player.setEmail(result.getString("player_email"));
                player.setFirstName(result.getString("player_first_name"));
                player.setLastName(result.getString("player_last_name"));
                player.setUsername(result.getString("player_name"));
                player.setTokenBalance(result.getInt("token_balance"));
                player.setTicketBalance(result.getInt("ticket_balance"));
                player.setTier(result.getInt("tier"));

                list.add(player);
            }

            return list;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return new ArrayList<Player>();
    }

    @Override
    public Player getPlayerByUsername(String username) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM players WHERE player_name = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
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
            if(result.next()){
                return true;
            }else{
                return false;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deletePlayer(Player player) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "DELETE FROM players WHERE player_email = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, player.getEmail());
            statement.execute();

            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}

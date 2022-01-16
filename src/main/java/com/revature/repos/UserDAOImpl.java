package com.revature.repos;

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
    public Player getPlayer(String username) {
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
            }
            return player;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return new Player();
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

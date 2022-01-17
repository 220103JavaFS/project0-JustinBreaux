package com.revature.repos;

import com.revature.models.Game;
import com.revature.models.Player;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAOImpl implements GameDAO{

    @Override
    public List<Game> getAllGames() {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM games;";

            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(sql);

            List<Game> list = new ArrayList<>();

            while(result.next()){
                Game game = new Game();
                game.setTitle(result.getString("title"));
                game.setCost(result.getInt("token_cost"));
                game.setPointMin(result.getInt("point_min"));
                game.setPointMax(result.getInt("point_max"));
                game.setTicketMultiplier(result.getDouble("ticket_to_point_ratio"));

                list.add(game);
            }

            return list;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return new ArrayList<Game>();
    }

    @Override
    public Game getGameByTitle(String title) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM games WHERE title = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, title);
            ResultSet result = statement.executeQuery();
            Game game = new Game();
            if(result.next()){
                game.setTitle(result.getString("title"));
                game.setCost(result.getInt("token_cost"));
                game.setPointMin(result.getInt("point_min"));
                game.setPointMax(result.getInt("point_max"));
                game.setTicketMultiplier(result.getDouble("ticket_to_point_ratio"));
            }else{
                return null;
            }
            return game;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}

package com.revature.repos;

import com.revature.models.Player;
import com.revature.models.Record;
import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecordDAOImpl implements RecordDAO{
    UserDAO userDAO = new UserDAOImpl();
    GameDAO gameDAO = new GameDAOImpl();

    @Override
    public List<Record> getScoresByPlayer(String username) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM records WHERE player = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            List<Record> list = new ArrayList<>();
            while(result.next()){
                Record record = new Record();
                record.setTime(result.getTime("record_time"));
                record.setScore(result.getInt("score"));
                record.setMachineNum(result.getInt("machine"));
                record.setPlayer(userDAO.getPlayer(result.getString("player")));
                record.setGame(gameDAO.getGameByMachineNum(result.getInt("machine")));
                list.add(record);
            }
            return list;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Record> getScoresByGame(String title) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM records " +
                    "WHERE machine IN (SELECT machine_number FROM machines WHERE game_title = ?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, title);
            ResultSet result = statement.executeQuery();
            List<Record> list = new ArrayList<>();
            while(result.next()){
                Record record = new Record();
                record.setTime(result.getTime("record_time"));
                record.setScore(result.getInt("score"));
                record.setMachineNum(result.getInt("machine"));
                record.setPlayer(userDAO.getPlayer(result.getString("player")));
                record.setGame(gameDAO.getGameByMachineNum(result.getInt("machine")));
                list.add(record);
            }
            return list;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}

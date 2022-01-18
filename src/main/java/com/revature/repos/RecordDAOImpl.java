package com.revature.repos;

import com.revature.models.Record;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.time.LocalDateTime;
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
                record.setTime(result.getTimestamp("record_time"));
                record.setScore(result.getInt("score"));
                record.setMachineNum(result.getInt("machine"));
                record.setPlayer(userDAO.getPlayerByUsername(result.getString("player")));
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
                record.setTime(result.getTimestamp("record_time"));
                record.setScore(result.getInt("score"));
                record.setMachineNum(result.getInt("machine"));
                record.setPlayer(userDAO.getPlayerByUsername(result.getString("player")));
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
    public boolean createNewRecord(Timestamp time, String username, int machineNum) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO records (record_time, player, machine) " +
                    "VALUES (?, ?, "+machineNum+");";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setTimestamp(1, time);
            statement.setString(2, username);
            statement.execute();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getScore(Timestamp time, String username) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT score FROM records WHERE (record_time, player) = (?, ?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setTimestamp(1, time);
            statement.setString(2, username);

            ResultSet result = statement.executeQuery();

            if(result.next()){
                return result.getInt("score");
            }else{
                return 0;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}

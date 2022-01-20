package com.revature.repos;

import com.revature.models.Record;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
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
            return (statement.executeUpdate() > 0);

           // return true;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Record getRecord(Timestamp time, String username) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM records WHERE (record_time, player) = (?, ?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setTimestamp(1, time);
            statement.setString(2, username);

            ResultSet result = statement.executeQuery();

            if(result.next()){
                return new Record(time,
                        userDAO.getPlayerByUsername(username),
                        result.getInt("score"),
                        result.getInt("machine"),
                        gameDAO.getGameByMachineNum(result.getInt("machine")));
            }else{
                return new Record();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteRecord(Record record) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "DELETE FROM records WHERE (record_time, player) = (?, ?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setTimestamp(1, record.getTime());
            statement.setString(2, record.getPlayer().getUsername());
            statement.execute();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Record> getHighScoresByGame(String title) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM records WHERE machine IN " +
                    "(SELECT machine_number FROM machine_expanded WHERE title = ?) " +
                    "ORDER BY score DESC, record_time DESC LIMIT 5;";
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
    public List<Record> getHighScoresByMachine(int num) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM records WHERE machine = "+num+
                    " ORDER BY score DESC, record_time DESC LIMIT 5;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

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
}

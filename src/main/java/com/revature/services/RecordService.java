package com.revature.services;

import com.revature.models.Player;
import com.revature.models.Record;
import com.revature.repos.GameDAO;
import com.revature.repos.GameDAOImpl;
import com.revature.repos.RecordDAO;
import com.revature.repos.RecordDAOImpl;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class RecordService {
    RecordDAO recordDAO = new RecordDAOImpl();
    GameDAO gameDAO = new GameDAOImpl();

    public List<Record> getScoresByPlayer(String username){
        return recordDAO.getScoresByPlayer(username);
    }

    public List<Record> getScoresByGame(String title){
        return recordDAO.getScoresByGame(title);
    }

    public Record playGame(Player player, int machineNumber){
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());

        if(recordDAO.createNewRecord(time, player.getUsername(), machineNumber)){
            Record record = new Record();
            record.setTime(time);
            record.setPlayer(player);
            record.setMachineNum(machineNumber);
            record.setGame(gameDAO.getGameByMachineNum(machineNumber));
            record.setScore(recordDAO.getScore(time, player.getUsername()));
            return record;
        }else{
            return new Record();
        }
    }
}

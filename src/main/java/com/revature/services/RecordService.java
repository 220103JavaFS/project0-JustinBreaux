package com.revature.services;

import com.revature.models.Player;
import com.revature.models.Record;
import com.revature.repos.GameDAO;
import com.revature.repos.GameDAOImpl;
import com.revature.repos.RecordDAO;
import com.revature.repos.RecordDAOImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class RecordService {
    private RecordDAO recordDAO = new RecordDAOImpl();
    private GameDAO gameDAO = new GameDAOImpl();

    public List<Record> getScoresByPlayer(String username){
        return recordDAO.getScoresByPlayer(username);
    }

    public List<Record> getScoresByGame(String title){
        return recordDAO.getScoresByGame(title);
    }

    public Record playGame(Player player, int machineNumber){
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());

        if(recordDAO.createNewRecord(time, player.getUsername(), machineNumber)){
            return recordDAO.getRecord(time, player.getUsername());
        }else{
            return null;
        }
    }

    public boolean deleteRecord(Record record){
        return recordDAO.deleteRecord(record);
    }

    public List<Record> getHighScoresByGame(String title){
        return recordDAO.getHighScoresByGame(title);
    }

    public List<Record> getHighScoresByMachine(int num){
        return recordDAO.getHighScoresByMachine(num);
    }
}

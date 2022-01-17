package com.revature.services;

import com.revature.models.Record;
import com.revature.repos.RecordDAO;
import com.revature.repos.RecordDAOImpl;

import java.util.List;

public class RecordService {
    RecordDAO recordDAO = new RecordDAOImpl();

    public List<Record> getScoresByPlayer(String username){
        return recordDAO.getScoresByPlayer(username);
    }

    public List<Record> getScoresByGame(String title){
        return recordDAO.getScoresByGame(title);
    }
}

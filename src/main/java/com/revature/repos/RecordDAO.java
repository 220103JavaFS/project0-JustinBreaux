package com.revature.repos;

import com.revature.models.Record;

import java.sql.Timestamp;
import java.util.List;

public interface RecordDAO {
    public List<Record> getScoresByPlayer(String username);
    public List<Record> getScoresByGame(String title);
    public boolean createNewRecord(Timestamp time, String username, int machineNum);
    public Record getRecord(Timestamp time, String username);
    public boolean deleteRecord(Record record);
    public List<Record> getHighScoresByGame(String title);
    public List<Record> getHighScoresByMachine(int num);
}

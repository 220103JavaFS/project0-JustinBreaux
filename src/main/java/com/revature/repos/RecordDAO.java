package com.revature.repos;

import com.revature.models.Record;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface RecordDAO {
    public List<Record> getScoresByPlayer(String username);
    public List<Record> getScoresByGame(String title);
    public boolean createNewRecord(Timestamp time, String username, int machineNum);
    public int getScore(Timestamp time, String username);
}

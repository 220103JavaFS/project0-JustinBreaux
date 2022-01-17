package com.revature.repos;

import com.revature.models.Record;

import java.util.List;

public interface RecordDAO {
    public List<Record> getScoresByPlayer(String username);
    public List<Record> getScoresByGame(String title);
}

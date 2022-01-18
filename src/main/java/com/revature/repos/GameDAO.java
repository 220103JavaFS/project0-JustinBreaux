package com.revature.repos;

import com.revature.models.Game;

import java.util.List;

public interface GameDAO {

    public List<Game> getAllGames();
    public Game getGameByTitle(String title);
    public Game getGameByMachineNum(int num);
    public boolean updateGame(Game game);
}

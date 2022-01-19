package com.revature.services;

import com.revature.repos.GameDAOImpl;
import com.revature.models.Game;

import java.util.List;

public class GameService {

    private GameDAOImpl gameDAO = new GameDAOImpl();

    public List<Game> getAllGames(){
        return gameDAO.getAllGames();
    }

    public Game getGameByTitle(String title){
        return gameDAO.getGameByTitle(title);
    }

    public boolean changeGame(Game game){
        return gameDAO.updateGame(game);
    }
}

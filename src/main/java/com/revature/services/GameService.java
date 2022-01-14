package com.revature.services;

import com.revature.repos.GameDAO;
import com.revature.models.Game;

import java.util.List;

public class GameService {

    GameDAO gameDAO = new GameDAO();

    public List<Game> getAllGames(){
        return gameDAO.getAllGames();
    }

    public Game getGameByName(String name){
        return gameDAO.getGameByName(name);
    }
}

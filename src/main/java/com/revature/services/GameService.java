package com.revature.services;

import com.revature.repos.GameDAOImpl;
import com.revature.models.Game;

import java.util.List;

public class GameService {

    GameDAOImpl gameDAO = new GameDAOImpl();

    public List<Game> getAllGames(){
        return gameDAO.getAllGames();
    }

    public Game getGameByName(String name){
        return gameDAO.getGameByName(name);
    }
}

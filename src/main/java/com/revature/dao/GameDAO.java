package com.revature.dao;

import com.revature.models.Game;
import com.revature.models.User;

import java.util.ArrayList;
import java.util.List;

public class GameDAO {
    private static ArrayList<Game> games;

    public GameDAO(){
        games = new ArrayList<Game>();

        games.add(new Game(0, 100));
        games.add(new Game(
                "pinball",
                4,
                0,
                99999,
                1
        ));
    }

    public List<Game> getAllGames() {
        return games;
    }

    public Game getGameByName(String name){

//        for(Game game:games){
//            if(game.getName().equals(name)){
//                return game;
//            }
//        }
//        return null;

        return games.stream()
                .filter(game->name.equals(game.getName()))
                .findAny()
                .orElse(null);

    }
}

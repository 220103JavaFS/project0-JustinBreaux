package com.revature.controllers;

import com.revature.models.Game;
import com.revature.services.GameService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class GameController {

    private GameService gameService = new GameService();

    private Handler getAllGames = (ctx)->{
        List<Game> list = gameService.getAllGames();
        ctx.json(list);
        ctx.status(200);
    };

    private Handler getGameByName = (ctx)->{
        Game game = gameService.getGameByName(ctx.pathParam("name"));

        if(game != null){
            ctx.json(game);
            ctx.status(200);
        }else{
            ctx.html("<h1> No such game exists!! </h1>");
            ctx.status(404);
        }

    };

    public void addRoutes(Javalin app) {
        app.get("/Game", getAllGames);
        app.get("/Game/{name}", getGameByName);
    }
}

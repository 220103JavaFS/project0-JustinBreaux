package com.revature.controllers;

import com.revature.models.Game;
import com.revature.services.GameService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class GameController implements Controller{

    private GameService gameService = new GameService();

    private Handler getAllGames = (ctx)->{
        List<Game> list = gameService.getAllGames();
        if(list.isEmpty()){
            ctx.html("<h1> There are no games :( </h1>");
            ctx.status(404);
        }else{
            ctx.json(list);
            ctx.status(200);
        }

    };

    private Handler getGameByTitle = (ctx)->{
        Game game = gameService.getGameByTitle(ctx.pathParam("title"));
        if(game != null){
            ctx.json(game);
            ctx.status(200);
        }else{
            ctx.html("<h1> No such game exists!! </h1>");
            ctx.status(404);
        }
    };

    @Override
    public void addRoutes(Javalin app) {
        app.get("/games", getAllGames);
        app.get("/games/{title}", getGameByTitle);
    }
}

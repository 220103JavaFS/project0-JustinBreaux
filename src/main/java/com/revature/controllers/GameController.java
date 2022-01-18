package com.revature.controllers;

import com.revature.models.Admin;
import com.revature.models.Game;
import com.revature.services.GameService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;
import java.util.Objects;

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

    private Handler changeGame = (ctx)->{
        if (ctx.req.getSession(false)!=null &&
                ctx.req.getSession().getAttribute("userInfo").getClass().equals(Admin.class)) {
            if (gameService.changeGame(ctx.bodyAsClass(Game.class))) {
                ctx.status(202); //accepted
            } else {
                ctx.status(400);
            }
        } else {
            ctx.status(401);
        }

    };

    @Override
    public void addRoutes(Javalin app) {
        app.get("/game", getAllGames);
        app.get("/game/{title}", getGameByTitle);
        app.put("/game", changeGame);
    }
}

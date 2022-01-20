package com.revature.controllers;

import com.revature.models.Admin;
import com.revature.models.Game;
import com.revature.services.GameService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GameController implements Controller{

    private static Logger log = LoggerFactory.getLogger(GameController.class);
    private GameService gameService = new GameService();

    private Handler getAllGames = (ctx)->{
        log.info("someone requested a list of all games");
        List<Game> list = gameService.getAllGames();
        if(list.isEmpty()){
            ctx.status(404);
            log.info("request for a list of all games failed");
        }else{
            ctx.json(list);
            ctx.status(200);
            log.info("request for a list of all games was successful");
        }

    };

    private Handler getGameByTitle = (ctx)->{
        String title = ctx.pathParam("title");

        log.info("someone requested game info about "+title);

        Game game = gameService.getGameByTitle(title);

        if(game != null){
            ctx.json(game);
            ctx.status(200);
            log.info("request for info about "+title+" was successful");
        }else{
            ctx.status(404);
            log.info("request for info about "+title+" failed");
        }
    };

    private Handler changeGame = (ctx)->{
        if (ctx.req.getSession(false)!=null){
            Admin user = (Admin) ctx.req.getSession().getAttribute("userInfo");
            Game game = ctx.bodyAsClass(Game.class);

            log.info(user.getEmail()+" requested to update game info for "+game.getTitle());

            if(user.getClass().equals(Admin.class)){
                if (gameService.changeGame(game)) {
                    ctx.status(202); //accepted
                    log.info("request from "+user.getEmail()+" to update info for "+game.getTitle()+" was successful");
                    log.info("old game info: "+game.toString());
                    log.info("new game info: "+gameService.getGameByTitle(game.getTitle()).toString());
                } else {
                    ctx.status(400);
                    log.info("request from "+user.getEmail()+" to update info for "+game.getTitle()+" failed");
                }
            } else{
                ctx.status(403);
                log.info("request from "+user.getEmail()+" was denied");
            }
        } else {
            ctx.status(401);
            log.info("access to update game info denied");
        }

    };

    @Override
    public void addRoutes(Javalin app) {
        app.get("/game", getAllGames);
        app.get("/game/{title}", getGameByTitle);
        app.put("/game", changeGame);
    }
}

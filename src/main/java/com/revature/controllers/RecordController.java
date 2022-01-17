package com.revature.controllers;

import com.revature.models.Player;
import com.revature.models.Record;
import com.revature.services.RecordService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class RecordController implements Controller{
    RecordService recordService = new RecordService();

    private Handler getPlayerScores = (ctx)->{
        if(ctx.req.getSession(false)!=null){
            String username = ctx.pathParam("username");
            List<Record> list = recordService.getScoresByPlayer(username);
            if(list.isEmpty()){
                ctx.html("<h1> No records exist for a player of that name </h1>");
                ctx.status(404);
            }else{
                ctx.json(list);
                ctx.status(201);
            }
        }else {
            ctx.status(401);
        }
    };

    private Handler getGameScores = (ctx)->{
        if(ctx.req.getSession(false)!=null){
            String title = ctx.pathParam("title");
            List<Record> list = recordService.getScoresByGame(title);
            if(list.isEmpty()){
                ctx.html("<h1> No records exist for a game of that name </h1>");
                ctx.status(404);
            }else{
                ctx.json(recordService.getScoresByGame(ctx.pathParam("title")));
                ctx.status(200);
            }
        }else {
            ctx.status(401);
        }
    };

    private Handler playGame = (ctx)->{
        if(ctx.req.getSession().getAttribute("userType").equals("player")){
            Player player = (Player) ctx.req.getSession().getAttribute("userInfo");
            int machineNum = Integer.parseInt(ctx.body());
            ctx.json(recordService.playGame(player, machineNum));
            ctx.status(200);
        }else {
            ctx.status(401);
        }
    };

    @Override
    public void addRoutes(Javalin app) {
        app.get("/players/{username}/scores", getPlayerScores);
        app.get("/games/{title}/scores", getGameScores);
        app.post("/play", playGame);
    }
}
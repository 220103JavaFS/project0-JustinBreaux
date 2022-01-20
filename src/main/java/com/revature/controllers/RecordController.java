package com.revature.controllers;

import com.revature.models.Admin;
import com.revature.models.Player;
import com.revature.models.Record;
import com.revature.services.RecordService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RecordController implements Controller {
    private static Logger log = LoggerFactory.getLogger(RecordController.class);
    private RecordService recordService = new RecordService();

    private Handler getPlayerScores = (ctx) -> {
        if (ctx.req.getSession(false) != null) {
            Admin user = (Admin) ctx.req.getSession().getAttribute("userInfo");
            String username = ctx.pathParam("username");

            log.info(user.getEmail()+" requested records of player named "+username);

            List<Record> list = recordService.getScoresByPlayer(username);
            if (list.isEmpty()) {
                ctx.status(404);
                log.info("request from "+user.getEmail()+" for records of "+username+" returned no results");
            } else {
                ctx.json(list);
                ctx.status(200);
                log.info("request from "+user.getEmail()+" for records of "+username+" was successful");
            }
        } else {
            ctx.status(401);
            log.info("access to player records denied");
        }
    };

    private Handler getGameScores = (ctx) -> {
        if (ctx.req.getSession(false) != null) {
            Admin user = (Admin) ctx.req.getSession().getAttribute("userInfo");
            String title = ctx.pathParam("title");

            log.info(user.getEmail()+" requested records of game titled "+title);

            List<Record> list = recordService.getScoresByGame(title);
            if (list.isEmpty()) {
                ctx.status(404);
                log.info("request from "+user.getEmail()+" for records of "+title+" returned no results");
            } else {
                ctx.json(list);
                ctx.status(200);
                log.info("request from "+user.getEmail()+" for records of "+title+" was successful");
            }
        } else {
            ctx.status(401);
            log.info("access to game records denied");
        }
    };

    private Handler playGame = (ctx) -> {
        if (ctx.req.getSession(false) != null){
            Admin user = (Admin) ctx.req.getSession().getAttribute("userInfo");
            int machineNum = Integer.parseInt(ctx.body());

            log.info(user.getEmail()+" requested to play machine number "+machineNum);

            if(user.getClass().equals(Player.class)) {
                Record record = recordService.playGame((Player) user, machineNum);
                if(record != null){
                    ctx.json(record);
                    ctx.status(201);
                    log.info("request from "+user.getEmail()+" to play machine number "+machineNum+" was successful");
                    log.info("new record: "+record.toString());
                }else{
                    ctx.status(400);
                    log.info("request from "+user.getEmail()+" to play "+machineNum+" failed");
                }
            }else{
                ctx.status(403);
                log.info("request from "+user.getEmail()+" to play games was forbidden");
            }
        } else {
            ctx.status(401);
            log.info("access to play game denied");
        }
    };

    private Handler deleteRecord = (ctx) -> {
        if (ctx.req.getSession(false) != null){
            Admin user = (Admin) ctx.req.getSession().getAttribute("userInfo");
            Record record = ctx.bodyAsClass(Record.class);

            log.info(user.getEmail()+" requested to delete record: "+record.toString());

            if(user.getClass().equals(Admin.class)) {
                if(recordService.deleteRecord(record)){
                    ctx.status(202);
                    log.info("request from "+user.getEmail()+" to delete record was successful");
                    log.info("deleted record: "+record.toString());
                }else{
                    ctx.status(404);
                    log.info("request from "+user.getEmail()+" to delete record failed");
                }
            }else{
                ctx.status(403);
                log.info("request from "+user.getEmail()+" to delete record was forbidden");
            }
        }else {
            ctx.status(401);
            log.info("access to delete record denied");
        }
    };

    private Handler getHighScoresByGame = (ctx)->{
        if (ctx.req.getSession(false) != null){
            Admin user = (Admin) ctx.req.getSession().getAttribute("userInfo");
            String title = ctx.pathParam("title");

            log.info(user.getEmail()+" requested high scores for game titled "+title);

            if(user.getClass().equals(Admin.class) || ((Player) user).getTier() > 1){
                List<Record> list = recordService.getHighScoresByGame(title);

                if(list.isEmpty()){
                    ctx.status(404);
                    log.info("request from "+user.getEmail()+" for high scores of "+title+" failed");
                }else{
                    ctx.json(list);
                    ctx.status(200);
                    log.info("request from "+user.getEmail()+" for high scores of "+title+" was successful");
                }
            }else{
                ctx.status(403);
                log.info("request from "+user.getEmail()+" for high scores of "+title+" was forbidden");
            }
        }else {
            ctx.status(401);
            log.info("access to game's high scores denied");
        }
    };

    private Handler getHighScoresByMachine = (ctx)->{
        if (ctx.req.getSession(false) != null){
            Admin user = (Admin) ctx.req.getSession().getAttribute("userInfo");
            int machineNum = Integer.parseInt(ctx.pathParam("id"));

            log.info(user.getEmail()+" requested high scores for machine number "+machineNum);

            if(user.getClass().equals(Admin.class) || ((Player) user).getTier() > 1){
                List<Record> list = recordService.getHighScoresByMachine(machineNum);

                if(list.isEmpty()){
                    ctx.status(404);
                    log.info("request from "+user.getEmail()+" for high scores of machine number "+machineNum+" failed");
                }else{
                    ctx.json(list);
                    ctx.status(200);
                    log.info("request from "+user.getEmail()+" for high scores of machine number "+machineNum+" was successful");
                }
            }else{
                ctx.status(403);
                log.info("request from "+user.getEmail()+" for high scores of machine number "+machineNum+" was forbidden");
            }
        }else {
            ctx.status(401);
            log.info("access to machine's high scores denied");
        }
    };

    @Override
    public void addRoutes(Javalin app) {
        app.get("/player/{username}/records", getPlayerScores); //returns list of a player's records
        app.get("/machine/{id}/highscores", getHighScoresByMachine);
        app.get("/game/{title}/records", getGameScores); //returns list of a game's records on all machines
        app.get("/game/{title}/highscores", getHighScoresByGame);
        app.post("/record", playGame); //creates a record and processes transaction
        app.delete("/record", deleteRecord);
    }
}


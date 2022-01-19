package com.revature.controllers;

import com.revature.models.Admin;
import com.revature.models.Player;
import com.revature.models.Record;
import com.revature.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController implements Controller{

    private UserService userService = new UserService();

    private Handler getAllPlayers = (ctx)->{
        if(ctx.req.getSession(false)!=null){
            ctx.json(userService.getAllPlayers());
            ctx.status(200);
        }else {
            ctx.status(401);
        }

    };

    private Handler getPlayer = (ctx)->{
        if(ctx.req.getSession(false)!=null){ //check if logged in
            //Create new player object, get player by username from service layer with username provided by path parameter
            Player player = userService.getPlayerByUsername(ctx.pathParam("username"));
            if(player != null){
                ctx.json(player); //returns complete player info
                ctx.status(200); //OK
            }else{
                ctx.html("<h1> No Player by that name exists! </h1>");
                ctx.status(404); //not found
            }

        }else {
            ctx.status(401); //unauthorized
        }
    };

    private Handler gameOver = (ctx) -> {
        if (ctx.req.getSession(false) != null &&
                ctx.req.getSession().getAttribute("userInfo").getClass().equals(Admin.class)) {
            if(userService.deletePlayer(ctx.bodyAsClass(Player.class))){
                ctx.status(202);
            }else{
                ctx.status(404);
            }
        }else {
            ctx.status(401);
        }

    };


    @Override
    public void addRoutes(Javalin app) {
        app.get("/player", getAllPlayers); //returns list of all players and all their info
        app.get("/player/{username}", getPlayer); //returns all info of one player
        app.delete("/player", gameOver);


    }
}

package com.revature.controllers;

import com.revature.models.Admin;
import com.revature.models.LoginDTO;
import com.revature.models.Player;
import com.revature.models.User;
import com.revature.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.Objects;

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
        if(ctx.req.getSession(false)!=null){
            Player player = userService.getPlayerByUsername(ctx.pathParam("username"));
            if(player != null){
                ctx.json(player);
                ctx.status(200);
            }else{
                ctx.html("<h1> No Player by that name exists! </h1>");
                ctx.status(404);
            }

        }else {
            ctx.status(401);
        }
    };


    @Override
    public void addRoutes(Javalin app) {
        app.get("/players", getAllPlayers);
        app.get("/players/{username}", getPlayer);
        //app.post("/user", addUser);


    }
}

package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class UserController {

    private UserService userService = new UserService();

    private Handler getAllUsers = (ctx)->{
       //List<User> list = userService.getAllUsers();
       ctx.json(userService.getAllUsers());
       ctx.status(200);
    };

    public void addRoutes(Javalin app) {
        app.get("/User", getAllUsers);
        //app.get("/User/{id}", getOneUser);
        //app.post("/User", addUser);

    }
}

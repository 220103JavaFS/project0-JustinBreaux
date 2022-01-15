package com.revature.controllers;

import com.revature.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController implements Controller{

    private UserService userService = new UserService();

    private Handler getAllUsers = (ctx)->{
       //List<User> list = userService.getAllUsers();
       ctx.json(userService.getAllUsers());
       ctx.status(200);
    };

    @Override
    public void addRoutes(Javalin app) {
        app.get("/User", getAllUsers);
        //app.get("/User/{id}", getOneUser);
        //app.post("/User", addUser);

    }
}

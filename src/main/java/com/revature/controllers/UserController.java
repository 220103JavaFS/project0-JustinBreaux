package com.revature.controllers;

import com.revature.models.LoginDTO;
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

    private Handler setPassword = (ctx)->{
        LoginDTO login = ctx.bodyAsClass(LoginDTO.class);

        if(userService.setPassword(login.username, login.password)){
            ctx.status(202);
        }else{
            ctx.status(401);
        }

    };

    @Override
    public void addRoutes(Javalin app) {
        app.get("/user", getAllUsers);
        //app.get("/user/{id}", getOneUser);
        //app.post("/user", addUser);
        app.post("/user/changepw", setPassword);

    }
}

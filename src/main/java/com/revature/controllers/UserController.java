package com.revature.controllers;

import com.revature.models.LoginDTO;
import com.revature.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController implements Controller{

    private UserService userService = new UserService();

    private Handler getAllUsers = (ctx)->{
       ctx.json(userService.getAllUsers());
       ctx.status(200);
    };

    private Handler setPassword = (ctx)->{
        if(ctx.req.getSession(false)!=null) {
            LoginDTO login = ctx.bodyAsClass(LoginDTO.class);

            if (userService.setPassword(login.userEmail, login.password)) {
                ctx.status(202);
            } else {
                ctx.status(401);
            }
        }else {
            ctx.status(401);
        }
    };

    private Handler login = (ctx)->{
        LoginDTO login = ctx.bodyAsClass(LoginDTO.class);

        switch (userService.login(login.userEmail, login.password)){
            case "admin": ctx.req.getSession();
                ctx.sessionAttribute("userType", "admin");
                ctx.status(200);
                break;
            case "player": ctx.req.getSession();
                ctx.sessionAttribute("userType", "player");
                ctx.status(200);
                break;
            case "failed": ctx.req.getSession().invalidate();
                ctx.status(401);
        }

//        if(userService.login(login.userEmail, login.password).isEmpty()){
//            ctx.req.getSession();
//            ctx.sessionAttribute("userType", "player");
//            ctx.status(200);
//        }else{
//            ctx.req.getSession().invalidate();
//            ctx.status(401);
//        }
    };

    @Override
    public void addRoutes(Javalin app) {
        app.get("/users", getAllUsers);
        //app.get("/user/{id}", getOneUser);
        //app.post("/user", addUser);
        app.post("/users/changepw", setPassword);
        app.post("/login", login);

    }
}

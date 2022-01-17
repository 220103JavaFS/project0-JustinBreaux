package com.revature.controllers;

import com.revature.models.LoginDTO;
import com.revature.models.Player;
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
        if(ctx.req.getSession(false)!=null){
            Player player = userService.getPlayer(ctx.pathParam("username"));
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

    private Handler setPassword = (ctx)->{
        if(ctx.req.getSession(false)!=null) {
            LoginDTO login = ctx.bodyAsClass(LoginDTO.class);

            if(ctx.sessionAttribute("email").equals(login.userEmail)){
                if (userService.setPassword(login.userEmail, login.password)) {
                    ctx.status(202);
                } else {
                    ctx.status(401);
                }
            }else{
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
                ctx.sessionAttribute("email", login.userEmail.toString());
                ctx.status(200);
                break;
            case "failed": ctx.req.getSession().invalidate();
                ctx.status(401);
        }
    };

    private Handler logout = (ctx)->{
        ctx.req.getSession().invalidate();
        ctx.status(200);
    };

    @Override
    public void addRoutes(Javalin app) {
        app.get("/players", getAllPlayers);
        app.get("/players/{username}", getPlayer);
        //app.post("/user", addUser);
        app.put("/players/{username}/changepw", setPassword);
        app.post("/login", login);
        app.post("/logout", logout);

    }
}

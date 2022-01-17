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

    private Handler setPassword = (ctx)->{
        if(ctx.req.getSession(false)!=null) {
            LoginDTO login = ctx.bodyAsClass(LoginDTO.class);
            String email;
            String userType = (String) ctx.req.getSession().getAttribute("userType");
            if(userType.equals("admin")){
                Admin admin = (Admin) ctx.req.getSession().getAttribute("userInfo");
                //Admin admin = userService.getAdminByEmail(login.userEmail);
                email = admin.getEmail();
            }else{
                Player player = (Player) ctx.req.getSession().getAttribute("userInfo");
                //Player player = userService.getPlayerByEmail(login.userEmail);
                email = player.getEmail();
            }

            userService.setPassword(email, login.password);
            ctx.status(200);
//            if(user.getEmail().equals(login.userEmail)){
//                if (userService.setPassword(login.userEmail, login.password)) {
//                    ctx.status(202);
//                } else {
//                    ctx.status(401);
//                }
//            }else{
//                ctx.status(401);
//            }
        }else {
            ctx.status(401);
        }
    };

    private Handler login = (ctx)->{
        LoginDTO login = ctx.bodyAsClass(LoginDTO.class);

        if (userService.login(login.userEmail, login.password)){

            if(userService.getAdminStatus(login.userEmail)){
                Admin admin = userService.getAdminByEmail(login.userEmail);
                ctx.req.getSession().setAttribute("userInfo", admin);
                ctx.req.getSession().setAttribute("userType", "admin");
                //ctx.sessionAttribute("user info", player);
            }else{
                Player player = userService.getPlayerByEmail(login.userEmail);
                ctx.req.getSession().setAttribute("userInfo", player);
                ctx.req.getSession().setAttribute("userType", "player");
            }

            ctx.status(200);
        }else{
            ctx.req.getSession().invalidate();
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
        app.put("/players/changepw", setPassword);
        app.post("/login", login);
        app.post("/logout", logout);

    }
}

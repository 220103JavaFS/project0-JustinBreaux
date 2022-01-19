package com.revature.controllers;

import com.revature.models.Admin;
import com.revature.models.LoginDTO;
import com.revature.models.Player;
import com.revature.services.LoginService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class LoginController implements Controller{
    private LoginService loginService = new LoginService();

    private Handler setPassword = (ctx)->{
        if(ctx.req.getSession(false)!=null) {
            LoginDTO login = ctx.bodyAsClass(LoginDTO.class);

            Admin user = (Admin) ctx.req.getSession().getAttribute("userInfo");

            if(loginService.setPassword(user.getEmail(), login.password)){
                ctx.status(200);
            }else{
                ctx.status(400);
            }
        }else {
            ctx.status(401);
        }
    };

    private Handler login = (ctx)->{
        LoginDTO login = ctx.bodyAsClass(LoginDTO.class);

        if (loginService.login(login.userEmail, login.password)){

            if(loginService.getAdminStatus(login.userEmail)){
                Admin admin = loginService.getAdminByEmail(login.userEmail);
                ctx.req.getSession().setAttribute("userInfo", admin);
            }else{
                Player player = loginService.getPlayerByEmail(login.userEmail);
                ctx.req.getSession().setAttribute("userInfo", player);
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
        app.patch("/changepw", setPassword);
        app.post("/login", login);
        app.post("/logout", logout);
    }
}

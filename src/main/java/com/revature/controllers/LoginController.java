package com.revature.controllers;

import com.revature.models.Admin;
import com.revature.models.Login;
import com.revature.models.Player;
import com.revature.services.LoginService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController implements Controller{
    private static Logger log = LoggerFactory.getLogger(LoginController.class);
    private LoginService loginService = new LoginService();

    private Handler setPassword = (ctx)->{
        if(ctx.req.getSession(false)!=null) {
            Login login = ctx.bodyAsClass(Login.class);

            Admin user = (Admin) ctx.req.getSession().getAttribute("userInfo");

            log.info(user.getEmail()+" requested to set a new password");

            if(loginService.setPassword(user.getEmail(), login.password)){
                ctx.status(200);
                log.info("request from "+user.getEmail()+" to change their password was successful");
            }else{
                ctx.status(400);
                log.info("request from "+user.getEmail()+" to change their password failed");
            }
        }else {
            ctx.status(401);
            log.info("access to change password denied");
        }
    };

    private Handler login = (ctx)->{
        Login login = ctx.bodyAsClass(Login.class);

        if (loginService.login(login.userEmail, login.password)){

            if(loginService.getAdminStatus(login.userEmail)){
                Admin admin = loginService.getAdminByEmail(login.userEmail);
                ctx.req.getSession().setAttribute("userInfo", admin);
            }else{
                Player player = loginService.getPlayerByEmail(login.userEmail);
                ctx.req.getSession().setAttribute("userInfo", player);
            }

            ctx.status(200);
            log.info(login.userEmail+" has logged in");
        }else{
            ctx.req.getSession().invalidate();
            ctx.status(401);
            log.info("invalid login credentials");
        }
    };

    private Handler logout = (ctx)->{
        Admin user = (Admin) ctx.req.getSession().getAttribute("userInfo");
        ctx.req.getSession().invalidate();
        ctx.status(200);
        log.info(user.getEmail()+" has logged out");
    };

    @Override
    public void addRoutes(Javalin app) {
        app.patch("/changepw", setPassword);
        app.post("/login", login);
        app.post("/logout", logout);
    }
}

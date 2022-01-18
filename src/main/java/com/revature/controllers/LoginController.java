package com.revature.controllers;

import com.revature.models.Admin;
import com.revature.models.LoginDTO;
import com.revature.models.Player;
import com.revature.services.LoginService;
import com.revature.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class LoginController implements Controller{
    LoginService loginService = new LoginService();
    UserService userService = new UserService();

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

            loginService.setPassword(email, login.password);
            ctx.status(200);
        }else {
            ctx.status(401);
        }
    };

    private Handler login = (ctx)->{
        LoginDTO login = ctx.bodyAsClass(LoginDTO.class);

        if (loginService.login(login.userEmail, login.password)){

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
        app.put("/changepw", setPassword);
        app.post("/login", login);
        app.post("/logout", logout);
    }
}
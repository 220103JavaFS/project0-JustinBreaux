package com.revature.controllers;

import com.revature.models.Admin;
import com.revature.models.Player;
import com.revature.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserController implements Controller{
    private static Logger log = LoggerFactory.getLogger(UserController.class);
    private UserService userService = new UserService();

    private Handler getAllPlayers = (ctx)->{
        if(ctx.req.getSession(false)!=null){
            Admin user = (Admin) ctx.req.getSession().getAttribute("userInfo");
            log.info(user.getEmail()+" has requested full player list");
            ctx.json(userService.getAllPlayers());
            ctx.status(200);
            log.info(user.getEmail()+" got a list of all players");
        }else {
            ctx.status(401);
            log.info("access to player list denied");
        }

    };

    private Handler getPlayer = (ctx)->{
        if(ctx.req.getSession(false)!=null){ //check if logged in
            Admin user = (Admin) ctx.req.getSession().getAttribute("userInfo");
            //Create new player object, get player by username from service layer with username provided by path parameter
            String username = ctx.pathParam("username");

            log.info(user.getEmail()+" requested info about "+username);

            Player player = userService.getPlayerByUsername(username);

            if(player != null){
                ctx.json(player); //returns complete player info
                ctx.status(200); //OK
                log.info("request from "+user.getEmail()+" about "+player.getUsername()+" was successful");
            }else{
                ctx.status(404); //not found
                log.info("request from "+user.getEmail()+" about "+player.getUsername()+" failed");
            }

        }else {
            ctx.status(401); //unauthorized
            log.info("access to player info denied");
        }
    };

    private Handler gameOver = (ctx) -> {
        if (ctx.req.getSession(false) != null){ //check if logged in
            Admin user = (Admin) ctx.req.getSession().getAttribute("userInfo"); //retrieve user info
            Player player = ctx.bodyAsClass(Player.class); //retrieve player info from request body
            log.info(user.getEmail()+" requested to delete "+player.getUsername());
            if(user.getClass().equals(Admin.class)){ //check admin status
                if(userService.deletePlayer(player)){ //attempt to delete player
                    ctx.status(202);
                    log.info("request from "+user.getEmail()+" to delete "+player.getUsername()+" was successful.");
                    log.info("Deleted info:"+player.toString());
                }else{ //request failed
                    ctx.status(404);
                    log.info("request from "+user.getEmail()+" to delete "+player.getUsername()+" failed");
                }
            }else{ //no permission
                ctx.status(403);
                log.info(user.getEmail()+" does not have permission to delete a player");
            }
        }else { //no access
            ctx.status(401);
            log.info("access to delete player denied");
        }

    };

    private Handler addTokens = (ctx)->{
        if (ctx.req.getSession(false) != null){
            Admin user = (Admin) ctx.req.getSession().getAttribute("userInfo");
            int amount = Integer.parseInt(ctx.body());
            String username = ctx.pathParam("username");

            log.info(user.getEmail()+" requested to modify "+username+"'s token balance");

            if(user.getClass().equals(Admin.class) || ((Player) user).getUsername().equals(username)){
                if(userService.addTokens(username, amount)){
                    ctx.status(202);
                    log.info(user.getEmail()+" modified "+username+"'s token balance by "+amount);
                }else{
                    ctx.status(400);
                    log.info("request from "+user.getEmail()+" to modify "+username+"'s token balance failed");
                }
            }else{ //no permission
                ctx.status(403);
                log.info(user.getEmail()+" does not have permission to modify "+username+"'s token balance");
            }
        }else { //no access
            ctx.status(401);
            log.info("access to modify token balance denied");
        }
    };


    @Override
    public void addRoutes(Javalin app) {
        app.get("/player", getAllPlayers); //returns list of all players and all their info
        app.get("/player/{username}", getPlayer); //returns all info of one player
        app.delete("/player", gameOver);
        app.patch("/player/{username}/modifytokens", addTokens);

    }
}

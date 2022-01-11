package com.revature;

import com.revature.controllers.UserController;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static Javalin app;
    private static Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        app = Javalin.create();

        configure(new UserController());

        app.start();


    }

    private static void configure(UserController... controllers) {
        for(UserController c:controllers){
            c.addRoutes(app);
        }
    }
}

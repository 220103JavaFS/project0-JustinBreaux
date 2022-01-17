package com.revature.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Record {
    private LocalDateTime time;
    private Player player;
    private int score;
    private int machineNum;
    private Game game;

//    public static void main(String[] args) {
//        time = LocalDateTime.of(2022, 1, 1, 14, 15, 0);
//        System.out.println(time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//    }
}

package org.grad.project.log;

import org.grad.project.model.Log;
import org.springframework.scheduling.annotation.Async;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Singleton {

    private PrintWriter writer;
    private String token;
    private final String key = "51e791da45820b34f137b8d9c87e733e";

    public Singleton() {
        try {
            writer = new PrintWriter(new FileWriter("log.txt", true), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final Singleton singleton = new Singleton();

    public static Singleton getInstance() {
        return singleton;
    }

    public String getKey() {
        return key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Async("threadPoolTaskExecutor")
    public void write(Log log, boolean exist, boolean within) {
        writer.printf("%s,\t%d,\t%.2f,\t%b,\t%b\n", log.getTime(), log.getId(), log.getTemp(), exist, within);
    }
}
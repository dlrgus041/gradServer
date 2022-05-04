package org.grad.project.log;

import org.grad.project.model.Log;
import org.springframework.scheduling.annotation.Async;

import java.io.*;
import java.util.Map;

public class IO {

    private PrintWriter writer;

    public IO() {
        try {
            writer = new PrintWriter(new FileWriter("log.txt", true), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final IO io = new IO();

    public static IO getInstance() {
        return io;
    }

    @Async("threadPoolTaskExecutor")
    public void write(Map<String, String> info, String time, int result) {
        writer.printf("%s,\t%s,\t%s,\t%d\n", time, info.get("ID"), info.get("temp"), result);
    }

    @Async("threadPoolTaskExecutor")
    public void write(Log log) {writer.printf("%s,\t%d,\t%.2f,\t%d\n", log.getTime(), log.getId(), log.getTemp(), log.getCode());
    }
}
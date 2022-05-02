package org.grad.project.log;

import org.grad.project.model.Log;
import org.springframework.scheduling.annotation.Async;

import java.io.*;
import java.util.Map;

public class IO {

    private PrintWriter writer;

    public IO() {
        try {
            writer = new PrintWriter(new FileWriter("log.txt"), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final IO io = new IO();

    public static IO getInstance() {
        return io;
    }

    @Async("threadPoolTaskExecutor")
    public void write(Map<String, String> info, String time, Boolean register) {
        writer.printf("%s,\t%s,\t%s,\t%b\n", time, info.get("ID"), info.get("temp"), register ? "O" : "X");
    }

    @Async("threadPoolTaskExecutor")
    public void write(Log log) {writer.printf("%s,\t%s,\t%s,\tO\n", log.getTime(), log.getId(), log.getTemp());
    }
}
package org.grad.project.log;

import org.grad.project.model.Log;
import org.springframework.scheduling.annotation.Async;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
    public void write(Log log, boolean exist, boolean within) {
        writer.printf("%s,\t%d,\t%.2f,\t%b,\t%b\n", log.getTime(), log.getId(), log.getTemp(), exist, within);
    }
}
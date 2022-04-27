package org.grad.project.system;

import org.springframework.scheduling.annotation.Async;

import java.io.*;
import java.util.Map;

public class IO {

    private static final IO io = new IO();

    private PrintWriter writer;
    private Long count = 1L;

    public IO() {
        try {
            writer = new PrintWriter("log.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static IO getInstance() {
        return io;
    }

    @Async("threadPoolTaskExecutor")
    public void write(Map<String, Object> info, String time) {
        writer.printf("%s, %s, %s, %d\n", time, info.get("ID"), info.get("temp"), count++);
    }
}

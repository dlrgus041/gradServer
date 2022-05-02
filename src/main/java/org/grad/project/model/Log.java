package org.grad.project.model;

public class Log {

    private String id, temp, time, register = "O";

    public Log() { }

    public Log(String line) {
        String[] info = line.split(",\t");
        this.id = info[1];
        this.temp = info[2];
        this.time = info[0];
        this.register = info[3];
    }

    public String getId() {
        return id;
    }

    public String getTemp() {
        return temp;
    }

    public String getTime() {
        return time;
    }

    public String getRegister() {
        return register;
    }
}

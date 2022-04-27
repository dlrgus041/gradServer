package org.grad.project.log;

public class Log {

    private final String count, id, temp, time;

    public Log(String line) {
        String[] info = line.split(", ");
        this.count = info[3];
        this.id = info[1];
        this.temp = info[2];
        this.time = info[0];
    }

    public String getCount() {
        return count;
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
}

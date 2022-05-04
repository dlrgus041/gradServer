package org.grad.project.model;

public class Log {

    public static final int PASS = 0;                   // GREEN
    public static final int DATA_DOES_NOT_EXIST = 1;    // RED
    public static final int INFO_DOES_NOT_MATCH = 2;    // YELLOW (RED + GREEN)
    public static final int TEMP_OUT_OF_RANGE = 3;      // PINK (RED + BLUE)

    private int id, code;
    private float temp;
    private String time;

    public Log() { }

    public Log(String line) {
        String[] info = line.split(",\t");
        this.time = info[0];
        this.id = Integer.parseInt(info[1]);
        this.temp = Float.parseFloat(info[2]);
        this.code = Integer.parseInt(info[3]);
    }

    public int getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public float getTemp() {
        return temp;
    }

    public String getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean checkTemp() {
        return temp >= 35 && temp <= 38;
    }
}

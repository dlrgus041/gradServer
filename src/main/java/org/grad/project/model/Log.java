package org.grad.project.model;

import java.util.Map;

public class Log {

    private int id;
    private float temp;
    private String time;
    private boolean exist, within;

    public Log(LogForm form, boolean exist, boolean within) {
        this.id = form.getId();
        this.temp = form.getTemp();
        this.time = form.getTime();
        this.exist = exist;
        this.within = within;
    }

    public Log(Map<String, String> info, String time, boolean exist, boolean within) {
        this.id = Integer.parseInt(info.get("id"));
        this.temp = Float.parseFloat(info.get("temp"));
        this.time = time;
        this.exist = exist;
        this.within = within;
    }

    public Log(String line) {
        String[] info = line.split(",\t");
        this.time = info[0];
        this.id = Integer.parseInt(info[1]);
        this.temp = Float.parseFloat(info[2]);
        this.exist = Boolean.parseBoolean(info[3]);
        this.within = Boolean.parseBoolean(info[4]);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public boolean getWithin() {
        return within;
    }

    public void setWithin(boolean within) {
        this.within = within;
    }
}

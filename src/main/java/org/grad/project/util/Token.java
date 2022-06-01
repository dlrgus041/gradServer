package org.grad.project.util;

public class Token {

    private String token = null;
    private long timeStamp = 0L;
    private long expiresIn = 0L;

    public void setToken(String token) {
        this.token = token;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public boolean isValid() {
        return token != null && (System.currentTimeMillis() - timeStamp) / 1000 < expiresIn;
    }
}
package org.grad.project.dto;

public class TokenResponse {
    public String token_type, access_token, refresh_token, scope;
    public int expires_in, refresh_token_expires_in;
}

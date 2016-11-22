package com.sola.github.solauiproject.param;


import java.io.Serializable;

public class UserInfoDTO extends UserDTO implements Serializable {

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

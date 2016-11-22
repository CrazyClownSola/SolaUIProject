package com.sola.github.solauiproject.param;

import java.io.Serializable;

/**
 * Created by huaixiangJin on 16/5/19.
 */
public class UserDTO implements Serializable {

    private String userId;

    private String nickname;

    private String gender;

    private String username;

    private String headPortrait;

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return The nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname The nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The headPortrait
     */
    public String getHeadPortrait() {
        return headPortrait;
    }

    /**
     * @param headPortrait The headPortrait
     */
    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }
}

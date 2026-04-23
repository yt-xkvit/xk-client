package com.xkclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a Minecraft account
 */
public class Account {
    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("refreshToken")
    private String refreshToken;

    @SerializedName("isOffline")
    private boolean isOffline;

    @SerializedName("lastLogin")
    private long lastLogin;

    @SerializedName("created")
    private long created;

    public Account() {
        this.created = System.currentTimeMillis();
    }

    public Account(String username, String email, String uuid) {
        this();
        this.username = username;
        this.email = email;
        this.uuid = uuid;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public boolean isOffline() { return isOffline; }
    public void setOffline(boolean offline) { isOffline = offline; }

    public long getLastLogin() { return lastLogin; }
    public void setLastLogin(long lastLogin) { this.lastLogin = lastLogin; }

    public long getCreated() { return created; }
    public void setCreated(long created) { this.created = created; }

    @Override
    public String toString() {
        return username + " (" + email + ")";
    }
}

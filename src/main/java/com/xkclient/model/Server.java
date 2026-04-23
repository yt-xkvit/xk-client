package com.xkclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a Minecraft server connection
 */
public class Server {
    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("port")
    private int port;

    @SerializedName("description")
    private String description;

    @SerializedName("icon")
    private String icon;

    @SerializedName("lastJoined")
    private long lastJoined;

    @SerializedName("joinCount")
    private int joinCount;

    @SerializedName("favorite")
    private boolean favorite;

    public Server() {
        this.port = 25565;
    }

    public Server(String name, String address) {
        this();
        this.name = name;
        this.address = address;
    }

    public Server(String name, String address, int port) {
        this();
        this.name = name;
        this.address = address;
        this.port = port;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public long getLastJoined() { return lastJoined; }
    public void setLastJoined(long lastJoined) { this.lastJoined = lastJoined; }

    public int getJoinCount() { return joinCount; }
    public void setJoinCount(int joinCount) { this.joinCount = joinCount; }
    public void incrementJoinCount() { this.joinCount++; }

    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    public String getFullAddress() {
        return address + ":" + port;
    }

    @Override
    public String toString() {
        return name + " (" + getFullAddress() + ")";
    }
}

package com.xkclient.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Minecraft game instance
 */
public class GameInstance {
    @SerializedName("name")
    private String name;

    @SerializedName("version")
    private String version;

    @SerializedName("instancePath")
    private String instancePath;

    @SerializedName("javaVersion")
    private String javaVersion;

    @SerializedName("jvmArguments")
    private String jvmArguments;

    @SerializedName("maxMemory")
    private int maxMemory; // in GB

    @SerializedName("minMemory")
    private int minMemory; // in GB

    @SerializedName("mods")
    private List<String> mods;

    @SerializedName("lastPlayed")
    private long lastPlayed;

    @SerializedName("created")
    private long created;

    @SerializedName("enabled")
    private boolean enabled;

    // Constructors
    public GameInstance() {
        this.mods = new ArrayList<>();
        this.created = System.currentTimeMillis();
        this.enabled = true;
        this.maxMemory = 4;
        this.minMemory = 2;
        this.jvmArguments = "-XX:+UseG1GC -XX:MaxGCPauseMillis=200";
    }

    public GameInstance(String name, String version, String instancePath) {
        this();
        this.name = name;
        this.version = version;
        this.instancePath = instancePath;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getInstancePath() { return instancePath; }
    public void setInstancePath(String instancePath) { this.instancePath = instancePath; }

    public String getJavaVersion() { return javaVersion; }
    public void setJavaVersion(String javaVersion) { this.javaVersion = javaVersion; }

    public String getJvmArguments() { return jvmArguments; }
    public void setJvmArguments(String jvmArguments) { this.jvmArguments = jvmArguments; }

    public int getMaxMemory() { return maxMemory; }
    public void setMaxMemory(int maxMemory) { this.maxMemory = maxMemory; }

    public int getMinMemory() { return minMemory; }
    public void setMinMemory(int minMemory) { this.minMemory = minMemory; }

    public List<String> getMods() { return mods; }
    public void setMods(List<String> mods) { this.mods = mods; }
    public void addMod(String modId) {
        if (!mods.contains(modId)) { mods.add(modId); }
    }
    public void removeMod(String modId) { mods.remove(modId); }

    public long getLastPlayed() { return lastPlayed; }
    public void setLastPlayed(long lastPlayed) { this.lastPlayed = lastPlayed; }

    public long getCreated() { return created; }
    public void setCreated(long created) { this.created = created; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    @Override
    public String toString() {
        return "GameInstance{" + "name='" + name + '\'' + ", version='" + version + '\'' + ", maxMemory=" + maxMemory + ", mods=" + mods.size() + '}';
    }
}

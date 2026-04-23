package com.xkclient.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Minecraft mod
 */
public class Mod {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("version")
    private String version;

    @SerializedName("author")
    private String author;

    @SerializedName("description")
    private String description;

    @SerializedName("filePath")
    private String filePath;

    @SerializedName("modLoader")
    private String modLoader; // FORGE, FABRIC, QUILT

    @SerializedName("minecraftVersion")
    private String minecraftVersion;

    @SerializedName("dependencies")
    private List<String> dependencies;

    @SerializedName("enabled")
    private boolean enabled;

    @SerializedName("installed")
    private boolean installed;

    @SerializedName("downloadUrl")
    private String downloadUrl;

    public Mod() {
        this.dependencies = new ArrayList<>();
        this.enabled = true;
        this.installed = false;
    }

    public Mod(String id, String name, String version) {
        this();
        this.id = id;
        this.name = name;
        this.version = version;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getModLoader() { return modLoader; }
    public void setModLoader(String modLoader) { this.modLoader = modLoader; }

    public String getMinecraftVersion() { return minecraftVersion; }
    public void setMinecraftVersion(String minecraftVersion) { this.minecraftVersion = minecraftVersion; }

    public List<String> getDependencies() { return dependencies; }
    public void setDependencies(List<String> dependencies) { this.dependencies = dependencies; }
    public void addDependency(String modId) {
        if (!dependencies.contains(modId)) { dependencies.add(modId); }
    }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public boolean isInstalled() { return installed; }
    public void setInstalled(boolean installed) { this.installed = installed; }

    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }

    @Override
    public String toString() {
        return name + " v" + version + " by " + author;
    }
}

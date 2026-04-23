package com.xkclient.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xkclient.model.Account;
import com.xkclient.model.GameInstance;
import com.xkclient.model.Mod;
import com.xkclient.model.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Service for managing configuration files
 */
public class ConfigService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigService.class);
    private static final String CONFIG_DIR = ".xkclient";
    private static final String INSTANCES_FILE = "instances.json";
    private static final String ACCOUNTS_FILE = "accounts.json";
    private static final String SERVERS_FILE = "servers.json";
    private static final String SETTINGS_FILE = "settings.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Path configPath;

    public ConfigService() {
        String userHome = System.getProperty("user.home");
        this.configPath = Paths.get(userHome, CONFIG_DIR);
        initializeDirectories();
    }

    private void initializeDirectories() {
        try {
            Files.createDirectories(configPath);
            Files.createDirectories(configPath.resolve("instances"));
            logger.info("Configuration directories initialized at: {}", configPath);
        } catch (IOException e) {
            logger.error("Failed to initialize configuration directories", e);
        }
    }

    public List<GameInstance> loadInstances() {
        Path instancesFile = configPath.resolve(INSTANCES_FILE);
        try {
            if (Files.exists(instancesFile)) {
                String content = new String(Files.readAllBytes(instancesFile), StandardCharsets.UTF_8);
                GameInstance[] instances = gson.fromJson(content, GameInstance[].class);
                logger.info("Loaded {} game instances", instances.length);
                return Arrays.asList(instances != null ? instances : new GameInstance[0]);
            }
        } catch (IOException e) {
            logger.error("Failed to load instances", e);
        }
        return new ArrayList<>();
    }

    public void saveInstances(List<GameInstance> instances) {
        Path instancesFile = configPath.resolve(INSTANCES_FILE);
        try {
            String json = gson.toJson(instances);
            Files.write(instancesFile, json.getBytes(StandardCharsets.UTF_8));
            logger.info("Saved {} game instances", instances.size());
        } catch (IOException e) {
            logger.error("Failed to save instances", e);
        }
    }

    public List<Account> loadAccounts() {
        Path accountsFile = configPath.resolve(ACCOUNTS_FILE);
        try {
            if (Files.exists(accountsFile)) {
                String content = new String(Files.readAllBytes(accountsFile), StandardCharsets.UTF_8);
                Account[] accounts = gson.fromJson(content, Account[].class);
                logger.info("Loaded {} accounts", accounts.length);
                return Arrays.asList(accounts != null ? accounts : new Account[0]);
            }
        } catch (IOException e) {
            logger.error("Failed to load accounts", e);
        }
        return new ArrayList<>();
    }

    public void saveAccounts(List<Account> accounts) {
        Path accountsFile = configPath.resolve(ACCOUNTS_FILE);
        try {
            String json = gson.toJson(accounts);
            Files.write(accountsFile, json.getBytes(StandardCharsets.UTF_8));
            logger.info("Saved {} accounts", accounts.size());
        } catch (IOException e) {
            logger.error("Failed to save accounts", e);
        }
    }

    public List<Server> loadServers() {
        Path serversFile = configPath.resolve(SERVERS_FILE);
        try {
            if (Files.exists(serversFile)) {
                String content = new String(Files.readAllBytes(serversFile), StandardCharsets.UTF_8);
                Server[] servers = gson.fromJson(content, Server[].class);
                logger.info("Loaded {} servers", servers.length);
                return Arrays.asList(servers != null ? servers : new Server[0]);
            }
        } catch (IOException e) {
            logger.error("Failed to load servers", e);
        }
        return new ArrayList<>();
    }

    public void saveServers(List<Server> servers) {
        Path serversFile = configPath.resolve(SERVERS_FILE);
        try {
            String json = gson.toJson(servers);
            Files.write(serversFile, json.getBytes(StandardCharsets.UTF_8));
            logger.info("Saved {} servers", servers.size());
        } catch (IOException e) {
            logger.error("Failed to save servers", e);
        }
    }

    public Map<String, Object> loadSettings() {
        Path settingsFile = configPath.resolve(SETTINGS_FILE);
        try {
            if (Files.exists(settingsFile)) {
                String content = new String(Files.readAllBytes(settingsFile), StandardCharsets.UTF_8);
                Map<String, Object> settings = gson.fromJson(content, Map.class);
                logger.info("Loaded settings");
                return settings != null ? settings : new HashMap<>();
            }
        } catch (IOException e) {
            logger.error("Failed to load settings", e);
        }
        return new HashMap<>();
    }

    public void saveSettings(Map<String, Object> settings) {
        Path settingsFile = configPath.resolve(SETTINGS_FILE);
        try {
            String json = gson.toJson(settings);
            Files.write(settingsFile, json.getBytes(StandardCharsets.UTF_8));
            logger.info("Saved settings");
        } catch (IOException e) {
            logger.error("Failed to save settings", e);
        }
    }

    public Path getConfigPath() { return configPath; }
    public Path getInstancesPath() { return configPath.resolve("instances"); }
    public Path getModsPath(String instanceName) { return configPath.resolve("instances").resolve(instanceName).resolve("mods"); }
}

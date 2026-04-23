package com.xkclient.service;

import com.xkclient.model.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing saved servers
 */
public class ServerService {
    private static final Logger logger = LoggerFactory.getLogger(ServerService.class);
    private final ConfigService configService;
    private final List<Server> servers;

    public ServerService(ConfigService configService) {
        this.configService = configService;
        this.servers = configService.loadServers();
    }

    public void addServer(Server server) {
        servers.add(server);
        save();
        logger.info("Added server: {}", server.getName());
    }

    public void removeServer(String name) {
        servers.removeIf(server -> server.getName().equals(name));
        save();
        logger.info("Removed server: {}", name);
    }

    public List<Server> getServers() {
        return new ArrayList<>(servers);
    }

    public List<Server> getFavoriteServers() {
        return servers.stream()
                .filter(Server::isFavorite)
                .sorted(Comparator.comparingLong(Server::getLastJoined).reversed())
                .collect(Collectors.toList());
    }

    public Server getServer(String name) {
        return servers.stream()
                .filter(server -> server.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void updateServer(Server server) {
        Server existing = getServer(server.getName());
        if (existing != null) {
            int index = servers.indexOf(existing);
            servers.set(index, server);
            save();
            logger.info("Updated server: {}", server.getName());
        }
    }

    public void recordJoin(String serverName) {
        Server server = getServer(serverName);
        if (server != null) {
            server.setLastJoined(System.currentTimeMillis());
            server.incrementJoinCount();
            updateServer(server);
            logger.info("Recorded join for server: {}", serverName);
        }
    }

    public void save() {
        configService.saveServers(servers);
    }
}

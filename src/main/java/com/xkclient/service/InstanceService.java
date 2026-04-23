package com.xkclient.service;

import com.xkclient.model.GameInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing game instances
 */
public class InstanceService {
    private static final Logger logger = LoggerFactory.getLogger(InstanceService.class);
    private final ConfigService configService;
    private final List<GameInstance> instances;

    public InstanceService(ConfigService configService) {
        this.configService = configService;
        this.instances = configService.loadInstances();
    }

    public GameInstance createInstance(String name, String version) {
        GameInstance instance = new GameInstance();
        instance.setName(name);
        instance.setVersion(version);
        instance.setInstancePath(configService.getInstancesPath().resolve(name).toString());
        createInstanceDirectories(instance);
        instances.add(instance);
        save();
        logger.info("Created new instance: {}", name);
        return instance;
    }

    public void deleteInstance(String name) {
        instances.removeIf(inst -> inst.getName().equals(name));
        save();
        logger.info("Deleted instance: {}", name);
    }

    public List<GameInstance> getInstances() {
        return new ArrayList<>(instances);
    }

    public GameInstance getInstance(String name) {
        return instances.stream()
                .filter(inst -> inst.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void updateInstance(GameInstance instance) {
        int index = instances.indexOf(instances.stream()
                .filter(inst -> inst.getName().equals(instance.getName()))
                .findFirst()
                .orElse(null));
        if (index >= 0) {
            instances.set(index, instance);
            save();
            logger.info("Updated instance: {}", instance.getName());
        }
    }

    public void addMod(String instanceName, String modId) {
        GameInstance instance = getInstance(instanceName);
        if (instance != null) {
            instance.addMod(modId);
            updateInstance(instance);
            logger.info("Added mod {} to instance {}", modId, instanceName);
        }
    }

    public void removeMod(String instanceName, String modId) {
        GameInstance instance = getInstance(instanceName);
        if (instance != null) {
            instance.removeMod(modId);
            updateInstance(instance);
            logger.info("Removed mod {} from instance {}", modId, instanceName);
        }
    }

    private void createInstanceDirectories(GameInstance instance) {
        try {
            Path basePath = Path.of(instance.getInstancePath());
            Files.createDirectories(basePath.resolve("mods"));
            Files.createDirectories(basePath.resolve("config"));
            Files.createDirectories(basePath.resolve("saves"));
            logger.info("Created directories for instance: {}", instance.getName());
        } catch (IOException e) {
            logger.error("Failed to create instance directories", e);
        }
    }

    public void save() {
        configService.saveInstances(instances);
    }
}

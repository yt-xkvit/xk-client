package com.xkclient.test;

import com.xkclient.model.GameInstance;
import com.xkclient.service.ConfigService;
import com.xkclient.service.InstanceService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for InstanceService
 */
public class InstanceServiceTest {
    private ConfigService configService;
    private InstanceService instanceService;

    @Before
    public void setUp() {
        configService = new ConfigService();
        instanceService = new InstanceService(configService);
    }

    @Test
    public void testCreateInstance() {
        GameInstance instance = instanceService.createInstance("TestInstance", "1.20.1");
        assertNotNull(instance);
        assertEquals("TestInstance", instance.getName());
        assertEquals("1.20.1", instance.getVersion());
    }

    @Test
    public void testGetInstance() {
        instanceService.createInstance("TestInstance", "1.20.1");
        GameInstance instance = instanceService.getInstance("TestInstance");
        assertNotNull(instance);
        assertEquals("TestInstance", instance.getName());
    }

    @Test
    public void testGetInstances() {
        instanceService.createInstance("Instance1", "1.20.1");
        instanceService.createInstance("Instance2", "1.19.3");
        List<GameInstance> instances = instanceService.getInstances();
        assertTrue(instances.size() >= 2);
    }

    @Test
    public void testDeleteInstance() {
        instanceService.createInstance("TestInstance", "1.20.1");
        instanceService.deleteInstance("TestInstance");
        GameInstance instance = instanceService.getInstance("TestInstance");
        assertNull(instance);
    }

    @Test
    public void testAddMod() {
        instanceService.createInstance("TestInstance", "1.20.1");
        instanceService.addMod("TestInstance", "mod1");
        GameInstance instance = instanceService.getInstance("TestInstance");
        assertTrue(instance.getMods().contains("mod1"));
    }

    @Test
    public void testRemoveMod() {
        instanceService.createInstance("TestInstance", "1.20.1");
        instanceService.addMod("TestInstance", "mod1");
        instanceService.removeMod("TestInstance", "mod1");
        GameInstance instance = instanceService.getInstance("TestInstance");
        assertFalse(instance.getMods().contains("mod1"));
    }
}

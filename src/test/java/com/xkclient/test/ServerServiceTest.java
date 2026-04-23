package com.xkclient.test;

import com.xkclient.model.Server;
import com.xkclient.service.ConfigService;
import com.xkclient.service.ServerService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for ServerService
 */
public class ServerServiceTest {
    private ConfigService configService;
    private ServerService serverService;

    @Before
    public void setUp() {
        configService = new ConfigService();
        serverService = new ServerService(configService);
    }

    @Test
    public void testAddServer() {
        Server server = new Server("TestServer", "example.com", 25565);
        serverService.addServer(server);
        Server retrieved = serverService.getServer("TestServer");
        assertNotNull(retrieved);
        assertEquals("TestServer", retrieved.getName());
        assertEquals("example.com", retrieved.getAddress());
    }

    @Test
    public void testGetServers() {
        Server server1 = new Server("Server1", "server1.com", 25565);
        Server server2 = new Server("Server2", "server2.com", 25565);
        serverService.addServer(server1);
        serverService.addServer(server2);
        List<Server> servers = serverService.getServers();
        assertTrue(servers.size() >= 2);
    }

    @Test
    public void testRemoveServer() {
        Server server = new Server("TestServer", "example.com", 25565);
        serverService.addServer(server);
        serverService.removeServer("TestServer");
        Server retrieved = serverService.getServer("TestServer");
        assertNull(retrieved);
    }

    @Test
    public void testRecordJoin() {
        Server server = new Server("TestServer", "example.com", 25565);
        serverService.addServer(server);
        serverService.recordJoin("TestServer");
        Server retrieved = serverService.getServer("TestServer");
        assertTrue(retrieved.getJoinCount() > 0);
    }
}

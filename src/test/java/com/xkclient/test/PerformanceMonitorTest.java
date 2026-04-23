package com.xkclient.test;

import com.xkclient.util.PerformanceMonitor;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for PerformanceMonitor
 */
public class PerformanceMonitorTest {
    @Test
    public void testGetMemoryUsageMB() {
        long memory = PerformanceMonitor.getMemoryUsageMB();
        assertTrue(memory > 0);
    }

    @Test
    public void testGetMaxMemoryMB() {
        long maxMemory = PerformanceMonitor.getMaxMemoryMB();
        assertTrue(maxMemory > 0);
    }

    @Test
    public void testGetCpuUsage() {
        double cpuUsage = PerformanceMonitor.getCpuUsage();
        assertTrue(cpuUsage >= 0 && cpuUsage <= 100);
    }

    @Test
    public void testGetAvailableCores() {
        int cores = PerformanceMonitor.getAvailableCores();
        assertTrue(cores > 0);
    }

    @Test
    public void testGetSystemInfo() {
        String systemInfo = PerformanceMonitor.getSystemInfo();
        assertNotNull(systemInfo);
        assertTrue(systemInfo.contains("OS:"));
    }
}

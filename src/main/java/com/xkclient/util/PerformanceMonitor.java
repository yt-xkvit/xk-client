package com.xkclient.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;

/**
 * Utility class for system performance monitoring
 */
public class PerformanceMonitor {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceMonitor.class);
    private static final MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
    private static final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

    /**
     * Get current memory usage in MB
     */
    public static long getMemoryUsageMB() {
        return memoryBean.getHeapMemoryUsage().getUsed() / (1024 * 1024);
    }

    /**
     * Get maximum memory available in MB
     */
    public static long getMaxMemoryMB() {
        return memoryBean.getHeapMemoryUsage().getMax() / (1024 * 1024);
    }

    /**
     * Get CPU usage percentage
     */
    public static double getCpuUsage() {
        return osBean.getProcessCpuLoad() * 100;
    }

    /**
     * Get system memory usage percentage
     */
    public static double getSystemMemoryUsage() {
        long totalMemory = osBean.getTotalPhysicalMemorySize();
        long freeMemory = osBean.getFreePhysicalMemorySize();
        return ((totalMemory - freeMemory) / (double) totalMemory) * 100;
    }

    /**
     * Get available CPU cores
     */
    public static int getAvailableCores() {
        return osBean.getAvailableProcessors();
    }

    /**
     * Get system information
     */
    public static String getSystemInfo() {
        StringBuilder info = new StringBuilder();
        info.append("OS: ").append(System.getProperty("os.name")).append("\n");
        info.append("OS Version: ").append(System.getProperty("os.version")).append("\n");
        info.append("Java Version: ").append(System.getProperty("java.version")).append("\n");
        info.append("Available Cores: ").append(getAvailableCores()).append("\n");
        info.append("Memory: ").append(getMemoryUsageMB()).append("MB / ").append(getMaxMemoryMB()).append("MB\n");
        info.append("CPU Usage: ").append(String.format("%.2f", getCpuUsage())).append("%\n");
        return info.toString();
    }
}

package com.nilesh.practice.autosuggest.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class Memory {

    public long printSystemMemoryUsage() {
        // OperatingSystemMXBean osMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        // Get total physical memory and free memory
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        long usedMemory = heapMemoryUsage.getUsed();
        // System.out.println("System Memory Usage:");
        // System.out.printf("  Total Memory: %d MB\n", usedMemory / 1_000_000);
        // long totalMemory = osMXBean.getgetTotalMemorySize();
        // long freeMemory = osMXBean.getFreeMemorySize();
        //
        // // Print out system memory details
        // System.out.println("System Memory Usage:");
        // System.out.printf("  Total Memory: %d bytes\n", totalMemory);
        // System.out.printf("  Total Memory: %d MB\n", totalMemory/1_000_000);
        // System.out.printf("  Free Memory: %d bytes\n", freeMemory);
        // System.out.printf("  Free Memory: %d MB\n", freeMemory/1_000_000);
        // System.out.printf("  Used Memory: %d bytes\n", totalMemory - freeMemory);
        // System.out.printf("  Used Memory: %d MB\n", (totalMemory - freeMemory)/1_000_000);
        // System.out.println("----------------------");
        return usedMemory / 1_000_000;
    }
}

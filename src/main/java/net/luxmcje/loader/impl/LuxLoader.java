package net.luxmcje.loader.impl;

import net.luxmcje.loader.mod.ModResolver;

public class LuxLoader {
    public static final String VERSION = "0.1.0-alpha";
    
    public static final String META_BASE_URL = "https://raw.githubusercontent.com/LuxMCJE/lux-meta/main/v1/";

    public static void boot() {
        System.out.println("---------------------------------------");
        System.out.println("   LuxLoader Java Edition (LuxMCJE)   ");
        System.out.println("   Version: " + VERSION);
        System.out.println("   License: Apache-2.0");
        System.out.println("---------------------------------------");
        
        System.out.println("[Lux-Core] Meta Server: " + META_BASE_URL);
        System.out.println("[Lux-Core] Checking Loader Info: " + getMetaUrl("loader.json"));

        try {
            ModResolver.resolve();
        } catch (Exception e) {
            System.err.println("[Lux-Core] Critical error during mod resolution!");
            e.printStackTrace();
        }
    }

    public static String getMetaUrl(String endpoint) {
        return META_BASE_URL + endpoint;
    }
}

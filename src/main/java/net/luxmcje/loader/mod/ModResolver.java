package net.luxmcje.loader.mod;

import net.luxmcje.loader.impl.LuxLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModResolver {

    private static final List<String> DISCOVERED_MODS = new ArrayList<>();

    public static void resolve() {
        System.out.println("[Lux-Resolver] Initializing mod resolution process...");

        File modsFolder = new File("mods");

        if (!modsFolder.exists()) {
            System.out.println("[Lux-Resolver] Mods folder not found, creating one...");
            modsFolder.mkdirs();
            return;
        }

        File[] files = modsFolder.listFiles((dir, name) -> name.endsWith(".jar"));

        if (files == null || files.length == 0) {
            System.out.println("[Lux-Resolver] No mods found in /mods folder.");
            return;
        }

        for (File modFile : files) {
            System.out.println("[Lux-Resolver] Found potential mod: " + modFile.getName());
            processMod(modFile);
        }

        System.out.println("[Lux-Resolver] Successfully resolved " + DISCOVERED_MODS.size() + " mods.");
    }

    private static void processMod(File file) {
        DISCOVERED_MODS.add(file.getName());
    }
    
    public static List<String> getDiscoveredMods() {
        return DISCOVERED_MODS;
    }
}

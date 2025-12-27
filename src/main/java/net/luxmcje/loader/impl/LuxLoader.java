package net.luxmcje.loader.impl;

import net.luxmcje.loader.impl.discovery.ModScanner;
import net.luxmcje.loader.impl.discovery.ModCandidate;
import net.luxmcje.loader.impl.launch.ModLauncher;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

public class LuxLoader {
    public static final String VERSION = "0.1.0-alpha";
    public static final String META_BASE_URL = "https://raw.githubusercontent.com/LuxMCJE/lux-meta/main/v1/";

    public static void main(String[] args) {
        System.out.println("---------------------------------------");
        System.out.println("   LuxLoader Java Edition (LuxMCJE)   ");
        System.out.println("   Version: " + VERSION);
        System.out.println("---------------------------------------");
        
        System.out.println("[Lux-Core] Meta Server: " + META_BASE_URL);
        
        scanLocalMods();

        System.out.println("[Lux-Core] Checking Loader Info: " + getMetaUrl("loader.json"));
    }

    private static void scanLocalMods() {
        String gameDir = System.getProperty("user.dir");
        Path modsPath = Paths.get(gameDir, "mods");

        System.out.println("[Lux-Scanner] Searching for mods in /mods directory...");

        ModScanner scanner = new ModScanner();
        
        try {
            java.nio.file.Files.createDirectories(modsPath);
            
            scanner.scanDirectory(modsPath);
            List<ModCandidate> candidates = scanner.getFoundMods();
            
            System.out.println("[Lux-Scanner] Success! Found " + candidates.size() + " mods.");

            if (!candidates.isEmpty()) {
                ModLauncher launcher = new ModLauncher();
                launcher.launch(candidates);
            }
            
            for (ModCandidate mod : candidates) {
                System.out.println("  >> Loaded: " + mod.id() + " [" + mod.version() + "]");
            }
        } catch (Exception e) {
            System.err.println("[Lux-Scanner] Critical error while scanning mods!");
            e.printStackTrace();
        }
    }
    
    public static String getMetaUrl(String endpoint) {
        return META_BASE_URL + endpoint;
    }
}

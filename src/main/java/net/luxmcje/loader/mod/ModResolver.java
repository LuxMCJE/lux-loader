package net.luxmcje.loader.mod;

import net.luxmcje.loader.impl.LuxLoader;
import com.google.gson.Gson;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModResolver {

    private static final List<ModMetadata> DISCOVERED_MODS = new ArrayList<>();
    private static final Gson GSON = new Gson();

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
            processMod(modFile);
        }

        System.out.println("[Lux-Resolver] Successfully resolved " + DISCOVERED_MODS.size() + " valid Lux mods.");

        ModLauncher.launch(DISCOVERED_MODS);
    }

    private static void processMod(File file) {
        try (JarFile jar = new JarFile(file)) {
            JarEntry entry = jar.getJarEntry("lux.mod.json");

            if (entry != null) {
                try (InputStreamReader reader = new InputStreamReader(jar.getInputStream(entry))) {
                    ModMetadata meta = GSON.fromJson(reader, ModMetadata.class);
                    
                    if (meta != null && meta.id != null) {
                        System.out.println("[Lux-Resolver] Verified mod: " + meta.id + " v" + meta.version);
                        DISCOVERED_MODS.add(meta);
                    }
                }
            } else {
                System.out.println("[Lux-Resolver] Skipping " + file.getName() + " (Missing lux.mod.json)");
            }
        } catch (Exception e) {
            System.err.println("[Lux-Resolver] Error reading jar file: " + file.getName());
        }
    }
    
    public static List<ModMetadata> getDiscoveredMods() {
        return DISCOVERED_MODS;
    }
}

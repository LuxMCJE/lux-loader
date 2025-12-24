package net.luxmcje.loader.mod;

import net.luxmcje.loader.api.LuxMod;
import java.util.List;

public class ModLauncher {

    public static void launch(List<ModMetadata> mods) {
        System.out.println("[Lux-Launcher] Preparing to launch " + mods.size() + " mods...");

        for (ModMetadata meta : mods) {
            try {
                System.out.println("[Lux-Launcher] Launching mod: " + meta.id);
                
                Class<?> modClass = Class.forName(meta.entrypoint);
                
                if (LuxMod.class.isAssignableFrom(modClass)) {
                    LuxMod instance = (LuxMod) modClass.getDeclaredConstructor().newInstance();
                    
                    instance.onInitialize();
                    System.out.println("[Lux-Launcher] Mod " + meta.id + " initialized successfully.");
                } else {
                    System.err.println("[Lux-Launcher] Critical: " + meta.id + " does not implement LuxMod!");
                }
                
            } catch (Exception e) {
                System.err.println("[Lux-Launcher] Failed to launch mod: " + meta.id);
                e.printStackTrace();
            }
        }
    }
}

package net.luxmcje.loader.impl.launch;

import net.luxmcje.loader.api.LuxMod;
import net.luxmcje.loader.impl.discovery.ModCandidate;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class ModLauncher {
    public void launch(List<ModCandidate> mods) {
        for (ModCandidate mod : mods) {
            try {
                URL jarUrl = mod.jarPath().toUri().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl}, this.getClass().getClassLoader());

                Class<?> modClass = Class.forName(mod.mainClass(), true, classLoader);
      
                if (LuxMod.class.isAssignableFrom(modClass)) {
                    LuxMod instance = (LuxMod) modClass.getDeclaredConstructor().newInstance();
                 
                    instance.onInitialize();
                    System.out.println("[Lux-Launcher] Initialized mod: " + mod.id());
                }
            } catch (Exception e) {
                System.err.println("[Lux-Launcher] Failed to launch mod: " + mod.id());
                e.printStackTrace();
            }
        }
    }
}

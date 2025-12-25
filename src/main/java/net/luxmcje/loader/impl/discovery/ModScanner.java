package net.luxmcje.loader.impl.discovery;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModScanner {
    private final List<ModCandidate> foundMods = new ArrayList<>();

    public void scanDirectory(Path modsDir) throws Exception {
        if (!Files.exists(modsDir)) Files.createDirectories(modsDir);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(modsDir, "*.jar")) {
            for (Path path : stream) {
                try (ZipFile zip = new ZipFile(path.toFile())) {
                    ZipEntry entry = zip.getEntry("lux.mod.json");
                    if (entry != null) {
                        JsonObject json = JsonParser.parseReader(
                            new InputStreamReader(zip.getInputStream(entry))).getAsJsonObject();
                        
                        String id = json.get("id").getAsString();
                        String version = json.get("version").getAsString();
                        String mainClass = json.get("main").getAsString();
                        
                        foundMods.add(new ModCandidate(id, version, path, mainClass));
                        System.out.println("[Lux-Loader] Mapped mod: " + id + " (" + version + ")");
                    }
                }
            }
        }
    }

    public List<ModCandidate> getFoundMods() {
        return foundMods;
    }
}

package net.luxmcje.loader.mod;

public class ModMetadata {
    public String id;
    public String version;
    public String entrypoint;

    public ModMetadata(String id, String version, String entrypoint) {
        this.id = id;
        this.version = version;
        this.entrypoint = entrypoint;
    }
}

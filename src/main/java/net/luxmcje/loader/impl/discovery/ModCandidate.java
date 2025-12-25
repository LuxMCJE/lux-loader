package net.luxmcje.loader.impl.discovery;

import java.nio.file.Path;
import java.util.jar.Attributes;

public record ModCandidate(String id, String version, Path jarPath, String mainClass) {
}

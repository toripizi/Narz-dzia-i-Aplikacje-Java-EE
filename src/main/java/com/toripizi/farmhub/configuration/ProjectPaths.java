package com.toripizi.farmhub.configuration;

import java.nio.file.Path;

public class ProjectPaths {

    public static Path getAvatarPath(String name, String resourcesPath) {
        String path = ((name.endsWith(".png")) ? resourcesPath + name : resourcesPath + name + ".png");
        return Path.of(path);
    }
}

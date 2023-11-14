package com.toripizi.farmhub.configuration;

import java.nio.file.Path;

public class ProjectPaths {
    private static final String resourcePath = "/home/toripizi/Studies/sem7/farmhub/avatars/";

    public static Path getAvatarPath(String name) {
        return Path.of((name.endsWith(".png")) ? resourcePath + name : resourcePath + name + ".png");
    }
}

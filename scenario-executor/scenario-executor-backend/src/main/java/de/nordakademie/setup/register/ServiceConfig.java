package de.nordakademie.setup.register;

public record ServiceConfig(String name, String path, String port) {

    public String getFullQualifiedPath() {
        return path + ":" + port + "/";
    }
}

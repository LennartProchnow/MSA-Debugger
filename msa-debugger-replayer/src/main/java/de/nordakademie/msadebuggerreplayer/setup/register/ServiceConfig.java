package de.nordakademie.msadebuggerreplayer.setup.register;

public record ServiceConfig(String name, String hostUri) {

    public String getFullQualifiedPath() {
        return hostUri + "/";
    }
}

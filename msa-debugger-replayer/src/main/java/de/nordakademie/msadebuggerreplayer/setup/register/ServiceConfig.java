package de.nordakademie.msadebuggerreplayer.setup.register;

import java.util.Objects;

public record ServiceConfig(String name, String hostUri) {

    public String getFullQualifiedPath() {
        return hostUri + "/";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceConfig that = (ServiceConfig) o;

        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(hostUri, that.hostUri);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (hostUri != null ? hostUri.hashCode() : 0);
        return result;
    }
}

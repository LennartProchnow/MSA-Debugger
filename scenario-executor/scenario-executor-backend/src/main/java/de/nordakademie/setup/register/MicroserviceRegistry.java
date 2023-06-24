package de.nordakademie.setup.register;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class MicroserviceRegistry {

    private Map<String, ServiceConfig> registry = new HashMap<>();

    public void register(String serviceName, ServiceConfig config) {
        registry.put(serviceName, config);
    }

    public void deregister(String serviceName) {
        registry.remove(serviceName);
    }

    public void getServiceConfig(String serviceName) {
        registry.get(serviceName);
    }

    public Set<String> clear() {
        Set<String> result = registry.keySet();
        registry.clear();
        return result;
    }
}

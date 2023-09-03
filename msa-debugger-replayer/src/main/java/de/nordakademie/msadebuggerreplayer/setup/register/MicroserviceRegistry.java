package de.nordakademie.msadebuggerreplayer.setup.register;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MicroserviceRegistry {

    private Map<String, ServiceConfig> registry = new HashMap<>();

    public void register(String serviceName, ServiceConfig config) {
        registry.put(serviceName, config);
    }

    public void deregister(String serviceName) {
        registry.remove(serviceName);
    }

    public ServiceConfig getServiceConfig(String serviceName) {
        return registry.get(serviceName);
    }

    public List<ServiceConfig> getAllServiceConfigs() {
        return registry.values().stream().toList();
    }

    public Set<String> clear() {
        Set<String> result = registry.keySet();
        registry.clear();
        return result;
    }

    public boolean isRegistered(String target, String source) {
        return registry.containsKey(target) || registry.containsKey(source);
    }
}

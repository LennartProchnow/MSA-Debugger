package de.nordakademie.msadebuggerreplayer.setup.register;

import jakarta.annotation.Nonnull;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class MicroserviceRegisterController {

    Logger logger = Logger.getLogger(MicroserviceRegisterController.class);

    @Autowired
    private MicroserviceRegistry registry;

    @PostMapping("/register/{name}")
    public void register(@PathVariable(name = "name") String name, @RequestBody ServiceConfig config) {
        registry.register(name, config);
        logger.info(String.format("service %s registered for next scenario execution", name));
    }

    @PostMapping("/deregister/{name}")
    public void deregister(@PathVariable(name = "name") String name) {
        registry.deregister(name);
        logger.info(String.format("service %s deregistered for next scenario execution", name));
    }

    @PostMapping("/deregister")
    public void deregisterAll(){
        List<ServiceConfig> services = registry.getAllServiceConfigs();
        services.forEach(s -> logger.info(String.format("service %s deregistered for next scenario execution", s.name())));
        registry.clear();
    }

}

package de.nordakademie.setup.register;

import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.logging.Logger;

import java.util.Set;

@Path("/service")
@ApplicationScoped
public class MicroserviceRegisterResource {

    Logger logger = Logger.getLogger(MicroserviceRegisterResource.class);

    @Inject
    MicroserviceRegistry registry;

    @POST
    @Path("/register/{name}")
    public void register(@NotNull String name, @NotNull ServiceConfig config) {
        registry.register(name, config);
        logger.info(String.format("service %s registered for next scenario execution", name));
    }

    @POST
    @Path("/deregister/{name}")
    public void deregister(@NotNull String name) {
        registry.deregister(name);
        logger.info(String.format("service %s deregistered for next scenario execution", name));
    }

    @POST
    @Path("/deregister")
    public void deregisterAll(){
        Set<String> services = registry.clear();
        services.forEach(s -> logger.info(String.format("service %s deregistered for next scenario execution", s)));
    }

}

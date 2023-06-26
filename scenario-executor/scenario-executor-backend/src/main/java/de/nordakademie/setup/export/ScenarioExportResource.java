package de.nordakademie.setup.export;

import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@Path("replay/setup/scenario")
@ApplicationScoped
public class ScenarioExportResource {

    Logger logger = Logger.getLogger(ScenarioExportResource.class);

    @Inject
    ScenarioExportRegistry registry;

    @RestClient
    ScenarioExportService scenarioClientService;

    @Path("/{id}")
    @POST
    public void exportScenario(@NotNull String id) {
        var export = scenarioClientService.getById(id);
        if(export == null) {
            logger.warn(String.format("No scenario with the id: %s found", id));
        } else {
            logger.info(String.format("Scenario with the id: %s found", id));
            registry.registerScenario(export);
        }
    }
}

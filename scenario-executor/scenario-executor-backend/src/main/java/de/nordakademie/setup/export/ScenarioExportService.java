package de.nordakademie.setup.export;

import de.nordakademie.setup.export.model.Scenario;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("scenario/")
@RegisterRestClient(configKey = "scenario-recorder-export-api")
public interface ScenarioExportService {

    @GET
    @Path("/{id}")
    Scenario getById(String id);
}

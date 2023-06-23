package de.nordakademie.export;

import de.nordakademie.export.model.Scenario;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("scenario/")
@RegisterRestClient(configKey = "scenario-recorder-export-api")
public interface ScenarioExportService {

    @GET
    @Path("/{id}")
    Scenario getById(String id);
}

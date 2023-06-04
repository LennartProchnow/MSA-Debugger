package de.nordakademie.scenario.rest;

import de.nordakademie.scenario.modell.ContentType;
import de.nordakademie.scenario.modell.RequestRecord;
import de.nordakademie.scenario.modell.scenarioModell.*;
import de.nordakademie.scenario.services.ScenarioService;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Produces(MediaType.TEXT_PLAIN)
@Path("/scenario/request")
@ApplicationScoped
public class ScenarioRequestResource {

    @Inject
    EntityManager em;

    @Inject
    ScenarioService scenarioService;

    @POST
    @Path("/{traceId}")
    @Transactional
    public String addRequest(@PathParam("traceId") String traceId, @NotNull RequestRecord request) {
        var scenario = scenarioService.getActiveScenario();

        var event = new Event();

        event.setType(Communication.REQUEST);
        var requestId = new Header("requestId", request.getRequestId());
        var authority = new Header("authority", request.getAuthority());

        var body = new EventBody();
        //ToDo: das müsste hier dann eigentlich aus den Headern rausgelesen werden,
        // oder halt per Default auf Plain Text gesetzt werden
        body.setContentType(ContentType.APPLICATION_JSON);
        body.setBody(request.getBody());

        event.setBody(body);

        event.setLamportTime(scenario.nextLamportTime());

        event.addHeader(requestId);

        event.addHeader(authority);

        scenario.addEvent(event);

        em.merge(scenario);
        em.flush();


        var read = em.find(Scenario.class, scenario.getId());

        return String.valueOf(scenario.getId());
    }

}

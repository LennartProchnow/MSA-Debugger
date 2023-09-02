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
import org.jboss.logging.Logger;

@Produces(MediaType.APPLICATION_JSON)
@Path("/scenario/request")
@ApplicationScoped
public class ScenarioRequestResource {

    @Inject
    EntityManager em;

    @Inject
    ScenarioService scenarioService;

    private static final Logger logger = Logger.getLogger(ScenarioRequestResource.class);

    @POST
    @Transactional
    public String addRequest(@NotNull RequestRecord request) {
        var scenario = scenarioService.getActiveScenario();

        var event = new Event();

        event.setType(Communication.REQUEST);
        var authority = new Header("authority", request.getAuthority());

        var body = new EventBody();
        body.setContentType(ContentType.APPLICATION_JSON);
        body.setBody(request.getBody());

        event.setBody(body);

        event.setCommunicationId(request.getCommunicationId());

        event.setScenario(scenario);

        event.setSourceService(request.getSource());

        event.setLamportTime(scenario.nextLamportTime());

        event.addHeader(authority);

        scenario.addEvent(event);

        em.persist(event);
        logger.info(String.format("recorded request:{requestId: %s, communicationId: %s}", request.getRequestId(), request.getCommunicationId()));

        return String.valueOf(scenario.getId());
    }

}

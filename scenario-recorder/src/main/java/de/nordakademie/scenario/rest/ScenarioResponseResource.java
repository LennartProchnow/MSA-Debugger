package de.nordakademie.scenario.rest;

import de.nordakademie.scenario.modell.ContentType;
import de.nordakademie.scenario.modell.ResponseRecord;
import de.nordakademie.scenario.modell.scenarioModell.Communication;
import de.nordakademie.scenario.modell.scenarioModell.Event;
import de.nordakademie.scenario.modell.scenarioModell.EventBody;
import de.nordakademie.scenario.modell.scenarioModell.Header;
import de.nordakademie.scenario.services.ScenarioService;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Path("/scenario/response")
public class ScenarioResponseResource {

    @Inject
    ScenarioService scenarioService;

    @Inject
    EntityManager em;

    private static final Logger logger = Logger.getLogger(ScenarioResponseResource.class);

    @POST
    @Transactional
    public String addResponse(@NotNull ResponseRecord response){
        var scenario = scenarioService.getActiveScenario();

        var event = new Event();

        event.setType(Communication.RESPONSE);
        var status = new Header(":status", response.getStatus());
        var contentType = new Header("content-type", response.getContentType());

        var body = new EventBody();
        body.setContentType(ContentType.getEnum(response.getContentType()));
        body.setBody(response.getBody());

        event.setBody(body);

        event.setCommunicationId(response.getCommunicationId());

        event.setLamportTime(scenario.nextLamportTime());

        event.addHeader(status);

        event.addHeader(contentType);

        event.setScenario(scenario);

        scenario.addEvent(event);

        em.persist(event);
        logger.info(String.format("recorded response: {communicationId: %s}", response.getCommunicationId()));

        return String.valueOf(scenario.getId());
    }

}

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

@ApplicationScoped
@Path("/scenario/response")
public class ScenarioResponseResource {

    @Inject
    ScenarioService scenarioService;

    @Inject
    EntityManager em;

    @POST
    @Transactional
    public String addResponse(@NotNull ResponseRecord response){
        System.out.println("Request start");
        System.out.println(response.getCommunicationId());

        var scenario = scenarioService.getActiveScenario();

        var event = new Event();

        event.setType(Communication.RESPONSE);
        var status = new Header(":status", response.getStatus());
        var contentType = new Header("content-type", response.getContentType());
        var communicationId = new Header("x-communication-id", response.getCommunicationId());

        var body = new EventBody();
        //ToDo: das müsste hier dann eigentlich aus den Headern rausgelesen werden,
        // oder halt per Default auf Plain Text gesetzt werden
        body.setContentType(ContentType.getEnum(response.getContentType()));
        body.setBody(response.getBody());

        event.setBody(body);

        event.setLamportTime(scenario.nextLamportTime());

        event.addHeader(status);

        event.addHeader(contentType);

        event.addHeader(communicationId);

        scenario.addEvent(event);

        em.merge(scenario);
        em.flush();

        return String.valueOf(scenario.getId());
    }

}

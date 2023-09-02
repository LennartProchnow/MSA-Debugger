package de.nordakademie.scenario.rest;

import de.nordakademie.scenario.modell.scenarioModell.EventBody;
import de.nordakademie.scenario.modell.scenarioModell.Scenario;
import de.nordakademie.scenario.services.ScenarioService;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

import java.util.List;

/**
 * With this API it should be possible to export a scenaio
 */
@Path("scenario")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ScenarioResource {

    @Inject
    EntityManager em;

    private ScenarioService scenarioService;
    private static final Logger logger = Logger.getLogger(ScenarioResource.class);

    public ScenarioResource(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @POST
    @Path("/record/start")
    @Transactional
    public String startScenario(String name){
        Scenario s = new Scenario();
        s.setName(name);
        em.persist(s);
        scenarioService.setActiveScenario(s);
        logger.info("start recording of Scenario: " + s.getId());
        return String.valueOf(s.getId());
    }

    @POST
    @Path("/record/stop/{scenaioId}")
    public String stopScenario(String scenaioId){
        //ToDo hier müsste jetzt noch nach der Id gefiltert werden
        var scenario = scenarioService.getDeactivateScenario();
        logger.info("stopped recording of Scenario: " + scenario.getId());
        return String.format("Scenario %s is stopped", scenario.getId());
    }

    @GET
    @Path("/{scenarioId}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Scenario getScenario(long scenarioId) {
        var scenario = em.find(Scenario.class, scenarioId);
        return scenario;
    }

    @GET
    public List<Scenario> getAllScenario(){
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("/resolveBody/{bodyId}")
    public EventBody resolveBody(String bodyId){
        throw new UnsupportedOperationException();
    }
}

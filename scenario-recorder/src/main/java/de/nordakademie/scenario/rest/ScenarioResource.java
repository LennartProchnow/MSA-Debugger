package de.nordakademie.scenario.rest;

import de.nordakademie.scenario.modell.scenarioModell.EventBody;
import de.nordakademie.scenario.modell.scenarioModell.Scenario;
import de.nordakademie.scenario.services.ScenarioService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.List;

/**
 * With this API it should be possible to export a scenaio
 */
@Path("scenario")
@ApplicationScoped
public class ScenarioResource {

    @Inject
    EntityManager em;

    private ScenarioService scenarioService;

    public ScenarioResource(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @POST
    @Path("/start")
    @Transactional
    public String startScenario(String name){
        Scenario s = new Scenario();
        s.setName(name);
        em.persist(s);
        em.flush();
        scenarioService.setActiveScenario(s);
        return String.valueOf(s.getId());
    }

    @POST
    @Path("/stop")
    public String stopScenario(){
        var scenario = scenarioService.getDeactivateScenario();
        return String.format("Scenario %s is stopped", scenario.getId());
    }

    @GET
    @Path("/{scenarioId}")
    @Transactional
    public Scenario getScenario(long scenarioId){
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

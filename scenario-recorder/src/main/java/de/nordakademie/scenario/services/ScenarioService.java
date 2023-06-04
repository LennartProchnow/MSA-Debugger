package de.nordakademie.scenario.services;

import de.nordakademie.scenario.modell.scenarioModell.Scenario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;

@ApplicationScoped
public class ScenarioService {

    private Scenario activeScenario;

    public void setActiveScenario(Scenario activeScenario) {
        this.activeScenario = activeScenario;
    }

    public Scenario getActiveScenario(){
        if(activeScenario == null) {
            throw new IllegalStateException("There is no active Scenario");
        }
        return this.activeScenario;
    }

    public Scenario getDeactivateScenario() {
        var deactivated = this.activeScenario;
        this.activeScenario = null;
        return deactivated;
    }
}

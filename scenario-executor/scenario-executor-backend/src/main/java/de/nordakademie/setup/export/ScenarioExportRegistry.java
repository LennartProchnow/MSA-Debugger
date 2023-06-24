package de.nordakademie.setup.export;

import de.nordakademie.setup.export.model.Scenario;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ScenarioExportRegistry {

    private Map<String, Scenario> scenarioRegistry = new HashMap<>();

    public void registerScenario(Scenario scenario) {
        scenarioRegistry.put(scenario.getId(), scenario);
    }

}

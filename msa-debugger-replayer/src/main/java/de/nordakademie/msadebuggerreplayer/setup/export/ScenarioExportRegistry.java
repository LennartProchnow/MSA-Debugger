package de.nordakademie.msadebuggerreplayer.setup.export;

import de.nordakademie.msadebuggerreplayer.setup.export.model.Scenario;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ScenarioExportRegistry {

    private Map<String, Scenario> scenarioRegistry = new HashMap<>();

    public void registerScenario(Scenario scenario) {
        scenarioRegistry.put(scenario.getId(), scenario);
    }

    public Scenario getScenario(String id) {
        return scenarioRegistry.get(id);
    }

}

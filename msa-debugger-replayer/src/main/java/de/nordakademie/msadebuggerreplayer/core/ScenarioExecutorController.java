package de.nordakademie.msadebuggerreplayer.core;

import de.nordakademie.msadebuggerreplayer.setup.export.ScenarioExportRegistry;
import de.nordakademie.msadebuggerreplayer.setup.export.ScenarioExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This controller receives control-actions from outside and starts different phases
 */
@RestController("/replayer/")
public class ScenarioExecutorController {

    @Autowired
    private ScenarioExecutor executor;

    @Autowired
    private ScenarioExportRegistry registry;

    @Autowired
    private ScenarioExportService exportService;

    @PostMapping("/setup/{scenarioId}")
    public String startSetupPhase(@PathVariable(name = "scenarioId") String scenarioId) {
        exportService.exportScenario(scenarioId);

        return "finished Setup Phase";
    }

    @PostMapping("/prepare/{id}")
    public ScenarioQueue startPreparationPhase(@PathVariable(name = "id") String id) {
        var scenario = registry.getScenario(id);
        if(scenario == null) {
            throw new IllegalArgumentException(String.format("A Scenario with the id: %s is not available", id));
        }

        return executor.prepare(scenario);
    }

    @PostMapping("/execute")
    public String startExecutionPhase(@RequestBody ScenarioQueue queue) {
        executor.executeReplay(queue);
        return "finished Execution Phase";
    }

    public void restart(){

    }

}

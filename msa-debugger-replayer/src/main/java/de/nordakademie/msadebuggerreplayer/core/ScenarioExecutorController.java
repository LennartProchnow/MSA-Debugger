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
        // die ScenarioEvents werden überführt in ReplayEvents
        // die Events werden in eine Execution Queue geschrieben
        // die Execution Queue wird dann zurück gegeben
        var scenario = registry.getScenario(id);
        if(scenario == null) {
            throw new IllegalArgumentException(String.format("A Scenario with the id: %s is not available", id));
        }

        return executor.prepare(scenario);
    }

    @PostMapping("/execute")
    public String startExecutionPhase(@RequestBody ScenarioQueue queue) {
        // hier sollte dann als Eingabe Parameter die ExecutionQueue zurückgegeben werden,
        // die entsprechend bearbeitet bearbeitet worden ist
        // anschließend kann die Ausführung gestartet werden
        executor.executeReplay(queue);
        return "finished Execution Phase";
        // Die Steuerung sollte über den Executor passieren
    }

    public void restart(){

    }

}

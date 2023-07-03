package de.nordakademie.msadebuggerreplayer.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller receives control-actions from outside and starts different phases
 */
@RestController("/replayer/")
public class ScenarioExecutorController {

    @Autowired
    private ScenarioExecutor executor;

    @PostMapping("/setup")
    public String startSetupPhase() {
        // hier wird dann das Scenario geladen
        // Die Microservices können sich Connecten
        return "finished Setup Phase";
        // Die Steuerung sollte über den Executor passieren
    }

    @PostMapping("/prepare")
    public String startPreparationPhase() {
        // die ScenarioEvents werden überführt in ReplayEvents
        // die Events werden in eine Execution Queue geschrieben
        // die Execution Queue wird dann zurück gegeben
        executor.prepare();
        return "finished Preparation Phase";
        // Die Steuerung sollte über den Executor passieren
    }

    @PostMapping("/execute")
    public String startExecutionPhase(ScenarioQueue queue) {
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

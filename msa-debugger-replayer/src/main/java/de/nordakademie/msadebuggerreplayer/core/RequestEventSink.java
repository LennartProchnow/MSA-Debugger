package de.nordakademie.msadebuggerreplayer.core;

import de.nordakademie.msadebuggerreplayer.core.model.NextEventExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestEventSink {

    private NextEventExecution exe;

    public void setNextEventExecution(NextEventExecution exe) {
        this.exe = exe;
    }

    public ScenarioQueue getCurrentScenarioQueue() {
        return this.exe.queue();
    }

    public void applyNextEvent() {
        exe.apply();
    }

}

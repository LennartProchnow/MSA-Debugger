package de.nordakademie.msadebuggerreplayer.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestEventSink {

    private ScenarioQueue queue;

    public void setCurrentScenarioQueue(ScenarioQueue queue) {
        this.queue = queue;
    }

    public ScenarioQueue getCurrentScenarioQueue() {
        var result = this.queue;
        this.queue = null;
        return result;
    }

}

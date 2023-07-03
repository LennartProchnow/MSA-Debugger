package de.nordakademie.msadebuggerreplayer.core;

import de.nordakademie.msadebuggerreplayer.core.model.ReplayEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScenarioExecutor {

    private ScenarioQueue queue;

    @Autowired
    private RequestSender sender;

    public void prepare(){
        // Hier dann noch die Queue preparen
    }

    public void executeReplay(ScenarioQueue queue) {
        // das hier finde ich extrem unhübsch
        this.queue = queue;
        this.executeNextEvent(queue);
    }

    public void executeNextEvent(ScenarioQueue queue){
        var nextEvent = queue.getNextEvent();
        nextEvent.apply();
        if(nextEvent.isRequest()) {
            sender.send(nextEvent);
            executeNextEvent(queue);
        }
    }

    public ReplayEvent executeRequest(ReplayEvent event) {
        executeNextEvent(queue);
        return null;
    }
}

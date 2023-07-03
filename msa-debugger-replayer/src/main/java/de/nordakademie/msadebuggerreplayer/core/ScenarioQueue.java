package de.nordakademie.msadebuggerreplayer.core;

import de.nordakademie.msadebuggerreplayer.core.model.ReplayEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class ScenarioQueue {

    ArrayList<ReplayEvent> eventsToReplay = new ArrayList<>();

    ArrayList<ReplayEvent> allEvents = new ArrayList<>();

    public void setupEventsToReplay(List<ReplayEvent> eventsToReplay) {
        this.eventsToReplay.addAll(eventsToReplay);
        this.allEvents.addAll(eventsToReplay);
    }

    public ReplayEvent getNextEvent() {
        if(!eventsToReplay.isEmpty()) {
            return eventsToReplay.remove(0);
        } else {
            throw new ScenarioAlreadyCompletedException("The Scenario is already completed! Please restart the Scenario");
        }
    }

    public boolean isScenarioCompleted () {
        return this.eventsToReplay.isEmpty();
    }

    public List<ReplayEvent> getEventsToReplay() {
        return this.eventsToReplay;
    }
}

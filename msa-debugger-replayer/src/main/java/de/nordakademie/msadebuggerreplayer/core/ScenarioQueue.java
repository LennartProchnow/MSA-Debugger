package de.nordakademie.msadebuggerreplayer.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.nordakademie.msadebuggerreplayer.core.model.ReplayEvent;
import de.nordakademie.msadebuggerreplayer.core.model.ResponseEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class ScenarioQueue {

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "class")
    private List<ReplayEvent> eventsToReplay = new ArrayList<>();

    private List<ResponseEvent> responses = new ArrayList<>();

    @JsonIgnore
    private ReplayEvent currentEvent;

    public ScenarioQueue(List<ReplayEvent> eventsToReplay, List<ResponseEvent> responses){
        this.eventsToReplay = eventsToReplay;
        this.responses = responses;
    }

    public ScenarioQueue() {
        //Default constructor
    }

    public void setupEventsToReplay(List<ReplayEvent> eventsToReplay) {
        setEventsToReplay(eventsToReplay);
    }

    public void setupResponsesToReplay(List<ResponseEvent> responsesToReplay) {
        this.responses = responsesToReplay;
    }

    public List<ResponseEvent> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseEvent> responses) {
        this.responses = responses;
    }

    public ResponseEvent getResponseToRequestId(String requestId) {
        return  responses.stream()
                .filter(response -> requestId.equals(response.getRequestId()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @JsonIgnore
    public ReplayEvent getNextEvent() {
        if(!eventsToReplay.isEmpty()) {
            this.currentEvent = eventsToReplay.remove(0);
            return currentEvent;
        } else {
            throw new ScenarioAlreadyCompletedException("The Scenario is already completed! Please restart the Scenario");
        }
    }

    @JsonIgnore
    public boolean isScenarioCompleted() {
        return this.eventsToReplay.isEmpty();
    }

    public List<ReplayEvent> getEventsToReplay() {
        return this.eventsToReplay;
    }

    public void setEventsToReplay(List<ReplayEvent> eventsToReplay) {
        this.eventsToReplay = eventsToReplay;
    }

    @JsonIgnore
    public ReplayEvent getCurrentEvent() {
        return this.currentEvent;
    }
}

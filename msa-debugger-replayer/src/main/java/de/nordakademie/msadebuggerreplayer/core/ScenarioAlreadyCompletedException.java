package de.nordakademie.msadebuggerreplayer.core;

public class ScenarioAlreadyCompletedException extends RuntimeException {

    public ScenarioAlreadyCompletedException(String message) {
        super(message);
    }

}

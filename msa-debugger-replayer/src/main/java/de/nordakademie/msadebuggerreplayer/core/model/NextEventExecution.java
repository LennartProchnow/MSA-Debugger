package de.nordakademie.msadebuggerreplayer.core.model;

import de.nordakademie.msadebuggerreplayer.core.ScenarioQueue;

import java.util.function.Consumer;

public record NextEventExecution(Consumer<ScenarioQueue> nextExecution, ScenarioQueue queue) {

    public void apply(){
        nextExecution.accept(queue);
    }

}

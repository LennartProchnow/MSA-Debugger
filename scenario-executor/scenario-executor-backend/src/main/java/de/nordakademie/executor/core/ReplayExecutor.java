package de.nordakademie.executor.core;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ReplayExecutor {

    @Inject
    ReplayQueue queue;

    public void prepareReplay(){

    }

    public void executeReplay() {

    }

    public void consumeRequest() {

    }

    public void consumeResponse() {

    }

}

package de.nordakademie.msadebuggerreplayer.core.model;

import de.nordakademie.msadebuggerreplayer.core.RequestEventSink;

public class RequestReceiveEvent extends AbstractReplayEvent {

    private RequestEventSink sink;

    public RequestReceiveEvent(RequestEventSink sink) {
        this.sink = sink;
    }

    public RequestReceiveEvent() {
        //Default Constructor
    }

    @Override
    public void apply(NextEventExecution exe) {
        sink.setNextEventExecution(exe);
    }

}

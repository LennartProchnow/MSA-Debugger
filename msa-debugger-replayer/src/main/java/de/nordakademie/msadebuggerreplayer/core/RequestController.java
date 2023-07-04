package de.nordakademie.msadebuggerreplayer.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RequestController {

    @Autowired
    private RequestEventSink sink;

    @GetMapping
    public String performGetRequest(String str) {
        System.out.println(str);
        var queue = sink.getCurrentScenarioQueue();

        //ToDo entsprechende Response suchen und response zurückgeben

        //der würde hier blockieren, heißt ich komme wahrscheinlich,
        // um eine asynchrone Eventverarbeitung vielleicht garnicht drum herum
        sink.applyNextEvent();

        return "Moin";
    }

}

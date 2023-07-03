package de.nordakademie.msadebuggerreplayer.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RequestController {

    @Autowired
    private RequestEventSink sink;

    @Autowired
    private ScenarioExecutor executor;

    @GetMapping
    public String performGetRequest(String str) {
        System.out.println(str);
        var queue = sink.getCurrentScenarioQueue();

        //ToDo: hier müsste jetzt noch geguckt werden, ob das hier dann auch die richtige Message gewesen ist
        executor.executeNextEvent(queue);

        return "Moin";
    }

}

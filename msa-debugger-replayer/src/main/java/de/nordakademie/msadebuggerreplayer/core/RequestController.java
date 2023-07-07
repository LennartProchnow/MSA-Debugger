package de.nordakademie.msadebuggerreplayer.core;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestController {

    @Autowired
    private RequestEventSink sink;

    @RequestMapping("/**")
    public String performGetRequest(HttpServletRequest request) {
        //aus der Request bekomme ich auch den Path raus, mit dem ich die Request validieren kann
        //System.out.println(request);
        String path = request.getRequestURL().toString();
        var queue = sink.getCurrentScenarioQueue();

        var currentEvent = queue.getCurrentEvent();

        //ToDo entsprechende Response suchen und response zurückgeben
        var currentResponse = queue.getResponseToRequestId(currentEvent.getRequestId());

        //der würde hier blockieren, heißt ich komme wahrscheinlich,
        // um eine asynchrone Eventverarbeitung vielleicht garnicht drum herum
        sink.applyNextEvent();

        return "Moin";
    }

}

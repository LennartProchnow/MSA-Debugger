package de.nordakademie.msadebuggerreplayer.core;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RequestController {

    @Autowired
    private RequestEventSink sink;

    @RequestMapping(value = "/**")
    public ResponseEntity<String> performGetRequest(HttpServletRequest request) {
        var queue = sink.getCurrentScenarioQueue();

        var currentEvent = queue.getCurrentEvent();

        var currentResponse = queue.getResponseToRequestId(currentEvent.getRequestId());

        sink.applyNextEvent();

        return new ResponseEntity<String>(currentResponse.getBody(), HttpStatus.valueOf(Integer.parseInt(currentResponse.getStatus())));
    }


}

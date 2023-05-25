package de.nordakademie.scenario.rest;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/scenario/response")
public class ScenarioResponse {

    @POST
    public String addResponse(String response){
        System.out.println(response);
        return "alles da";
    }

}

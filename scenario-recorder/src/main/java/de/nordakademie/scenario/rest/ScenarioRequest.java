package de.nordakademie.scenario.rest;

import de.nordakademie.scenario.modell.RequestHeader;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Produces(MediaType.TEXT_PLAIN)
@Path("/scenario/request")
public class ScenarioRequest {

    @POST
    @Path("/{traceId}")
    public String addRequest(@PathParam("traceId") String traceId, RequestHeader request){
        System.out.println(traceId);
        System.out.println(request);
        return "alles da";
    }

}

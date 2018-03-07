package com.dolea.backEnd.resources;

import com.dolea.backEnd.dto.ScriptDto;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Path("/script/")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ScriptResource extends BaseBackEndResource {

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createScript(ScriptDto scriptDto) {
        return Response.ok(new HashMap<>()).build();
//        ((LinkedHashMap) ((ArrayList) ((LinkedHashMap) scriptDto.getScript()).get("elementList")).get(0)).get("execution")
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateScript() {
        return Response.ok().build();
    }
}

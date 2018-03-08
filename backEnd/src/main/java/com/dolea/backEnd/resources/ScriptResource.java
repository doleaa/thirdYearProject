package com.dolea.backEnd.resources;

import com.dolea.backEnd.dto.ScriptDto;
import com.dolea.backEnd.service.ActionManager;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/script/")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ScriptResource extends BaseBackEndResource {
    private final ActionManager actionManager;

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createScript(ScriptDto scriptDto) {
        return Response.ok(actionManager.createScript(scriptDto)).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateScript() {
        return Response.ok().build();
    }
}

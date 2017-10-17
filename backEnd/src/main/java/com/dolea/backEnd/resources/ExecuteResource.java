package com.dolea.backEnd.resources;

import com.dolea.backEnd.dto.CommandDto;
import com.dolea.backEnd.service.ActionManager;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("command")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ExecuteResource extends BaseBackEndResource {
    private final ActionManager actionManager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response runCommand(CommandDto commandDto) {
        return Response.ok(actionManager.runCommand(commandDto)).build();
    }
}

package com.dolea.backEnd.resources;

import com.dolea.backEnd.dto.CommandDto;
import com.dolea.backEnd.dto.CommentDto;
import com.dolea.backEnd.service.ActionManager;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PostResource extends BaseBackEndResource {
    private final ActionManager actionManager;

    @POST
    @Path("execution")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response runCommand(CommandDto commandDto) {
        return Response.ok(actionManager.executeCommand(commandDto)).build();
    }

    @PUT
    @Path("execution/{id}/comment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putComment(@PathParam("id") Integer executionId, CommentDto commentDto) {
        return Response.ok(actionManager.putComment(commentDto, executionId)).build();
    }

}

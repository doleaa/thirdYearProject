package com.dolea.backEnd.resources;

import com.dolea.backEnd.service.DashboardService;
import com.dolea.backEnd.service.ScriptService;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_PASSWORD_STRING;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_URL_STRING;
import static com.dolea.backEnd.util.ThirdYearProjectConstants.DB_USERNAME_STRING;

@Path("/")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GetResource extends BaseBackEndResource {
    private final DashboardService dashboardService;
    private final ScriptService scriptService;

    @GET
    @Path("executions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExecutions(@HeaderParam(DB_URL_STRING) String dbUrl,
                                  @HeaderParam(DB_USERNAME_STRING) String username,
                                  @HeaderParam(DB_PASSWORD_STRING) String password) {
        return Response.ok(dashboardService.getAllExecutions(
                ImmutableMap.of(
                        DB_URL_STRING, dbUrl,
                        DB_USERNAME_STRING, username,
                        DB_PASSWORD_STRING, password != null ? password : ""
                )
        )).build();
    }

    @GET
    @Path("scripts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScripts(@HeaderParam(DB_URL_STRING) String dbUrl,
                               @HeaderParam(DB_USERNAME_STRING) String username,
                               @HeaderParam(DB_PASSWORD_STRING) String password) {
        return Response.ok(scriptService.findByCreatedBy(
                ImmutableMap.of(
                        DB_URL_STRING, dbUrl,
                        DB_USERNAME_STRING, username,
                        DB_PASSWORD_STRING, password != null ? password : ""
                )
        )).build();
    }
}

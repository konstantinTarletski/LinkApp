package lv.bank.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("")
@Api
public class StatusInterfaceRest {

    @Inject
    private StatusService statusService;

    private static final Logger LOG = Logger.getLogger(StatusInterfaceRest.class);

    @GET
    @Path("/liveness")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Get service status", tags = "StatusInterfaceRest", notes = "Returns message with code 200. Can be used to check that service is running")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Service status")
    })
    public Response liveness() {
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("message", "It is working");
        return Response.ok(node).build();
    }

    @GET
    @Path("/version")
    @Produces(value = MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Get application version", tags = "StatusInterfaceRest", notes = "Returns application version number, e.g. 1.0.0")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Version number")
    })
    public Response getVersion() {
        String version = statusService.getApplicationVersion();
        LOG.info("Application version: " + version);
        return Response.ok().entity(version).build();
    }
}

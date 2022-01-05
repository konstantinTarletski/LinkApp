package lv.bank.rest;

import io.swagger.annotations.ApiOperation;

import java.io.InputStream;

import javax.ejb.Stateless;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("")
public class DocumentationService {

	@GET
    @Path("swagger.html")
    @Produces(value = MediaType.TEXT_HTML)
	@ApiOperation(value = "Get service documentation", tags = "CardsRestInterface", notes = "Get service documentation in HTML format")
    public InputStream getHtml(@Context ServletContext context) {
	    return context.getResourceAsStream("/WEB-INF/generated/swagger.html");
    }

    @GET
    @Path("swagger.json")
    @Produces(value = MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get service documentation", tags = "CardsRestInterface", notes = "Get service documentation in JSON format")
    public InputStream getJson(@Context ServletContext context) {
        return context.getResourceAsStream("/WEB-INF/generated/swagger.json");
    }

    @GET
    @Path("swagger.yaml")
    @Produces(value = MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Get service documentation", tags = "CardsRestInterface", notes = "Get service documentation in YAML format")
    public InputStream getYaml(@Context ServletContext context) {
        return context.getResourceAsStream("/WEB-INF/generated/swagger.yaml");
    }
    
    @GET
    @Path("favicon.ico")
    @Produces(value = MediaType.TEXT_HTML)
	@ApiOperation(value = "Get service favicon", tags = "CardsRestInterface", notes = "Get service favicon", hidden = true)
    public InputStream getFavicon(@Context ServletContext context) {
        return context.getResourceAsStream("/WEB-INF/favicon.ico");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.servlet;

import epsi.tma.service.ICommandeStatutLogService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Webservice to read log generate by command handling
 *
 * @author cDelage
 */
@Path("/orderlog")
public class CommandeStatutLogWebService {

    private static final Logger LOG = LogManager.getLogger(MonitorWebService.class);

    private ICommandeStatutLogService commandeStatutLogService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/read")
    public Response read(@Context ServletContext servletContext) {
        LOG.info("READ - CommandStatutLogWebService");
        try {
            ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            this.commandeStatutLogService = appContext.getBean(ICommandeStatutLogService.class);
            Map<String, Object> response = new HashMap();
            response.put("LOGS", commandeStatutLogService.logParser());
            return Response.ok(response).status(200)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers","origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        } catch (Exception e) {
            LOG.error("Catch error during read from commandeStatutLogWebService web service : ", e);
            return Response.ok(e).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers","origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        }
    }
}

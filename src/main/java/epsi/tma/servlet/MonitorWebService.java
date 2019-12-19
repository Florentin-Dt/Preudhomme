/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.servlet;

import epsi.tma.service.IDatabaseVersioningService;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * This webservices target is read information for front monitor application
 * Jax-RS is Java API for RESTful Web Services To learn more about it :
 * http://spoonless.github.io/epsi-b3-javaee/javaee_web/jaxrs.html
 *
 * @author Corentin Delage
 */
@Path("/monitor")
public class MonitorWebService {

    private static final Logger LOG = LogManager.getLogger(MonitorWebService.class);

    private IDatabaseVersioningService databaseVersioningService;

    /*
    * updateDatabase if current version is under DatabaseVersioningService version
    */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateDatabase(@Context ServletContext servletContext) {
        try {
            LOG.info("UPDATE DATABASE VERSION - MONITOR WEBSERVICE");
            ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            this.databaseVersioningService = appContext.getBean(IDatabaseVersioningService.class);
            return Response.ok(databaseVersioningService.updateDatabaseVersion()).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers","origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        } catch (Exception e) {
            LOG.error("Catch error during databaseVersioningService running by monitor web service", e);
            return Response.ok(e).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers","origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        }
    }

    /*
    * Method will return information from database and back end application
    */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/read")
    public Response readDatabaseInfo(@Context ServletContext servletContext) {
        try {
            LOG.debug("READ APPLICATION INFORMATION - MONITOR WEBSERVICE");
            ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            this.databaseVersioningService = appContext.getBean(IDatabaseVersioningService.class);
            return Response.ok(databaseVersioningService.readGenericInformation()).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers","origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        } catch (Exception e) {
         //ss   LOG.error("Catch error during databaseVersioningService running by monitor web service", e);
            return Response.ok(e).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers","origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        }
    }
}

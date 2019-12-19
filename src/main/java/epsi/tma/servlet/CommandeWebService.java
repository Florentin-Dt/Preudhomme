/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.servlet;

import epsi.tma.service.ICommandeService;
import epsi.tma.util.StringUtil;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author florentin
 */
@Path("/order")
public class CommandeWebService {

    private static final Logger LOG = LogManager.getLogger(CommandeWebService.class);

    private ICommandeService commandeService;

    /*
     * Create orders
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/simulate")
    public Response simulate(@Context ServletContext servletContext, @QueryParam("idMagasin") int idMagasin) {
        LOG.info("SIMULATE MAGASIN COMMAND - WS STACK");
        try {
            if (idMagasin != 0) {
                ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                this.commandeService = appContext.getBean(ICommandeService.class);
                return Response.ok(commandeService.simulateMagasinCommande(1, idMagasin, 1)).status(200)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
            }

        } catch (Exception e) {
            //   LOG.error("Catch error during databaseVersioningService running by monitor web service", e);
            return Response.ok(e).build();
        }
        return Response.ok("UNSPECIFIED idMagasin TO SIMULATE COMMAND").header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
    }

    /*
     * Update order
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateState(@Context ServletContext servletContext, @QueryParam("idCommande") int idCommande, @QueryParam("idEtat") int idEtat) {
        LOG.info("update command call");
        if (idEtat != 0 && idCommande != 0) {
            try {
                ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                this.commandeService = appContext.getBean(ICommandeService.class);
                String response = commandeService.updateCommand(idEtat, idCommande);
                return Response.ok(response).header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
            } catch (Exception e) {
                //   LOG.error("Catch error during databaseVersioningService running by monitor web service", e);
                return Response.ok(e).header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
            }
        }
        return Response.ok("Unspecify idCommande or idEtat requested").header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
    }

    /*
     * Update all orders from current status to a new status 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateAll")
    public Response updateAllState(@Context ServletContext servletContext, @QueryParam("oldState") int oldState, @QueryParam("newState") int newState) {
        LOG.info("updateAllState command call");
        if (oldState != 0 && newState != 0) {
            try {
                ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                this.commandeService = appContext.getBean(ICommandeService.class);
                String response = commandeService.updateAllCommand(oldState, newState);

                return Response.ok(response).header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
            } catch (Exception e) {
                //   LOG.error("Catch error during databaseVersioningService running by monitor web service", e);
                return Response.ok(e).header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
            }
        }
        return Response.ok("Unspecify old state or new state requested").header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
    }

    /*
     * Delete all orders
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/clear")
    public Response clear(@Context ServletContext servletContext) {
        LOG.info("CLEAR ALL ORDER");
        try {
            ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            this.commandeService = appContext.getBean(ICommandeService.class);
            commandeService.clear();
            return Response.ok("CLEAR").header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        } catch (Exception e) {
            //   LOG.error("Catch error during databaseVersioningService running by monitor web service", e);
            return Response.ok(e).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        }
    }

    /*
     * Delete orders with the final state
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cleartoday")
    public Response clearToday(@Context ServletContext servletContext) {
        LOG.info("UPDATE ALL ORDER");
        try {
            ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            this.commandeService = appContext.getBean(ICommandeService.class);
            commandeService.clearTodayStatus();
            return Response.ok("CLEAR").header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        } catch (Exception e) {
            //   LOG.error("Catch error during databaseVersioningService running by monitor web service", e);
            return Response.ok(e).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        }
    }

     /*
     * Read orders
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/read")
    public Response read(@Context ServletContext servletContext) {
        LOG.info("READ MAGASIN COMMAND - WS STACK");
        try {
            ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            this.commandeService = appContext.getBean(ICommandeService.class);
            return Response.ok(commandeService.readFormater()).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();

        } catch (Exception e) {
            LOG.error("Catch error durring read order in commande web service", e);
            return Response.ok(e).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        }
    }

    /*
     * read orders
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/readbystatus")
    public Response readByStatus(@Context ServletContext servletContext, @QueryParam("status") int status) {
        LOG.info("READ MAGASIN COMMAND - WS STACK");
        if (status != 0) {
            try {
                ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                this.commandeService = appContext.getBean(ICommandeService.class);
                return Response.ok(commandeService.readStatusFormater(status)).header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();

            } catch (Exception e) {
                LOG.error("Catch error durring read order in commande web service", e);
                return Response.ok(e).header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
            }
            
        }
        return Response.ok("INVALID STATUS NAME").header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
    }
}

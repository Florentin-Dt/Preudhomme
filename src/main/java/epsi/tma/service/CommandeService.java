/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.service;

import epsi.tma.dao.ICommandeDAO;
import epsi.tma.entity.Commande;
import epsi.tma.enumClass.ActionEnum;
import epsi.tma.enumClass.EntrepotEnum;
import epsi.tma.enumClass.MagasinEnum;
import epsi.tma.enumClass.ProduitEnum;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author florentin
 */
@Service
public class CommandeService implements ICommandeService {

    private static final Logger LOG = LogManager.getLogger(CommandeService.class);

    @Autowired
    ICommandeDAO commandeDAO;

    @Autowired
    ICommandeStatutLogService commandeStatutLogService;

    /**
     * Simulate orders from magasin
     * @param idProduit
     * @param idMagasin
     * @param idEntrepot
     * @param idEtat
     * @return message of the executed action
     */
    @Override
    public String simulateMagasinCommande(int idProduit, int idMagasin, int idEntrepot) {
        String response = new String();

        try {
            for (int i = 0; i < 10; i++) {
                int idCommande = create(idProduit, idMagasin, idEntrepot, 1);
                LOG.debug("SIMULATE magasin command : {}  - for product : {}", idMagasin, idProduit);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String action = " create order " + idCommande;
                commandeStatutLogService.create(MagasinEnum.getMagasinName(idMagasin), action, idCommande, timestamp, idProduit, 1, "CREATE");
            }

            response = "SIMULATE SUCCESSFULLY";
        } catch (Exception exception) {
            LOG.debug("CATCH EXCEPTION DURING SIMULATE MAGASIN COMMANDE :", exception);
            response = "ERROR DURING SIMULATE MAGASIN COMMANDE";
        }
        return response;
    }

    /**
    * Update state of an order
    * @param newState the new state
    * @param idCommande the order to update
    * @return message of the executed action
    */
    @Override
    public String updateCommand(int newState, int idCommande) {
        String response = new String();
        try {
            response = update(newState, idCommande);
            if (response.compareTo("UPDATE SUCCESSFULLY") == 0) {
                LOG.debug("UPDATE {} order to {} status : {} ", idCommande, newState, response);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String action = "update " + idCommande + " order";
                LOG.debug("TRY TO LOG ACTION : {}", action);
                commandeStatutLogService.create("ENTREPOT 1", action, idCommande, timestamp, 1, newState, "UPDATE");
            } else {
                String action = "fail to update " + idCommande + " order";
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                commandeStatutLogService.create("ENTREPOT 1", action, idCommande, timestamp, 1, newState, "ERROR");
            }
        } catch (Exception exception) {
            response = exception.getLocalizedMessage();
        }

        return response;
    }
    
    /**
     * Update all orders with a particular state
     * @param oldState the current state to update
     * @param newState the new state
     * @return message of the executed action
     */
    @Override
    public String updateAllCommand(int oldState, int newState) {
        String response = new String();
        List<Commande> commandes = new ArrayList();
        
        try {
            commandes = commandeDAO.readByStatus(oldState);
            try {
                response = updateAll(oldState, newState);
                if (response.compareTo("UPDATE SUCCESSFULLY") == 0) {
                    LOG.debug("UPDATE order status {} to {} status : {} ", oldState, newState, response);
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String action = "update " + oldState + " order";
                    LOG.debug("TRY TO LOG ACTION : {}", action);
                    commandeStatutLogService.create("ENTREPOT 1", action, 0, timestamp, 1, newState, "UPDATE_ALL");
                } else {
                    String action = "fail to update all orders with "+ oldState +" status to "+ newState;
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    commandeStatutLogService.create("ENTREPOT 1", action, 0, timestamp, 1, newState, "ERROR_ALL");
                }
            } catch (Exception e) {
                response = e.getLocalizedMessage();
            }
        } catch (Exception e){

        }

        return response;
    }

    /**
     * Allow to read orders data
     * @return list which contains informations about orders
     */
    @Override
    public List<Map<String, Object>> readFormater() {
        List<Commande> result = read();
        List<Map<String, Object>> response = new ArrayList();
        for (Commande i : result) {
            Map<String, Object> resp = new HashMap();
            resp.put("MAGASIN", MagasinEnum.getMagasinName(i.getIdMagasin()));
            resp.put("PRODUIT", ProduitEnum.getProduitName(i.getIdProduit()));
            resp.put("ENTREPOT", EntrepotEnum.getEntrepotName(i.getIdProduit()));
            resp.put("IDETAT", i.getIdEtat());
            resp.put("IDCOMMANDE", i.getIdCommande());
            response.add(resp);
        }
        return response;
    }
    
    @Override
    public List<Map<String, Object>> readStatusFormater(int status) {
        List<Commande> result = readByStatus(status);
        List<Map<String, Object>> response = new ArrayList();
        for (Commande i : result) {
            Map<String, Object> resp = new HashMap();
            resp.put("MAGASIN", MagasinEnum.getMagasinName(i.getIdMagasin()));
            resp.put("PRODUIT", ProduitEnum.getProduitName(i.getIdProduit()));
            resp.put("ENTREPOT", EntrepotEnum.getEntrepotName(i.getIdProduit()));
            resp.put("IDETAT", i.getIdEtat());
            resp.put("IDCOMMANDE", i.getIdCommande());
            response.add(resp);
        }
        return response;
    }

    /**
     * Create orders
     * @param pIdProduit
     * @param pIdMagasin
     * @param pIdEntrepot
     * @param pIdEtat
     * @return id of the new order
     */
    @Override
    public int create(int pIdProduit, int pIdMagasin, int pIdEntrepot, int pIdEtat) {
        return commandeDAO.create(pIdProduit, pIdMagasin, pIdEntrepot, pIdEtat);
    }

    /**
     * Service function to update state of an order
     * @param newState the new state
     * @param idCommand the order to update
     * @return message of the executed action
     */
    @Override
    public String update(int newState, int idCommand) {
        return commandeDAO.update(newState, idCommand);
    }
    
    /**
     * Service function to update state of all orders with a particular state 
     * @param oldState the current state
     * @param newState the new state
     * @return message of the executed action
     */
    @Override
    public String updateAll(int oldState, int newState) {
        return commandeDAO.updateAll(oldState, newState);
    }

    /**
     * Service function to read a list of orders
     * @return list of order object 
     */
    @Override
    public List<Commande> read() {
        return commandeDAO.read();
    }
    
    /**
     * Service method to delete all orders
     */
    @Override
    public void clear() {
        commandeDAO.clear();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        commandeStatutLogService.create("ADMIN", "Clear all order", 0, timestamp, 0, 0, "DELETE");
    }

    /**
     * Service method to delete all orders with the final state (4)
     */
    @Override
    public void clearTodayStatus() {
        commandeDAO.clearTodayStatus();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        commandeStatutLogService.create("ADMIN", "Clear status 4 order", 0, timestamp, 0, 0, "DELETE");

    }
    
    @Override
    public List<Commande> readByStatus(int status){
        return commandeDAO.readByStatus(status);
    }
}

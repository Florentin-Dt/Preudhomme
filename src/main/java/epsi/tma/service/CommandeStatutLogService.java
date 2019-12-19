package epsi.tma.service;

import epsi.tma.dao.ICommandeStatutLogDAO;
import epsi.tma.entity.CommandeStatutLog;
import epsi.tma.enumClass.ActionEnum;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Service to read and create log from command handling
 *
 * @author cDelage
 */
@Service
public class CommandeStatutLogService implements ICommandeStatutLogService {

    private static final Logger LOG = LogManager.getLogger(CommandeStatutLogService.class);

    @Autowired
    private ICommandeStatutLogDAO commandeStatutLogDAO;

   /**
    * Service function to parse log from object to string like 'HH:MM:DD [TYPE] USER make ACTION for Order...'
    * @return list of string which contains all actions log read from the database
    */
    @Override
    public List<String> logParser() {
        List<String> response = new ArrayList();
        List<CommandeStatutLog> logs = read();
        String logger;
        for (CommandeStatutLog log : logs) {
            if (log.getType().compareTo("DELETE") == 0) {
                logger = log.getHorodatage().toString()+ " [" + log.getType() + "] " + log.getEmmeteur() + " "+ log.getAction();
            } else if (log.getType().compareTo("UPDATE_ALL") == 0 || log.getType().compareTo("ERROR_ALL") == 0) {
                logger = log.getHorodatage().toString()+ " [" + log.getType() + "] " + log.getEmmeteur() + " " + log.getAction() + " orders to status " + log.getIdEtat();
            } else {
                logger = log.getHorodatage().toString() + " [" + log.getType() + "] " + log.getEmmeteur() + " " + log.getType() + " order " + log.getIdCommande() + " to status " + log.getIdEtat()+ " " +ActionEnum.getActionLibelle(log.getIdEtat());
            }
            response.add(logger);
        }
        return response;
    }

    /**
     * Service function to read log from database
     * @return list of CommandeStatutLog
     */
    @Override
    public List<CommandeStatutLog> read() {
        return commandeStatutLogDAO.read();
    }

    /**
     * Service function to create log
     * @param emmeteur the author of the action
     * @param action the action logged
     * @param idCommande the order modified
     * @param horodatage the date and time of the execution
     * @param idProduit the product use in the order
     * @param idEtat the state of the order
     * @param type the kind of action logged
     * @return message of the executed action
     */
    @Override
    public String create(String emmeteur, String action, int idCommande, Timestamp horodatage, int idProduit, int idEtat, String type) {
        return commandeStatutLogDAO.create(emmeteur, action, idCommande, horodatage, idProduit, idEtat, type);
    }
}

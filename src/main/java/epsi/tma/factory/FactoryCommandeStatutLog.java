/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.factory;

import epsi.tma.entity.CommandeStatutLog;
import java.sql.Timestamp;
import org.springframework.stereotype.Service;

/**
 *
 * @author utilisateur
 */
@Service
public class FactoryCommandeStatutLog implements IFactoryCommandeStatutLog {

    @Override
    public CommandeStatutLog create(int idLog, int idCommande, int idEtat, Timestamp horodatage, String emeteur, String action, String type) {

        CommandeStatutLog commandeStatutLog = new CommandeStatutLog();
        commandeStatutLog.setIdCommande(idCommande);
        commandeStatutLog.setIdLog(idLog);
        commandeStatutLog.setIdEtat(idEtat);
        commandeStatutLog.setHorodatage(horodatage);
        commandeStatutLog.setEmmeteur(emeteur);
        commandeStatutLog.setAction(action);
        commandeStatutLog.setType(type);
        return commandeStatutLog;
    }
}

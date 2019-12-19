/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.factory;

import epsi.tma.entity.CommandeStatutLog;
import java.sql.Timestamp;

/**
 *
 * @author utilisateur
 */
public interface IFactoryCommandeStatutLog {
    
    public CommandeStatutLog create(int idLog, int idCommande, int idEtat, Timestamp horodatage, String emeteur, String action, String type);
}

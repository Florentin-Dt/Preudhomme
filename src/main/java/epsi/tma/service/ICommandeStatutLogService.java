/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.service;

import epsi.tma.entity.CommandeStatutLog;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 *
 * @author utilisateur
 */
public interface ICommandeStatutLogService {
    
    public List<CommandeStatutLog> read();
    
    public List<String> logParser();
    
    public String create(String emmeteur, String action, int idCommande, Timestamp horodatage, int idProduit, int idEtat, String type);
}

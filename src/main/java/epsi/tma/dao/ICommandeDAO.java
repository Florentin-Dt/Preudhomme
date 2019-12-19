/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.dao;

import epsi.tma.entity.Commande;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author florentin
 */
public interface ICommandeDAO {

    public int create(int idProduit, int idMagasin, int idEntrepot, int idEtat);

    public String update(int newState, int idCommand);
    
    public String updateAll(int oldState, int newState);

    public List<Commande> read();
    
    public List<Commande> readByStatus(int status);

    public void clear();
    
    public void clearTodayStatus();
    
    public Commande loadFromCommandResultSet(ResultSet rs) throws SQLException;
    
}

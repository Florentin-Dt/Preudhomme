/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.service;

import epsi.tma.entity.DatabaseVersioning;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author utilisateur
 */

public interface IDatabaseVersioningService {
    
    public ArrayList<String> getSQLScript();
    
    public String updateSingleVersion(String SQLString);
    
    public DatabaseVersioning findMyVersionByKey(String key);
    
    public String updateDatabaseVersion();
    
    public boolean isDatabaseUptodate();
    
    public void updateNumberVersion(Integer version);
    
    public Map<String, Object> readDatabaseInformation();
    
    public Map<String, Object> readGenericInformation();
}

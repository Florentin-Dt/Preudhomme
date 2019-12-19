/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.dao;

import epsi.tma.entity.DatabaseVersioning;
import java.util.Map;

/**
 *
 * @author cDelage
 */
public interface IDatabaseVersioningDAO {
    
    public DatabaseVersioning findMyVersionByKey(String key);
    public String updateSingleVersion(String SQLString);
    public void updateNumberVersion(Integer version);
    public Map<String,Object> readDatabaseInformation();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.factory;

import epsi.tma.entity.DatabaseVersioning;
import org.springframework.stereotype.Service;

/**
 *
 * @author cDelage
 */
@Service
public class FactoryDatabaseVersioning implements IFactoryDatabaseVersioning {

    @Override
    public DatabaseVersioning create(String key, Integer value) {
        DatabaseVersioning databaseVersioning = new DatabaseVersioning();
        databaseVersioning.setKey(key);
        databaseVersioning.setVersion(value);
        return databaseVersioning;
    }
}

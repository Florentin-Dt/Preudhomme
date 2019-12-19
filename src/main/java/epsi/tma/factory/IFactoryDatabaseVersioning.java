/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.factory;

import epsi.tma.entity.DatabaseVersioning;

/**
 *
 * @author cDelage
 */
public interface IFactoryDatabaseVersioning {

    public DatabaseVersioning create(String key, Integer value);
}

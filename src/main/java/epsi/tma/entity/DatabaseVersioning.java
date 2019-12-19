/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.entity;

/**
 *
 * @author cDelage
 */
public class DatabaseVersioning {

    private String key;
    private Integer version;

    public String getKey() {
        return key;
    }

    public Integer getVersion() {
        return version;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}

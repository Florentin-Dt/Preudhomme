/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.enumClass;

/**
 *
 * @author utilisateur
 */
public enum EntrepotEnum {

    ENTREPOT_1(1, "ENTREPOT_1"),
    NOT_VALID(0, "NOT_VALID ENTREPOT");

    private int idEntrepot;
    private String nomEntrepot;

    private EntrepotEnum(int idEntrepot, String nomEntrepot) {
        this.idEntrepot = idEntrepot;
        this.nomEntrepot = nomEntrepot;
    }

    public static int getEntrepotID(String entrepotName) {
        for (EntrepotEnum en : values()) {
            if (en.getNomEntrepot().compareTo(entrepotName) == 0) {
                return en.getIdEntrepot();
            }
        }
        return NOT_VALID.getIdEntrepot();
    }

    public static String getEntrepotName(int entrepotId) {
        for (EntrepotEnum en : values()) {
            if (en.getIdEntrepot() == entrepotId) {
                return en.getNomEntrepot();
            }
        }
        return NOT_VALID.getNomEntrepot();
    }

    public int getIdEntrepot() {
        return idEntrepot;
    }

    public void setIdEntrepot(int idEntrepot) {
        this.idEntrepot = idEntrepot;
    }

    public String getNomEntrepot() {
        return nomEntrepot;
    }

    public void setNomEntrepot(String nomEntrepot) {
        this.nomEntrepot = nomEntrepot;
    }

}

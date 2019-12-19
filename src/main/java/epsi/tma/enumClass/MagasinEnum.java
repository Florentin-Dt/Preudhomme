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
public enum MagasinEnum {

    MAGASIN_1(1, "Magasin_1"),
    MAGASIN_2(2, "Magasin_2"),
    MAGASIN_3(3, "Magasin_3"),
    NOT_VALID(0, "NOT VALID MAGASIN");

    private int idMagasin;
    private String nomMagasin;

    public static int getMagasinID(String magasinName) {
        for (MagasinEnum en : values()) {
            if (en.getNomMagasin().compareTo(magasinName) == 0) {
                return en.getIdMagasin();
            }
        }
        return NOT_VALID.getIdMagasin();
    }
    
    public static String getMagasinName(int magasinId){
        for (MagasinEnum en : values()){
            if(en.getIdMagasin() == magasinId){
                return en.getNomMagasin();
            }
        }
        return NOT_VALID.getNomMagasin();
    }

    private MagasinEnum(int idMagasin, String nomMagasin) {
        this.idMagasin = idMagasin;
        this.nomMagasin = nomMagasin;
    }

    public int getIdMagasin() {
        return idMagasin;
    }

    public void setIdMagasin(int idMagasin) {
        this.idMagasin = idMagasin;
    }

    public String getNomMagasin() {
        return nomMagasin;
    }

    public void setNomMagasin(String nomMagasin) {
        this.nomMagasin = nomMagasin;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.entity;

/**
 * Class Commande
 * @author florentin
 */
public class Commande {
    private int idCommande;
    private int idProduit;
    private int idMagasin;
    private int idEntrepot;
    private int idEtat;

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getIdMagasin() {
        return idMagasin;
    }

    public void setIdMagasin(int idMagasin) {
        this.idMagasin = idMagasin;
    }

    public int getIdEntrepot() {
        return idEntrepot;
    }

    public void setIdEntrepot(int idEntrepot) {
        this.idEntrepot = idEntrepot;
    }

    public int getIdEtat() {
        return idEtat;
    }

    public void setIdEtat(int idEtat) {
        this.idEtat = idEtat;
    }


}

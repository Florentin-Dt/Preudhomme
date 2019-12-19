/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.factory;
import epsi.tma.entity.Commande;

/**
 *
 * @author florentin
 */
public interface IFactoryCommande {
    public Commande create(int pIdCommande, int pIdProduit, int pIdMagasin, int pIdEntrepot, int pIdEtat);
}

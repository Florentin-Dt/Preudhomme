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
public enum ProduitEnum {

    PRODUIT_0(1, "PRODUIT_0"),
    PRODUIT_1(1, "PRODUIT_1"),
    PRODUIT_2(2, "PRODUIT_2"),
    PRODUIT_3(3, "PRODUIT_3"),
    PRODUIT_4(4, "PRODUIT_4"),
    NOT_VALID(1000,"NOT_VALID PRODUCT");

    private int id;
    private String value;
    
    public static int getProductID(String productName) {
        for (ProduitEnum en : values()) {
            if (en.getValue().compareTo(productName) == 0) {
                return en.getId();
            }
        }
        return NOT_VALID.getId();
    }
    
    public static String getProduitName(int productID){
        for (ProduitEnum en : values()){
            if(en.getId()== productID){
                return en.getValue();
            }
        }
        return NOT_VALID.getValue();
    }

    private ProduitEnum(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

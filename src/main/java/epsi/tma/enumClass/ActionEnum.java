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
public enum ActionEnum {
    ACTION_1(1,"Demandé"),
    ACTION_2(2,"En préparation"),
    ACTION_3(3,"Finie"),
    ACTION_4(4,"Du jour"),
    NOT_VALID(0,"NOT VALID ACTION");
    
    private int idAction;
    private String actionLibelle;
    

    public static String getActionLibelle(int actionId) {
        for (ActionEnum en : values()) {
            if (en.getIdAction()== actionId) {
                return en.getActionLibelle();
            }
        }
        return NOT_VALID.getActionLibelle();
    }

    private ActionEnum(int idAction, String action) {
        this.idAction = idAction;
        this.actionLibelle = action;
    }

    public int getIdAction() {
        return idAction;
    }

    public void setIdAction(int idAction) {
        this.idAction = idAction;
    }

    public String getActionLibelle() {
        return actionLibelle;
    }

    public void setActionLibelle(String action) {
        this.actionLibelle = action;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.util;

/**
 *
 * @author utilisateur
 */
public class StringUtil {

    public static final String NULL = "null";

    public static boolean isNullOrEmpty(String str) {
        if ((str == null) || (str.trim().equalsIgnoreCase(""))) {
            return true;
        }
        return false;
    }
}

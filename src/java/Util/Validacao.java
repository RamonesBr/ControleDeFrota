/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author Ramon Cordeiro
 */
public class Validacao {
//classe de tratamento de sql injection, xss injection efetuado por regex
      public static String Formulario(String s) {

        s = s.replaceAll("(?i)<script.*?>.*?</script.*?>", "EntradaBloqueada");
        s = s.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "EntradaBloqueada");
        s = s.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "EntradaBloqueada");
        s = s.replaceAll("DELETE FROM", "EntradaBloqueada");
        s = s.replaceAll("\n", "");

        return s;
    
    }
}

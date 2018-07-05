/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.text.SimpleDateFormat;

/**
 *
 * @author acesso
 */
public class FormataHoraData {
    private final String data = "YYYY-MM-dd";
    private final String hora = "H:mm:ss";

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }
    
    public String formataHora(){
        String horaf;
        
        java.util.Date agora = new java.util.Date();
        SimpleDateFormat f = new SimpleDateFormat(getHora());
        horaf = f.format(agora);
        
        return horaf;
    }
    
    public String formataData(){
        String dataf;
        
        java.util.Date agora = new java.util.Date();
        SimpleDateFormat f = new SimpleDateFormat(getData());
        dataf = f.format(agora);
        
        return dataf;
        
    }
}


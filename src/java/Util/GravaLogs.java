/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author acesso
 */
public class GravaLogs {
    
    public static void registraLog(String s) throws IOException{
        FormataHoraData data = new FormataHoraData();
       /*
        File fw = new File("/home/acesso/Documentos/VEPAN/ControleDeFrota/ControleDeFrota/log/logs.txt");
        if(!fw.exists()){
            fw.createNewFile();
        }
        
        FileWriter f = new FileWriter(fw, true);
        
        PrintWriter pw = new PrintWriter(f);
        
        pw.printf("As " + data.formataHora() + " de " + data.formataData() + "::-> " + s + "%n");
        pw.close();
        
        */  
        
    }
    
}

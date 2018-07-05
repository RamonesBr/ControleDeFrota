/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexao;

import Util.GravaLogs;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author acesso
 */
public class Conectarbanco {
    

    static final String URL = "jdbc:mysql://localhost/";
    static final String BANCO = "controle_frota_vepan";
    static final String USUARIO = "phpmyadmin";
    static final String SENHA = "VE97531PAN";

    public static Connection Banco() throws SQLException, IOException {
        Connection cnx = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx = (Connection) DriverManager.getConnection(URL + BANCO, USUARIO, SENHA);
        } catch (ClassNotFoundException erro) {
            GravaLogs.registraLog("Erro1 Conexão: "+erro.getMessage());
        } catch (SQLException erro2) {
            GravaLogs.registraLog("Erro na conexao ao banco: " + erro2.getMessage());
        }

        return cnx;
    }


    
}

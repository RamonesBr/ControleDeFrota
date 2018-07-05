/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexao.Conectarbanco;
import Modelo.CheckList;
import Util.GravaLogs;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author acesso
 */
public class CheckListDAO {
    
    public static CheckList cadastraCheckList(CheckList c) throws IOException {
        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("INSERT INTO check_list "
                    + "(avaria_parachoque, avaria_pintura, avaria_pneu, avaria_vidro) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            sql.setString(1, c.getAvaria_parachoque());
            sql.setString(2, c.getAvaria_pintura());
            sql.setString(3, c.getAvaria_pneu());
            sql.setString(4, c.getAvaria_vidro());
            

            sql.executeUpdate();

            ResultSet r = sql.getGeneratedKeys();

            if (r.next()) {
                c.setId(r.getInt(1));
            }

            GravaLogs.registraLog("Novo Check_list criado. ID: : " + c.getId());
        } catch (Exception ex) {
            GravaLogs.registraLog("Erro no cadastro do Viagem: " + ex.getMessage());
        }

        return c;

    }
    
}

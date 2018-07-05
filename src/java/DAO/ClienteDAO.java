/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexao.Conectarbanco;
import Modelo.Cliente;
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
public class ClienteDAO {
    public static Cliente cadastrarCliente(Cliente c) throws IOException {
        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("INSERT INTO cliente "
                    + "(nome, telefone, endereco) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            sql.setString(1, c.getNome());
            sql.setString(2, c.getTelefone());
            sql.setInt(3, c.getEnderecos().getId());
            
            sql.executeUpdate();

            ResultSet r = sql.getGeneratedKeys();

            if (r.next()) {
                c.setId(r.getInt(1));
            }

            GravaLogs.registraLog("Novo Cadastro de Cliente. ID: : " + c.getId());
        } catch (Exception e) {
            GravaLogs.registraLog("Erro no cadastro do Cliente: " + e.getMessage());
        }

        return c;
    }
    
}

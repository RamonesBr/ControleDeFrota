/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexao.Conectarbanco;
import Modelo.Cliente;
import Modelo.Endereco;
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
public class EnderecoDAO {
    public static Endereco cadastrarEndereco(Endereco end) throws IOException {
        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("INSERT INTO enderecos "
                    + "(cep,rua, numero, bairro,cidade, estado) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            sql.setString(1, end.getCep());
            sql.setString(2, end.getRua());
            sql.setInt(3, end.getNumero() ); 
            sql.setString(4, end.getBairro());
            sql.setString(5, end.getCidade());
            sql.setString(6, end.getEstado());
            
            
            
            sql.executeUpdate();

            ResultSet r = sql.getGeneratedKeys();

            if (r.next()) {
                end.setId(r.getInt(1));
            }

            GravaLogs.registraLog("Novo Cadastro de Endereco. ID: : " + end.getId());
        } catch (Exception e) {
            GravaLogs.registraLog("Erro no cadastro do Cliente: " + e.getMessage());
        }

        return end;
    }
    
}

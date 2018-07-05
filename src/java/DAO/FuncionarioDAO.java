/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexao.Conectarbanco;
import Modelo.Funcionario;
import Modelo.PerfilDeAcesso;
import Util.GravaLogs;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acesso
 */
public class FuncionarioDAO {

    public static Funcionario cadastraFuncionario(Funcionario f) throws IOException {
        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("INSERT INTO funcionario "
                    + "(cpf, cnh, nome, data_nasc, departamento, login, senha, tipo, email, status) VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS); 
            sql.setString(1, f.getCpf());
            sql.setString(2, f.getCnh());
            sql.setString(3, f.getNome());
            sql.setString(4, f.getData_nasc());
            sql.setString(5, f.getDepartamento());
            sql.setString(6, f.getLogin());
            sql.setString(7, f.getSenha());
            sql.setString(8, f.getTipo().toString());
            sql.setString(9, f.getEmail());
            sql.setString(10,f.getStatus());

            sql.executeUpdate();

            ResultSet r = sql.getGeneratedKeys();

            if (r.next()) {
                f.setId(r.getInt(1));
            }

            GravaLogs.registraLog("Novo Cadastro de Funcionario. ID: : " + f.getId());
        } catch (Exception e) {
            GravaLogs.registraLog("Erro no cadastro do Funcionario: " + e.getMessage());
        }

        return f;
    }
    
    public Funcionario autenticaFuncionario(Funcionario funcionario) throws IOException {
        Funcionario funcionarioAutenticado = null;

        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsFuncionario = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("SELECT *FROM funcionario WHERE login=? AND senha=? AND status = 'ATIVO'");
            pstmt.setString(1, funcionario.getLogin());
            pstmt.setString(2, funcionario.getSenha());
            rsFuncionario = pstmt.executeQuery();

            if (rsFuncionario.next()) {
                funcionarioAutenticado = new Funcionario();
                funcionarioAutenticado.setLogin(rsFuncionario.getString("login"));
                funcionarioAutenticado.setSenha(rsFuncionario.getString("senha"));
                funcionarioAutenticado.setTipo(PerfilDeAcesso.valueOf(rsFuncionario.getString("tipo")));
                funcionarioAutenticado.setEmail(rsFuncionario.getString("email"));
                funcionarioAutenticado.setNome(rsFuncionario.getString("nome"));
                funcionarioAutenticado.setId(rsFuncionario.getInt("id"));
                funcionarioAutenticado.setDepartamento(rsFuncionario.getString("departamento"));
                
                
            }
            
            GravaLogs.registraLog("Usuario: "+ funcionarioAutenticado.getLogin()+ " autenticado com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro de autenticação " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return funcionarioAutenticado;

    }
    
    public static List<Funcionario> FuncionarioResponsavel() throws IOException {
        Funcionario funcionarioAutenticado = null;
        List<Funcionario> l = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsFuncionario = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("SELECT *FROM funcionario where status='ATIVO' order by nome");
            rsFuncionario = pstmt.executeQuery();

            while (rsFuncionario.next()) {
                funcionarioAutenticado = new Funcionario();
                funcionarioAutenticado.setLogin(rsFuncionario.getString("login"));
                funcionarioAutenticado.setSenha(rsFuncionario.getString("senha"));
                funcionarioAutenticado.setTipo(PerfilDeAcesso.valueOf(rsFuncionario.getString("tipo")));
                funcionarioAutenticado.setEmail(rsFuncionario.getString("email"));
                funcionarioAutenticado.setNome(rsFuncionario.getString("nome"));
                funcionarioAutenticado.setId(rsFuncionario.getInt("id"));
                
                
                l.add(funcionarioAutenticado);
            }
            
            GravaLogs.registraLog("Usuario: "+ funcionarioAutenticado.getLogin()+ " recuperado com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro de autenticação " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return l;

    }
    public static List<Funcionario> FuncionarioResponsavelDepto(Funcionario funcionario) throws IOException {
        Funcionario funcionarioAutenticado = null;
        List<Funcionario> l = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsFuncionario = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("SELECT *FROM funcionario where status='ATIVO'and departamento = ? order by nome");
            pstmt.setString(1, funcionario.getDepartamento());
            rsFuncionario = pstmt.executeQuery();

            while (rsFuncionario.next()) {
                funcionarioAutenticado = new Funcionario();
                funcionarioAutenticado.setLogin(rsFuncionario.getString("login"));
                funcionarioAutenticado.setSenha(rsFuncionario.getString("senha"));
                funcionarioAutenticado.setTipo(PerfilDeAcesso.valueOf(rsFuncionario.getString("tipo")));
                funcionarioAutenticado.setEmail(rsFuncionario.getString("email"));
                funcionarioAutenticado.setNome(rsFuncionario.getString("nome"));
                funcionarioAutenticado.setId(rsFuncionario.getInt("id"));
                
                
                l.add(funcionarioAutenticado);
            }
            
            GravaLogs.registraLog("Usuario: "+ funcionarioAutenticado.getLogin()+ " recuperado com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro de autenticação " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return l;

    }
    
    public static Funcionario idResponsavelNome(Funcionario funcionario) throws IOException {
        

        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsFuncionario = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("SELECT id, departamento FROM funcionario WHERE nome=?");
            pstmt.setString(1, funcionario.getNome());
            
            rsFuncionario = pstmt.executeQuery();

            if (rsFuncionario.next()) {
               
                funcionario.setId(rsFuncionario.getInt("id"));
                funcionario.setDepartamento(rsFuncionario.getString("departamento"));
                
                
            }
            
            GravaLogs.registraLog("Id numero "+funcionario.getId() + "recuperado com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao recuperar id de funcionario " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return funcionario;

    }
    
    public static List<Funcionario> todosFuncionarios() throws IOException {
        Funcionario funcionarioAutenticado = null;
        List<Funcionario> l = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsFuncionario = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("SELECT *FROM funcionario where nome not in('LIVRE') order by nome");
            rsFuncionario = pstmt.executeQuery();

            while (rsFuncionario.next()) {
                funcionarioAutenticado = new Funcionario();
                funcionarioAutenticado.setLogin(rsFuncionario.getString("login"));
                funcionarioAutenticado.setSenha(rsFuncionario.getString("senha"));
                funcionarioAutenticado.setTipo(PerfilDeAcesso.valueOf(rsFuncionario.getString("tipo")));
                funcionarioAutenticado.setEmail(rsFuncionario.getString("email"));
                funcionarioAutenticado.setNome(rsFuncionario.getString("nome"));
                funcionarioAutenticado.setId(rsFuncionario.getInt("id"));
                funcionarioAutenticado.setCpf(rsFuncionario.getString("cpf"));
                funcionarioAutenticado.setCnh(rsFuncionario.getString("cnh"));
                funcionarioAutenticado.setDepartamento(rsFuncionario.getString("departamento"));
                funcionarioAutenticado.setStatus(rsFuncionario.getString("status"));
                
                
                
                l.add(funcionarioAutenticado);
            }
            
            GravaLogs.registraLog("Todos os funcionarios foram recuperados com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro no metodo que recupera todos os funcionarios " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return l;

    }
    
    public static void alteraFuncionario(Funcionario f) throws IOException {
        
        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("update funcionario set cpf= ?, cnh= ?, nome= ?, data_nasc = ?, departamento= ?, login= ?, senha= ?, tipo= ?, email= ?, status = ? where id= ?");
                   
            sql.setString(1, f.getCpf());
            sql.setString(2, f.getCnh());
            sql.setString(3, f.getNome());
            sql.setString(4, f.getData_nasc());
            sql.setString(5, f.getDepartamento());
            sql.setString(6, f.getLogin());
            sql.setString(7, f.getSenha());
            sql.setString(8, f.getTipo().toString());
            sql.setString(9, f.getEmail());
            sql.setString(10,f.getStatus());
            sql.setInt(11, f.getId());
            sql.executeUpdate();

            

            GravaLogs.registraLog("Funcionario " + f.getNome() + "alterado com sucesso");
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao alterar Funcionario: " + e.getMessage());
        }

        
    
        
    }
    
    public static void excluirFuncionario(Funcionario f) throws IOException, SQLException{
        
        try (Connection cnx = Conectarbanco.Banco()) {
            
            PreparedStatement sql = cnx.prepareStatement("DELETE FROM funcionario WHERE id= ?");
            
            sql.setInt(1, f.getId());
            
            sql.execute();
            
            GravaLogs.registraLog("Funcionario deletado com sucesso");
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao excluir funcionario" +e.getMessage());
        }
        
        
    }
    
     public static Funcionario funcionarioEdicao(Funcionario funcionario) throws IOException {
        

        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsFuncionario = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("SELECT * FROM funcionario WHERE id=?");
            pstmt.setInt(1, funcionario.getId());
            
            rsFuncionario = pstmt.executeQuery();

            if (rsFuncionario.next()) {
               
                funcionario.setId(rsFuncionario.getInt("id"));
                funcionario.setCpf(rsFuncionario.getString("cpf"));
                funcionario.setCnh(rsFuncionario.getString("cnh"));
                funcionario.setNome(rsFuncionario.getString("nome"));
                funcionario.setData_nasc(rsFuncionario.getString("data_nasc"));
                funcionario.setDepartamento(rsFuncionario.getString("departamento"));
                funcionario.setLogin(rsFuncionario.getString("login"));
                funcionario.setSenha(rsFuncionario.getString("senha"));
                funcionario.setTipo(PerfilDeAcesso.valueOf(rsFuncionario.getString("tipo")));
                funcionario.setEmail(rsFuncionario.getString("email"));
                funcionario.setStatus(rsFuncionario.getString("status"));
                                                        
                
                
                
                
            }
            
            GravaLogs.registraLog("Id numero "+funcionario.getId() + "recuperado com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao recuperar id de funcionario " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return funcionario;

    }
     
     public static List<Funcionario> verificaAdm() throws IOException {
        Funcionario funcionarioAutenticado = null;
        List<Funcionario> l = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsFuncionario = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select * from funcionario where tipo = 'ADMINISTRADOR' ");
            rsFuncionario = pstmt.executeQuery();

            while (rsFuncionario.next()) {
                funcionarioAutenticado = new Funcionario();
                
                funcionarioAutenticado.setNome(rsFuncionario.getString("nome"));
                funcionarioAutenticado.setEmail(rsFuncionario.getString("email"));
                
                
                
                l.add(funcionarioAutenticado);
            }
            
            GravaLogs.registraLog("Usuario: "+ funcionarioAutenticado.getLogin()+ " recuperado com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro de autenticação " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return l;

    }
     
     public static void alteraSenha(Funcionario f) throws IOException {
        
        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("update funcionario set senha= ? where id= ?");
                   
            
            sql.setString(1, f.getSenha());
            sql.setInt(2, f.getId());
            sql.executeUpdate();

            

            GravaLogs.registraLog("Senha do funcionario " + f.getNome() + "alterada com sucesso");
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao alterar senha do  Funcionario: " + e.getMessage());
        }

        
    
        
    }
    
}

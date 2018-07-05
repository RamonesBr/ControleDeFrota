/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexao.Conectarbanco;
import Modelo.Funcionario;
import Modelo.PerfilDeAcesso;
import Modelo.Veiculo;
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
public class VeiculoDAO {
    public static Veiculo cadastraVeiculo(Veiculo v, Funcionario f) throws IOException {
        try (Connection cnx = Conectarbanco.Banco()) {
            

            PreparedStatement sql = cnx.prepareStatement("INSERT INTO veiculo "
                    + "(renavam, placa, marca, modelo, cor, responsavel, data_tr_oleo, data_manut, status, ano, grupo, km_veic,km_veic_tr_oleo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS); 
            sql.setString(1, v.getRenavam());
            sql.setString(2, v.getPlaca());
            sql.setString(3, v.getMarca());
            sql.setString(4, v.getModelo());
            sql.setString(5, v.getCor());
            sql.setInt(6, f.getId());
            sql.setString(7, v.getData_tr_oleo());
            sql.setString(8, v.getData_manut());
            sql.setString(9, v.getStatus());
            sql.setString(10, v.getAno());
            sql.setString(11, v.getGrupo());
            sql.setInt(12, v.getKm_veic());
            sql.setInt(13, v.getKm_veic_tr_oleo());
            

            sql.executeUpdate();

            ResultSet r = sql.getGeneratedKeys();

            if (r.next()) {
                f.setId(r.getInt(1));
            }

            GravaLogs.registraLog("Novo Cadastro de Veiculo. ID: : " + f.getId());
        } catch (Exception e) {
            GravaLogs.registraLog("Erro no cadastro do Veiculo: " + e.getMessage());
        }

        return v;
    }
    public static List<Veiculo> recuperaTodosVeiculos() throws IOException {
        Veiculo veiculo = null;
        List<Veiculo> lv = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select veiculo.id, veiculo.placa, veiculo.marca, veiculo.modelo, " +
                    "veiculo.cor, funcionario.nome, veiculo.data_tr_oleo, veiculo.data_manut, veiculo.status, veiculo.grupo, veiculo.km_veic, " +
                    "veiculo.km_veic_tr_oleo from veiculo inner join funcionario on veiculo.responsavel= funcionario.id where veiculo.placa not in ('NAO_ALOCADO') ");
            rsVeiculo = pstmt.executeQuery();

            while (rsVeiculo.next()) {
                veiculo = new Veiculo();
                veiculo.setId(rsVeiculo.getInt("id"));
                veiculo.setPlaca(rsVeiculo.getString("placa"));
                veiculo.setMarca(rsVeiculo.getString("marca"));
                veiculo.setModelo(rsVeiculo.getString("modelo"));
                veiculo.setCor(rsVeiculo.getString("cor"));
                veiculo.getResponsavel().setNome(rsVeiculo.getString("nome"));
                veiculo.setData_tr_oleo(rsVeiculo.getString("data_tr_oleo"));
                veiculo.setData_manut(rsVeiculo.getString("data_manut"));
                veiculo.setStatus(rsVeiculo.getString("status"));
                veiculo.setGrupo(rsVeiculo.getString("grupo"));
                veiculo.setKm_veic(rsVeiculo.getInt("km_veic"));
                veiculo.setKm_veic_tr_oleo(rsVeiculo.getInt("km_veic_tr_oleo"));
                
                
                lv.add(veiculo);
            }
            
            GravaLogs.registraLog("Veiculo placa: "+ veiculo.getPlaca()+ " recuperado com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao recuperar dados de veículos " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return lv;

    }
    public static List<Veiculo> recuperaTodosVeiculosDisponiveis() throws IOException {
        Veiculo veiculo = null;
        List<Veiculo> lv = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select veiculo.id, veiculo.placa, veiculo.marca, veiculo.modelo, " +
                    "veiculo.cor, funcionario.nome, veiculo.data_tr_oleo, veiculo.data_manut, veiculo.status, veiculo.grupo, veiculo.km_veic " +
                    "from veiculo inner join funcionario on veiculo.responsavel= funcionario.id where veiculo.status='DISPONIVEL' and veiculo.placa not in ('NAO_ALOCADO')");
            rsVeiculo = pstmt.executeQuery();

            while (rsVeiculo.next()) {
                veiculo = new Veiculo();
                veiculo.setId(rsVeiculo.getInt("id"));
                veiculo.setPlaca(rsVeiculo.getString("placa"));
                veiculo.setMarca(rsVeiculo.getString("marca"));
                veiculo.setModelo(rsVeiculo.getString("modelo"));
                veiculo.setCor(rsVeiculo.getString("cor"));
                veiculo.getResponsavel().setNome(rsVeiculo.getString("nome"));
                veiculo.setData_tr_oleo(rsVeiculo.getString("data_tr_oleo"));
                veiculo.setData_manut(rsVeiculo.getString("data_manut"));
                veiculo.setStatus(rsVeiculo.getString("status"));
                veiculo.setGrupo(rsVeiculo.getString("grupo"));
                veiculo.setKm_veic(rsVeiculo.getInt("km_veic"));
                
                lv.add(veiculo);
            }
            
            GravaLogs.registraLog("Veiculo placa: "+ veiculo.getPlaca()+ " recuperado com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao recuperar dados de veículos " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return lv;

    }
    public static List<Veiculo> recuperaTodosVeiculosDisponiveisPorDepto(Funcionario funcionario) throws IOException {
        Veiculo veiculo = null;
        List<Veiculo> lv = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select veiculo.id, veiculo.placa, veiculo.marca, veiculo.modelo, " +
                    "veiculo.cor, funcionario.nome, veiculo.data_tr_oleo, veiculo.data_manut, veiculo.status, veiculo.grupo, veiculo.km_veic " +
                    "from veiculo inner join funcionario on veiculo.responsavel= funcionario.id where veiculo.status='DISPONIVEL' and veiculo.placa not in ('NAO_ALOCADO') and grupo = ? ");
            pstmt.setString(1, funcionario.getDepartamento());
            rsVeiculo = pstmt.executeQuery();

            while (rsVeiculo.next()) {
                veiculo = new Veiculo();
                veiculo.setId(rsVeiculo.getInt("id"));
                veiculo.setPlaca(rsVeiculo.getString("placa"));
                veiculo.setMarca(rsVeiculo.getString("marca"));
                veiculo.setModelo(rsVeiculo.getString("modelo"));
                veiculo.setCor(rsVeiculo.getString("cor"));
                veiculo.getResponsavel().setNome(rsVeiculo.getString("nome"));
                veiculo.setData_tr_oleo(rsVeiculo.getString("data_tr_oleo"));
                veiculo.setData_manut(rsVeiculo.getString("data_manut"));
                veiculo.setStatus(rsVeiculo.getString("status"));
                veiculo.setGrupo(rsVeiculo.getString("grupo"));
                veiculo.setKm_veic(rsVeiculo.getInt("km_veic"));
                
                lv.add(veiculo);
            }
            
            GravaLogs.registraLog("Veiculo placa: "+ veiculo.getPlaca()+ " recuperado com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao recuperar dados de veículos " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return lv;

    }
    
    public static void alteraVeiculo(Veiculo v, Funcionario f) throws IOException {
    
    try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("update veiculo set renavam= ?, placa= ?, marca= ?,  " + 
                    "modelo = ?, cor= ?, responsavel= ?, data_tr_oleo= ?, data_manut= ?, ano= ?, grupo= ?, km_veic= ?,km_veic_tr_oleo = ? where id= ?");
            sql.setString(1, v.getRenavam());
            sql.setString(2, v.getPlaca());
            sql.setString(3, v.getMarca());
            sql.setString(4, v.getModelo());
            sql.setString(5, v.getCor());
            sql.setInt(6, f.getId());
            sql.setString(7, v.getData_tr_oleo());
            sql.setString(8, v.getData_manut());
            sql.setString(9, v.getAno());
            sql.setString(10, v.getGrupo());
            sql.setInt(11, v.getKm_veic());
            sql.setInt(12, v.getKm_veic_tr_oleo());
            sql.setInt(13, v.getId());
            
            sql.executeUpdate();

            

            GravaLogs.registraLog("Veiculo " + v.getPlaca() + "alterado com sucesso");
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao alterar Funcionario: " + e.getMessage());
        }

        
    
        
    }
    
    public static void excluiVeiculo(Veiculo v) throws IOException, SQLException{
        
        try (Connection cnx = Conectarbanco.Banco()) {
            
            PreparedStatement sql = cnx.prepareStatement("DELETE FROM veiculo WHERE id= ?");
            
            sql.setInt(1, v.getId());
            
            sql.execute();
            
            GravaLogs.registraLog("Veiculo deletado com sucesso");
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao excluir veiculo" +e.getMessage());
        }
        
        
    }
    public static Veiculo veiculoEdicao(Veiculo veiculo) throws IOException {
        

        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select veiculo.id, veiculo.renavam, veiculo.placa, veiculo.marca, veiculo.modelo, veiculo.km_veic_tr_oleo,  " +
                    "veiculo.cor, funcionario.nome, veiculo.data_tr_oleo, veiculo.data_manut, veiculo.status, veiculo.ano, veiculo.grupo, veiculo.km_veic " +
                    "from veiculo inner join funcionario on veiculo.responsavel= funcionario.id where veiculo.id = ?");
            pstmt.setInt(1, veiculo.getId());
            
            rsVeiculo = pstmt.executeQuery();

            if (rsVeiculo.next()) {
               
                veiculo.setId(rsVeiculo.getInt("id"));
                veiculo.setRenavam(rsVeiculo.getString("renavam"));
                veiculo.setPlaca(rsVeiculo.getString("placa"));
                veiculo.setMarca(rsVeiculo.getString("marca"));
                veiculo.setModelo(rsVeiculo.getString("modelo"));
                veiculo.setCor(rsVeiculo.getString("cor"));
                veiculo.getResponsavel().setNome(rsVeiculo.getString("nome"));
                veiculo.setData_tr_oleo(rsVeiculo.getString("data_tr_oleo"));
                veiculo.setData_manut(rsVeiculo.getString("data_manut"));
                veiculo.setStatus(rsVeiculo.getString("status"));
                veiculo.setAno(rsVeiculo.getString("ano"));
                veiculo.setGrupo(rsVeiculo.getString("grupo"));
                veiculo.setKm_veic(rsVeiculo.getInt("km_veic"));
                veiculo.setKm_veic_tr_oleo(rsVeiculo.getInt("km_veic_tr_oleo"));
                                                        
                
                
                
                
            }
            
            GravaLogs.registraLog("Id numero "+veiculo.getId() + "recuperado com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao recuperar id de veiculo " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return veiculo;

    }
    
    public static boolean verificaResponsavelVeiculo(Funcionario funcionario) throws IOException{
        
        Boolean retorna = false;
        
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select * from veiculo  where  responsavel = ? and  status = 'DISPONIVEL'");
            pstmt.setInt(1, funcionario.getId());
            
            rsVeiculo = pstmt.executeQuery();

            if (rsVeiculo.next()) {
               
              retorna =true;
                
            }
            
            GravaLogs.registraLog("O funcionario "+funcionario.getId()+ "pertence um veículo");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao verificar se funcionario tem veiculo" + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return retorna;
    }
    
     public static boolean comparaDeptoComGrupoBoolean(Funcionario funcionario) throws IOException{
        
        Boolean retorna = false;
        Veiculo veiculo = new Veiculo();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        try {
            conexao = Conectarbanco.Banco();
                pstmt = conexao.prepareStatement("select * from veiculo inner join funcionario on funcionario.id = veiculo.responsavel "
                        + "where veiculo.grupo = ? and veiculo.status='DISPONIVEL'");
                        
            pstmt.setString(1, funcionario.getDepartamento());
            
            rsVeiculo = pstmt.executeQuery();

            if (rsVeiculo.next()) {
               veiculo.setPlaca(rsVeiculo.getString("placa"));
              retorna =true;
                
            }
            
            GravaLogs.registraLog("O veiculo: "+veiculo.getPlaca() + " foi alocado para o funcionario "+funcionario.getId());

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao verificar se tem algum veiculo disponivel igual ao grupo do funcionario " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return retorna;
    }
     public static Veiculo comparaDeptoComGrupo(Funcionario funcionario) throws IOException{
        
        
        Veiculo veiculo = new Veiculo();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select * from veiculo inner join funcionario on funcionario.id = veiculo.responsavel "
                        + "where veiculo.grupo = ? and veiculo.status='DISPONIVEL'");
            pstmt.setString(1, funcionario.getDepartamento());
            
            rsVeiculo = pstmt.executeQuery();

            if (rsVeiculo.next()) {
               
             veiculo.setId(rsVeiculo.getInt("id"));
                
            }
            
            GravaLogs.registraLog("Existe veiculo alocado no departamento ");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao alocar o veiculo apos a confirmação da verificação entre dpto e grupo " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return veiculo;
    }
     public static boolean verificaVeicLivreBoolean() throws IOException{
        
        Boolean retorna = false;
        Veiculo veiculo = new Veiculo();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select *from veiculo where grupo ='LIVRE' "
                    + "and status='DISPONIVEL' and placa not in ('NAO_ALOCADO') ");
            
            
            rsVeiculo = pstmt.executeQuery();

            if (rsVeiculo.next()) {
               veiculo.setPlaca(rsVeiculo.getString("placa"));
              retorna =true;
                
            }
            
            GravaLogs.registraLog("O veiculo LIVRE: "+veiculo.getPlaca() + " foi alocado ");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao verificar se tem algum veiculo LIVRE disponivel " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return retorna;
    }
     
     public static Veiculo verificaVeicLivre() throws IOException{
        
        
        Veiculo veiculo = new Veiculo();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select *from veiculo where grupo ='LIVRE' "
                    + " and status='DISPONIVEL' and placa not in ('NAO_ALOCADO')");
            
            
            rsVeiculo = pstmt.executeQuery();

            if (rsVeiculo.next()) {
               
             veiculo.setId(rsVeiculo.getInt("id"));
             veiculo.setPlaca(rsVeiculo.getString("placa"));
                
            }
            
            GravaLogs.registraLog("O veiculo do grupo LIVRE: "+veiculo.getPlaca()+" esta disponivel");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao alocar o veiculo LIVRE" + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return veiculo;
    }
     public static Veiculo alocaVeicNaoAlocado() throws IOException{
        
        
        Veiculo veiculo = new Veiculo();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select id from veiculo where placa='NAO_ALOCADO' ");
            
            
            rsVeiculo = pstmt.executeQuery();

            if (rsVeiculo.next()) {
               
             veiculo.setId(rsVeiculo.getInt("id"));
             veiculo.setPlaca(rsVeiculo.getString("placa"));
                
            }
            
            GravaLogs.registraLog("O veiculo NAO_ALOCADO foi atribuido para viagem");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao atribuir o veiculo NAO_ALOCADO: " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return veiculo;
    }
    
    public static Veiculo retornaIdVeiculoFunc(Funcionario funcionario) throws IOException{
        
        
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        Veiculo veiculo = new Veiculo();
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select id from veiculo where responsavel=?");
            pstmt.setInt(1, funcionario.getId());
            
            rsVeiculo = pstmt.executeQuery();

            if (rsVeiculo.next()) {
               
              
              veiculo.setId(rsVeiculo.getInt("id"));
                
            }
            
            GravaLogs.registraLog("O veiculo "+veiculo.getId()+ "do funcionario "+funcionario.getId()+" foi recuperado" );

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao veiculo do funcionario responsavel" + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }
        
        return veiculo;
        
    }
    
    public static Veiculo buscaVeiculoPorStatusDisponivel() throws IOException{
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        Veiculo veiculo = new Veiculo();
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select id from veiculo where status='DISPONIVEL' ");
            
            
            rsVeiculo = pstmt.executeQuery();

            if (rsVeiculo.next()) {
               
              
              veiculo.setId(rsVeiculo.getInt("id"));
                
            }
            
            GravaLogs.registraLog("O veiculo foi recuperado" );

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao veiculo do funcionario responsavel" + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }
        
        return veiculo;
        
        
    }
    
      public static void alteraStatusVeiculo(Veiculo v) throws IOException{
        
        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("update veiculo set status = ?  where id=? and placa not in('NAO_ALOCADO') ");
            sql.setString(1, v.getStatus());
            sql.setInt(2, v.getId());
           
            sql.executeUpdate();

            

            GravaLogs.registraLog("Veiculo " + v.getId() + "foi alterado para Status INDISPONIVEL");
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao alterar status da viagem: " + e.getMessage());
        }
        
    }
      
    public static void alteraKmVeic(Veiculo v) throws IOException{
        
        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("update veiculo set km_veic = ?  where placa= ?  ");
            sql.setInt(1, v.getKm_veic());
            sql.setString(2, v.getPlaca());
           
            sql.executeUpdate();

            

            GravaLogs.registraLog("Quilometragem do veiculo "+v.getPlaca()+" atualizada para "+v.getKm_veic());
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao atualizar quilometragem " + e.getMessage());
        }
        
    }
    
    public static boolean verificaDataOleo(Veiculo veiculo) throws IOException{
        
        Boolean retorna = false;
        
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("SELECT * FROM veiculo WHERE DATEDIFF(now(), data_tr_oleo) >= 150 and id = ?");
            pstmt.setInt(1, veiculo.getId());
            
            rsVeiculo = pstmt.executeQuery();

            if (rsVeiculo.next()) {
               
              retorna =true;
                
            }
            
           

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao verificar data de oleo veiculo metodo verificaDataOleo " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return retorna;
    }
    
   
     public static List<Veiculo> veiculosManutencao() throws IOException {
        Veiculo veiculo = null;
        List<Veiculo> lv = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select id, placa, modelo, data_manut, data_tr_oleo, km_veic, km_veic_tr_oleo, status " +
                    "from veiculo where status in ('DISPONIVEL','MANUTENCAO') and placa not in ('NAO_ALOCADO') ");
            rsVeiculo = pstmt.executeQuery();

            while (rsVeiculo.next()) {
                veiculo = new Veiculo();
                veiculo.setId(rsVeiculo.getInt("id"));
                veiculo.setPlaca(rsVeiculo.getString("placa"));
                veiculo.setModelo(rsVeiculo.getString("modelo"));
                veiculo.setData_tr_oleo(rsVeiculo.getString("data_tr_oleo"));
                veiculo.setData_manut(rsVeiculo.getString("data_manut"));
                veiculo.setStatus(rsVeiculo.getString("status"));
                veiculo.setKm_veic(rsVeiculo.getInt("km_veic"));
                veiculo.setKm_veic_tr_oleo(rsVeiculo.getInt("km_veic_tr_oleo"));
                
                
                lv.add(veiculo);
            }
            
            GravaLogs.registraLog("Veiculo placa: "+ veiculo.getPlaca()+ " recuperado com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao recuperar dados de veículos " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return lv;

    }
     public static List<Veiculo> veiculosManutencaoPorDepto(Funcionario funcionario) throws IOException {
        Veiculo veiculo = null;
        List<Veiculo> lv = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVeiculo = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select id, placa, modelo, data_manut, data_tr_oleo, km_veic, km_veic_tr_oleo, status " +
                    "from veiculo where status in ('DISPONIVEL','MANUTENCAO') and placa not in ('NAO_ALOCADO') and grupo = ? ");
            pstmt.setString(1, funcionario.getDepartamento());
            rsVeiculo = pstmt.executeQuery();

            while (rsVeiculo.next()) {
                veiculo = new Veiculo();
                veiculo.setId(rsVeiculo.getInt("id"));
                veiculo.setPlaca(rsVeiculo.getString("placa"));
                veiculo.setModelo(rsVeiculo.getString("modelo"));
                veiculo.setData_tr_oleo(rsVeiculo.getString("data_tr_oleo"));
                veiculo.setData_manut(rsVeiculo.getString("data_manut"));
                veiculo.setStatus(rsVeiculo.getString("status"));
                veiculo.setKm_veic(rsVeiculo.getInt("km_veic"));
                veiculo.setKm_veic_tr_oleo(rsVeiculo.getInt("km_veic_tr_oleo"));
                
                
                lv.add(veiculo);
            }
            
            GravaLogs.registraLog("Veiculo placa: "+ veiculo.getPlaca()+ " recuperado com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao recuperar dados de veículos " + sqlErro.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }

        return lv;

    }
     public static void alteraDadosManutencao(Veiculo v) throws IOException {
    
    try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("update veiculo set data_tr_oleo= ?, data_manut= ?, km_veic_tr_oleo= ? " + 
                    "where id= ? and status='MANUTENCAO' ");
            sql.setString(1, v.getData_tr_oleo() );
            sql.setString(2, v.getData_manut()); 
            sql.setInt(3, v.getKm_veic_tr_oleo());
            sql.setInt(4, v.getId());
          
            sql.executeUpdate();

            

            GravaLogs.registraLog("Veiculo " + v.getPlaca() + "alterado no modulo de manut");
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao alterar Veiculo no mod de manut: " + e.getMessage());
        }

        
    
        
    }
    
}

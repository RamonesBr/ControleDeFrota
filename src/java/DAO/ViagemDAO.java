/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexao.Conectarbanco;
import Modelo.CheckList;
import Modelo.Endereco;
import Modelo.Funcionario;
import Modelo.Veiculo;
import Modelo.Viagem;
import Util.GravaLogs;
import com.mysql.jdbc.SQLError;
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
public class ViagemDAO {

    public static Viagem cadastraViagem(Viagem v, Funcionario f, Endereco e) throws IOException {
        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("INSERT INTO viagem "
                    + "(endereco, cliente, hr_saida, hr_retorno, funcionario, status,assunto,veiculo, data_viagem, data_retorno) VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            sql.setInt(1, e.getId());
            sql.setString(2, v.getCliente());
            sql.setString(3, v.getHr_saida());
            sql.setString(4, v.getHr_retorno());
            sql.setInt(5, f.getId());
            sql.setString(6, v.getStatus());
            sql.setString(7, v.getAssunto());
            sql.setInt(8, v.getVeiculo().getId());
            sql.setString(9, v.getData_viagem());
            sql.setString(10, v.getData_retorno());

            sql.executeUpdate();

            ResultSet r = sql.getGeneratedKeys();

            if (r.next()) {
                v.setId(r.getInt(1));
            }

            GravaLogs.registraLog("Novo Cadastro de Viagem. ID: : " + f.getId());
        } catch (Exception ex) {
            GravaLogs.registraLog("Erro no cadastro do Viagem: " + ex.getMessage());
        }

        return v;

    }

    public static List<Viagem> listaTodasViagens() throws IOException, SQLException {
        Viagem viagem = null;
        List<Viagem> lv = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsViagem = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select viagem.id, viagem.status, funcionario.nome, veiculo.id as veiculoId, veiculo.modelo, veiculo.placa, viagem.hr_saida, viagem.hr_retorno, viagem.assunto, "
                    + "veiculo.km_veic, viagem.data_viagem, viagem.data_retorno  from viagem inner join funcionario on funcionario.id = viagem.funcionario inner join veiculo on veiculo.id = viagem.veiculo "
                    + "where viagem.status not in('ENCERRADO') and viagem.status not in ('CANCELADO') order by data_viagem");
            rsViagem = pstmt.executeQuery();

            while (rsViagem.next()) {
                viagem = new Viagem();
                viagem.setId(rsViagem.getInt("id"));
                viagem.setStatus(rsViagem.getString("status"));
                viagem.getFuncionario().setNome(rsViagem.getString("nome"));
                viagem.getVeiculo().setId(rsViagem.getInt("veiculoId"));
                viagem.getVeiculo().setModelo(rsViagem.getString("modelo"));
                viagem.getVeiculo().setPlaca(rsViagem.getString("placa"));
                viagem.setHr_saida(rsViagem.getString("hr_saida"));
                viagem.setHr_retorno(rsViagem.getString("hr_retorno"));
                viagem.setAssunto(rsViagem.getString("assunto"));
                viagem.getVeiculo().setKm_veic(rsViagem.getInt("km_veic"));
                viagem.setData_viagem(rsViagem.getString("data_viagem"));
                viagem.setData_retorno(rsViagem.getString("data_retorno"));

                lv.add(viagem);
            }

            GravaLogs.registraLog("Viagens recuperadas com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao recuperar dados de viagens no metodo listaTodasViagens " + sqlErro.getMessage());
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

    public static void alteraStatusLiberado(Viagem v) throws IOException {

        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("UPDATE viagem INNER JOIN  veiculo AS v ON v.id = viagem.veiculo "
                    + "SET viagem.status = ?, viagem.adm_aprovador = ? WHERE viagem.id = ? AND viagem.status = 'PENDENTE' AND v.placa NOT IN('NAO_ALOCADO')");
            sql.setString(1, v.getStatus());
            sql.setString(2, v.getAdm_aprovador());
            sql.setInt(3, v.getId());

            sql.executeUpdate();

            GravaLogs.registraLog("Viagem " + v.getId() + "foi alterado para Status LIBERADO");
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao alterar status da viagem: " + e.getMessage());
        }

    }
    public static void alteraStatusCancelado(Viagem v) throws IOException {

        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("UPDATE viagem INNER JOIN  veiculo AS v ON v.id = viagem.veiculo "
                    + "SET viagem.status = ?, viagem.usuario_cancelador = ? WHERE viagem.id = ? AND viagem.status NOT IN ('ENCERRADO')");
            sql.setString(1, v.getStatus());
            sql.setString(2, v.getUsuario_cancelador());
            sql.setInt(3, v.getId());

            sql.executeUpdate();

            GravaLogs.registraLog("Viagem " + v.getId() + "foi alterado para Status CANCELADO");
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao alterar status da viagem: " + e.getMessage());
        }

    }

    public static void alteraStatusAndamento(Viagem v) throws IOException {

        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("UPDATE viagem INNER JOIN  veiculo AS v ON v.id = viagem.veiculo "
                    + "SET viagem.status = ?, viagem.portaria_saida = ? WHERE viagem.id = ? AND viagem.status = 'LIBERADO' AND v.placa NOT IN('NAO_ALOCADO')");
            sql.setString(1, v.getStatus());
            sql.setString(2, v.getPortaria_saida());
            sql.setInt(3, v.getId());

            sql.executeUpdate();

            GravaLogs.registraLog("Viagem " + v.getId() + "foi alterado para Status Em Viagem");
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao alterar status da viagem: " + e.getMessage());
        }

    }

    public static void alteraStatusEncerrado(Viagem v) throws IOException {

        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("UPDATE viagem INNER JOIN  veiculo AS v ON v.id = viagem.veiculo "
                    + "SET viagem.status = ?, viagem.portaria_chegada = ? WHERE viagem.id = ? AND viagem.status = 'ANDAMENTO' AND v.placa NOT IN('NAO_ALOCADO')");
            sql.setString(1, v.getStatus());
            sql.setString(2, v.getPortaria_chegada());
            sql.setInt(3, v.getId());

            sql.executeUpdate();

            GravaLogs.registraLog("Viagem " + v.getId() + "foi alterado para Status Encerrado");
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao alterar status da viagem: " + e.getMessage());
        }

    }

    public static Viagem listaViagemPorId(Viagem viagem) throws IOException {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsViagem = null;

        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select viagem.id, viagem.assunto, viagem.data_viagem, data_retorno, viagem.cliente, viagem.hr_saida, viagem.hr_retorno, viagem.status, "
                    + "funcionario.nome, funcionario.departamento,veiculo.id as id_veic, veiculo.placa,veiculo.marca, veiculo.modelo, veiculo.km_veic, enderecos.cep, enderecos.rua, enderecos.bairro, enderecos.cidade, enderecos.estado, enderecos.numero "
                    + "from viagem  inner join funcionario on funcionario.id = viagem.funcionario inner join veiculo on veiculo.id = viagem.veiculo inner join enderecos on enderecos.id = viagem.endereco where viagem.id = ?");

            pstmt.setInt(1, viagem.getId());
            rsViagem = pstmt.executeQuery();

            if (rsViagem.next()) {
                viagem.setId(rsViagem.getInt("id"));
                viagem.setData_viagem(rsViagem.getString("data_viagem"));
                viagem.setData_retorno(rsViagem.getString("data_retorno"));
                viagem.setAssunto(rsViagem.getString("assunto"));
                viagem.setCliente(rsViagem.getString("cliente"));
                viagem.setHr_saida(rsViagem.getString("hr_saida"));
                viagem.setHr_retorno(rsViagem.getString("hr_retorno"));
                viagem.setStatus(rsViagem.getString("status"));
                viagem.getFuncionario().setNome(rsViagem.getString("nome"));
                viagem.getFuncionario().setDepartamento(rsViagem.getString("departamento"));
                viagem.getVeiculo().setId(rsViagem.getInt("id_veic"));
                viagem.getVeiculo().setPlaca(rsViagem.getString("placa"));
                viagem.getVeiculo().setMarca(rsViagem.getString("marca"));
                viagem.getVeiculo().setModelo(rsViagem.getString("modelo"));
                viagem.getVeiculo().setKm_veic(rsViagem.getInt("km_veic"));
                viagem.getEnderecos().setCep(rsViagem.getString("cep"));
                viagem.getEnderecos().setRua(rsViagem.getString("rua"));
                viagem.getEnderecos().setBairro(rsViagem.getString("bairro"));
                viagem.getEnderecos().setCidade(rsViagem.getString("cidade"));
                viagem.getEnderecos().setEstado(rsViagem.getString("estado"));
                viagem.getEnderecos().setNumero(rsViagem.getInt("numero"));

            }

            GravaLogs.registraLog("A viagem: " + viagem.getId() + " foi recuperada com sucesso");

        } catch (Exception e) {

            GravaLogs.registraLog("Erro ao alterar status da viagem: " + e.getMessage());
        }

        return viagem;

    }

    public static void alterarVeiculoAdm(Veiculo veiculo, Viagem viagem) throws IOException {

        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("update viagem set veiculo = ? where id = ?");
            sql.setInt(1, veiculo.getId());
            sql.setInt(2, viagem.getId());

            sql.executeUpdate();

            GravaLogs.registraLog("O veiculo da viagem " + viagem.getId() + "foi alterado para o veiculo: " + veiculo.getId());
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao alterar veiculo da viagem: " + e.getMessage());
        }

    }

    public static List<Viagem> listaTodasViagensPortaria() throws IOException, SQLException {
        Viagem viagem = null;
        List<Viagem> lv = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsViagem = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select viagem.id, viagem.status, funcionario.nome,veiculo.id as id_veic, veiculo.modelo, veiculo.placa, viagem.hr_saida, viagem.hr_retorno, viagem.assunto, "
                    + "veiculo.km_veic, viagem.data_viagem, viagem.data_retorno  from viagem inner join funcionario on funcionario.id = viagem.funcionario inner join veiculo on veiculo.id = viagem.veiculo "
                    + "where viagem.status  in('LIBERADO','ANDAMENTO') ");
            rsViagem = pstmt.executeQuery();

            while (rsViagem.next()) {
                viagem = new Viagem();
                viagem.setId(rsViagem.getInt("id"));
                viagem.setStatus(rsViagem.getString("status"));
                viagem.getFuncionario().setNome(rsViagem.getString("nome"));
                viagem.getVeiculo().setModelo(rsViagem.getString("modelo"));
                viagem.getVeiculo().setId(rsViagem.getInt("id_veic"));
                viagem.getVeiculo().setPlaca(rsViagem.getString("placa"));
                viagem.setHr_saida(rsViagem.getString("hr_saida"));
                viagem.setHr_retorno(rsViagem.getString("hr_retorno"));
                viagem.setAssunto(rsViagem.getString("assunto"));
                viagem.getVeiculo().setKm_veic(rsViagem.getInt("km_veic"));
                viagem.setData_viagem(rsViagem.getString("data_viagem"));
                viagem.setData_retorno(rsViagem.getString("data_retorno"));

                lv.add(viagem);
            }

            GravaLogs.registraLog("Viagens recuperadas com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao recuperar dados de viagens no metodo listaTodasViagens " + sqlErro.getMessage());
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

    public static void checkListSaida(CheckList check, Viagem viagem) throws IOException {

        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("update viagem set check_list_saida = ? where id = ?");
            sql.setInt(1, check.getId());
            sql.setInt(2, viagem.getId());

            sql.executeUpdate();

            GravaLogs.registraLog("Check list cadastrado");
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao cadastrar checklist de saida " + e.getMessage());
        }

    }

    public static void checkListEntrada(CheckList check, Viagem viagem) throws IOException {

        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("update viagem set check_list_chegada = ? where id = ?");
            sql.setInt(1, check.getId());
            sql.setInt(2, viagem.getId());

            sql.executeUpdate();

            GravaLogs.registraLog("Check list cadastrado");
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao cadastrar checklist de entrada " + e.getMessage());
        }

    }

    public static void adicionaOcorrenciaSaida(Viagem viagem) throws IOException {

        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("update viagem set ocorrencia_saida = ? where id = ?");
            sql.setString(1, viagem.getOcorrencia_saida());
            sql.setInt(2, viagem.getId());

            sql.executeUpdate();

            GravaLogs.registraLog("Ocorrencia atribuida para viagem: " + viagem.getId());
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao atribuir ocorrencia para viagem");
        }

    }

    public static void adicionaOcorrenciaChegada(Viagem viagem) throws IOException {

        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = cnx.prepareStatement("update viagem set ocorrencia_chegada = ? where id = ?");
            sql.setString(1, viagem.getOcorrencia_chegada());
            sql.setInt(2, viagem.getId());

            sql.executeUpdate();

            GravaLogs.registraLog("Ocorrencia atribuida para viagem: " + viagem.getId());
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao atribuir ocorrencia para viagem");
        }

    }

    public static List<Viagem> listaViagensEncerradas() throws IOException, SQLException {
        Viagem viagem = null;
        List<Viagem> lv = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsViagem = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select viagem.id, viagem.status, funcionario.nome,veiculo.id as id_veic, veiculo.modelo, veiculo.placa, viagem.hr_saida, viagem.hr_retorno, viagem.assunto, "
                    + "veiculo.km_veic, viagem.data_viagem, viagem.data_retorno, viagem.log_saida, viagem.log_chegada  from viagem inner join funcionario on funcionario.id = viagem.funcionario inner join veiculo on veiculo.id = viagem.veiculo "
                    + "where viagem.status = 'ENCERRADO' ");
            rsViagem = pstmt.executeQuery();

            while (rsViagem.next()) {
                viagem = new Viagem();
                viagem.setId(rsViagem.getInt("id"));
                viagem.setStatus(rsViagem.getString("status"));
                viagem.getFuncionario().setNome(rsViagem.getString("nome"));
                viagem.getVeiculo().setModelo(rsViagem.getString("modelo"));
                viagem.getVeiculo().setId(rsViagem.getInt("id_veic"));
                viagem.getVeiculo().setPlaca(rsViagem.getString("placa"));
                viagem.setHr_saida(rsViagem.getString("hr_saida"));
                viagem.setHr_retorno(rsViagem.getString("hr_retorno"));
                viagem.setAssunto(rsViagem.getString("assunto"));
                viagem.getVeiculo().setKm_veic(rsViagem.getInt("km_veic"));
                viagem.setData_viagem(rsViagem.getString("data_viagem"));
                viagem.setData_retorno(rsViagem.getString("data_retorno"));
                viagem.setLog_chegada(rsViagem.getString("log_chegada"));
                viagem.setLog_saida(rsViagem.getString("log_saida"));


                lv.add(viagem);
            }

            GravaLogs.registraLog("Viagens recuperadas com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao recuperar dados de viagens no metodo listaTodasViagens " + sqlErro.getMessage());
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
    
    public static List<Viagem> listaTodasViagensPorGrupo(Funcionario funcionario) throws IOException, SQLException {
        Viagem viagem = null;
        List<Viagem> lv = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsViagem = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select viagem.id, viagem.status, viagem.data_viagem,viagem.data_retorno, funcionario.nome, veiculo.id as idVeic, veiculo.modelo, veiculo.placa, "
                    + "viagem.hr_saida, viagem.hr_retorno, viagem.assunto from viagem inner join veiculo on veiculo.id =viagem.veiculo  "
                    + "inner join funcionario on funcionario.id = viagem.funcionario "
                    + "where funcionario.departamento =? AND viagem.status in ('PENDENTE','LIBERADO','ANDAMENTO')");
            pstmt.setString(1, funcionario.getDepartamento());
            rsViagem = pstmt.executeQuery();

            while (rsViagem.next()) {
                viagem = new Viagem();
                viagem.setId(rsViagem.getInt("id"));
                viagem.setStatus(rsViagem.getString("status"));
                viagem.getFuncionario().setNome(rsViagem.getString("nome"));
                viagem.getVeiculo().setId(rsViagem.getInt("idVeic"));
                viagem.getVeiculo().setModelo(rsViagem.getString("modelo"));
                viagem.getVeiculo().setPlaca(rsViagem.getString("placa"));
                viagem.setHr_saida(rsViagem.getString("hr_saida"));
                viagem.setHr_retorno(rsViagem.getString("hr_retorno"));
                viagem.setAssunto(rsViagem.getString("assunto"));
                viagem.setData_viagem(rsViagem.getString("data_viagem"));
                viagem.setData_retorno(rsViagem.getString("data_retorno"));

                lv.add(viagem);
            }

            GravaLogs.registraLog("Viagens recuperadas com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao recuperar dados de viagens no metodo listaTodasViagensPorGrupo " + sqlErro.getMessage());
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
        public static Viagem listaViagemEncerradaId(Viagem viagem) throws IOException {

        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsViagem = null;

        try {
            conexao = Conectarbanco.Banco();
                pstmt = conexao.prepareStatement("select viagem.id,viagem.status,viagem.cliente, viagem.assunto, viagem.hr_saida, viagem.hr_retorno, viagem.ocorrencia_saida,viagem.data_viagem, "
                        + "viagem.ocorrencia_chegada, viagem.data_viagem,viagem.data_retorno,viagem.log_chegada, viagem.log_saida, viagem.adm_aprovador, viagem.portaria_saida,viagem.portaria_chegada,enderecos.cep, enderecos.rua, "
                        + "enderecos.bairro, enderecos.estado, enderecos.numero,  enderecos.cidade, funcionario.nome,veiculo.id as id_veic, veiculo.placa, veiculo.modelo, veiculo.cor, checkS.avaria_pintura as aps, checkS.avaria_vidro as avs, "
                        + "checkS.avaria_pneu as apns, checkS.avaria_parachoque as aprs, checkC.avaria_pintura, checkC.avaria_vidro, checkC.avaria_pneu, checkC.avaria_parachoque "
                        + "from viagem inner join enderecos on enderecos.id = viagem.endereco inner join funcionario on funcionario.id = viagem.funcionario "
                        + "inner join veiculo on veiculo.id = viagem.veiculo inner join check_list as checkS on checkS.id = viagem.check_list_saida "
                        + "inner join check_list as checkC on checkC.id = viagem.check_list_chegada where viagem.id= ? and viagem.status= ? ");

            pstmt.setInt(1, viagem.getId());
            pstmt.setString(2, viagem.getStatus());
            
            rsViagem = pstmt.executeQuery();

            if (rsViagem.next()) {
                viagem.setId(rsViagem.getInt("id"));
                viagem.setData_viagem(rsViagem.getString("data_viagem"));
                viagem.setData_retorno(rsViagem.getString("data_retorno"));
                viagem.setAssunto(rsViagem.getString("assunto"));
                viagem.setCliente(rsViagem.getString("cliente"));
                viagem.setHr_saida(rsViagem.getString("hr_saida"));
                viagem.setHr_retorno(rsViagem.getString("hr_retorno"));
                viagem.setStatus(rsViagem.getString("status"));
                viagem.setAdm_aprovador(rsViagem.getString("adm_aprovador"));
                viagem.setOcorrencia_saida(rsViagem.getString("ocorrencia_saida"));
                viagem.setOcorrencia_chegada(rsViagem.getString("ocorrencia_chegada"));
                viagem.setPortaria_saida(rsViagem.getString("portaria_saida"));
                viagem.setPortaria_chegada(rsViagem.getString("portaria_chegada"));
                viagem.setLog_chegada(rsViagem.getString("log_chegada"));
                viagem.setLog_saida(rsViagem.getString("log_saida"));
               
                viagem.getFuncionario().setNome(rsViagem.getString("nome"));
               
                viagem.getVeiculo().setId(rsViagem.getInt("id_veic"));
                viagem.getVeiculo().setPlaca(rsViagem.getString("placa"));
                viagem.getVeiculo().setModelo(rsViagem.getString("modelo"));
                viagem.getVeiculo().setCor(rsViagem.getString("cor"));
                
                
                viagem.getEnderecos().setRua(rsViagem.getString("rua"));
                viagem.getEnderecos().setBairro(rsViagem.getString("bairro"));
                viagem.getEnderecos().setCidade(rsViagem.getString("cidade"));
                viagem.getEnderecos().setCep(rsViagem.getString("cep"));
                viagem.getEnderecos().setEstado(rsViagem.getString("estado"));
                viagem.getEnderecos().setNumero(rsViagem.getInt("numero"));
                
                viagem.getCheck_list_saida().setAvaria_parachoque(rsViagem.getString("aprs"));
                viagem.getCheck_list_saida().setAvaria_pintura(rsViagem.getString("aps"));
                viagem.getCheck_list_saida().setAvaria_pneu(rsViagem.getString("apns"));
                viagem.getCheck_list_saida().setAvaria_vidro(rsViagem.getString("avs"));
                
                viagem.getCheck_list_chegada().setAvaria_parachoque(rsViagem.getString("avaria_parachoque"));
                viagem.getCheck_list_chegada().setAvaria_pintura(rsViagem.getString("avaria_pintura"));
                viagem.getCheck_list_chegada().setAvaria_pneu(rsViagem.getString("avaria_pneu"));
                viagem.getCheck_list_chegada().setAvaria_vidro(rsViagem.getString("avaria_vidro"));

            }

            GravaLogs.registraLog("A viagem: " + viagem.getId() + " foi recuperada com sucesso");

        } catch (Exception e) {

            GravaLogs.registraLog("Erro ao alterar status da viagem: " + e.getMessage());
        }

        return viagem;

    }
            public static List<Viagem> listaViagensFuncionarioComum(Funcionario funcionario ) throws IOException, SQLException {
        Viagem viagem = null;
        List<Viagem> lv = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsViagem = null;
        try {
            conexao = Conectarbanco.Banco();
            pstmt = conexao.prepareStatement("select viagem.id, viagem.status, funcionario.nome,veiculo.id as id_veic, veiculo.modelo, veiculo.placa, viagem.hr_saida, viagem.hr_retorno, viagem.assunto, "
                    + "veiculo.km_veic, viagem.data_viagem  from viagem inner join funcionario on funcionario.id = viagem.funcionario inner join veiculo on veiculo.id = viagem.veiculo "
                    + "where funcionario.id = ? and viagem.status not in ('CANCELADO') ");
            pstmt.setInt(1, funcionario.getId());
            rsViagem = pstmt.executeQuery();

            while (rsViagem.next()) {
                viagem = new Viagem();
                viagem.setId(rsViagem.getInt("id"));
                viagem.setStatus(rsViagem.getString("status"));
                viagem.getFuncionario().setNome(rsViagem.getString("nome"));
                viagem.getVeiculo().setModelo(rsViagem.getString("modelo"));
                viagem.getVeiculo().setId(rsViagem.getInt("id_veic"));
                viagem.getVeiculo().setPlaca(rsViagem.getString("placa"));
                viagem.setHr_saida(rsViagem.getString("hr_saida"));
                viagem.setHr_retorno(rsViagem.getString("hr_retorno"));
                viagem.setAssunto(rsViagem.getString("assunto"));
                viagem.getVeiculo().setKm_veic(rsViagem.getInt("km_veic"));
                viagem.setData_viagem(rsViagem.getString("data_viagem"));

                lv.add(viagem);
            }

            GravaLogs.registraLog("Viagens recuperadas com sucesso");

        } catch (SQLException sqlErro) {
            GravaLogs.registraLog("Erro ao recuperar dados de viagens no metodo listaTodasViagens " + sqlErro.getMessage());
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
      public static void registraHorario(Viagem viagem) throws IOException {

        try (Connection cnx = Conectarbanco.Banco()) {

            PreparedStatement sql = null;
            if(viagem.getGrava_hora() == true ){
               sql = cnx.prepareStatement("update viagem set log_chegada = now() where id = ? "); 
               
               sql.setInt(1, viagem.getId());

            }else{
               sql = cnx.prepareStatement("update viagem set log_saida = now() where id = ? "); 
               
               sql.setInt(1, viagem.getId());
                 
            }
          
            
            sql.executeUpdate();

            GravaLogs.registraLog("Ocorrencia atribuida para viagem: " + viagem.getId());
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao atribuir ocorrencia para viagem "+e.getMessage());
        }

    }
        public static void excluirViagem(Viagem v) throws IOException, SQLException{
        
        try (Connection cnx = Conectarbanco.Banco()) {
            
            PreparedStatement sql = cnx.prepareStatement("DELETE FROM viagem WHERE id= ?");
            
            sql.setInt(1, v.getId());
            
            sql.execute();
            
            GravaLogs.registraLog("Viagem deletada com sucesso");
        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao excluir viagem metodo DAO" +e.getMessage());
        }
        
        
    }

}

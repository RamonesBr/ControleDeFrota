/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import DAO.CheckListDAO;
import DAO.EnderecoDAO;
import DAO.FuncionarioDAO;
import DAO.VeiculoDAO;
import DAO.ViagemDAO;
import Modelo.CheckList;
import Modelo.Endereco;
import Modelo.Funcionario;
import Modelo.Veiculo;
import Modelo.Viagem;
import Util.GravaLogs;
import Util.Validacao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author acesso
 */
public class ControleViagem extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {

            String acao = Validacao.Formulario(request.getParameter("acao"));

            if (acao.equals("Cadastrar")) {
                cadastrarViagem(request, response);
            } else if (acao.equals("AlteraStatusLiberado")) {
                alteraStatusLiberado(request, response);
            } else if (acao.equals("ConsultaViagemPorId")) {
                consultaViagemPorId(request, response);

            } else if (acao.equals("AlteraVeicAdm")) {
                alteraVeicAdm(request, response);

            } else if (acao.equals("CheckList")) {
                checkList(request, response);

            } else if (acao.equals("Ocorrencia")) {

                adicionaOcorrencia(request, response);
            } else if (acao.equals("AlteraStatusPortaria")) {
                alteraStatusPortaria(request, response);
            } else if (acao.equals("ConsultaViagemEncerradoPorId")) {
                consultaViagemEncerradoPorId(request, response);
                teste(request, response);
            } else if (acao.equals("CancelaViagem")) {
                cancelaViagem(request, response);
            }

        } catch (Exception e) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void cadastrarViagem(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //Recebe dados de endereço
        try {
            String cep = Validacao.Formulario(request.getParameter("txtCep"));
            String rua = Validacao.Formulario(request.getParameter("txtRua"));
            int numero = Integer.parseInt(Validacao.Formulario(request.getParameter("txtNum")));
            String bairro = Validacao.Formulario(request.getParameter("txtBairro"));
            String cidade = Validacao.Formulario(request.getParameter("txtCidade"));
            String estado = Validacao.Formulario(request.getParameter("txtEstado"));
            String tipoFunc = Validacao.Formulario(request.getParameter("txtTipoFunc"));
           
            //Instancio objs 
            Endereco endereco = new Endereco();
            Funcionario funcionario = new Funcionario();
            Viagem viagem = new Viagem();
            Veiculo veiculo = new Veiculo();

            
            
            //Consulta o ID e Departamento pelo nome
            String nomeFunc = Validacao.Formulario(request.getParameter("txtFunc")); //Recebe o nome do funcionario
            funcionario.setNome(nomeFunc);
            FuncionarioDAO.idResponsavelNome(funcionario); 

            
            

            //Recebe dados da viagem
            String cliente = Validacao.Formulario(request.getParameter("txtCliente"));
            String assunto = Validacao.Formulario(request.getParameter("txtAssunto"));
            String hr_saida = Validacao.Formulario(request.getParameter("txtHoraSaida"));
            String hr_retorno = Validacao.Formulario(request.getParameter("txtHoraRetorno"));
            String data_viagem = Validacao.Formulario(request.getParameter("txtData_viagem"));
            String data_retorno = Validacao.Formulario(request.getParameter("txtData_retorno"));

            // Sets nos Objetos
            endereco.setCep(cep);
            endereco.setRua(rua);
            endereco.setNumero(numero);
            endereco.setBairro(bairro);
            endereco.setCidade(cidade);
            endereco.setEstado(estado);

            viagem.setCliente(cliente);
            viagem.setAssunto(assunto);
            viagem.setHr_saida(hr_saida);
            viagem.setHr_retorno(hr_retorno);
            viagem.setStatus("PENDENTE");
            viagem.setData_viagem(data_viagem);
            viagem.setData_retorno(data_retorno);

            //Cadastra Endereco
            EnderecoDAO.cadastrarEndereco(endereco);

            //Aloca Veiculo
            if (VeiculoDAO.verificaResponsavelVeiculo(funcionario)) {

                veiculo = VeiculoDAO.retornaIdVeiculoFunc(funcionario);

            } else if (VeiculoDAO.comparaDeptoComGrupoBoolean(funcionario)) {

                veiculo = VeiculoDAO.comparaDeptoComGrupo(funcionario);

            } else if (VeiculoDAO.verificaVeicLivreBoolean()) {

                veiculo = VeiculoDAO.verificaVeicLivre();

            } else {

                veiculo = VeiculoDAO.alocaVeicNaoAlocado();

            }

            viagem.setVeiculo(veiculo);

            //Cadastra Viagem 
            ViagemDAO.cadastraViagem(viagem, funcionario, endereco);

            veiculo.setStatus("INDISPONIVEL");
            VeiculoDAO.alteraStatusVeiculo(veiculo);

            GravaLogs.registraLog("Viagem cadastrada com sucesso");

            if (tipoFunc.equals("ADMINISTRADOR")) {

                RequestDispatcher rd = request.getRequestDispatcher("adm_principal.jsp");
                rd.forward(request, response);
            } else if (tipoFunc.equals("ADMINISTRADOR_COMUM")) {
                RequestDispatcher rd = request.getRequestDispatcher("aprovador_comum_principal.jsp");
                rd.forward(request, response);

            } else {
                RequestDispatcher rd = request.getRequestDispatcher("comum_principal.jsp");
                rd.forward(request, response);
            }

        } catch (Exception e) {
            GravaLogs.registraLog("Erro na servlet ControleViagem no metodo cadastra Viagem: " + e.getMessage());
        }

    }

    private void alteraStatusLiberado(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            int id = Integer.parseInt(Validacao.Formulario(request.getParameter("txtIdViagem")));
            String adm_aprovador = Validacao.Formulario(request.getParameter("txtAdm_aprovador"));
            String tipoFunc = Validacao.Formulario(request.getParameter("txtTipo_func"));

            Viagem viagem = new Viagem();
            viagem.setId(id);
            viagem.setStatus("LIBERADO");
            viagem.setAdm_aprovador(adm_aprovador);

            ViagemDAO.alteraStatusLiberado(viagem);

            GravaLogs.registraLog("Viagem " + viagem.getId() + "liberada com sucesso");

            if (tipoFunc.equals("ADMINISTRADOR")) {

                RequestDispatcher rd = request.getRequestDispatcher("adm_principal.jsp");
                rd.forward(request, response);

            } else {

                RequestDispatcher rd = request.getRequestDispatcher("aprovador_comum_principal.jsp");
                rd.forward(request, response);
            }

        } catch (Exception e) {
            GravaLogs.registraLog("Erro na servlet ControleViagem no metodo alteraStatusLiberado: " + e.getMessage());
        }

    }

    private void consultaViagemPorId(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {

            int id = Integer.parseInt(Validacao.Formulario(request.getParameter("txtId")));
            String tipoFunc = Validacao.Formulario(request.getParameter("txtTipo_func"));

            Viagem viagem = new Viagem();
            viagem.setId(id);

            Viagem viagemEdicao = ViagemDAO.listaViagemPorId(viagem);

            GravaLogs.registraLog("Vigem " + viagemEdicao.getId() + " recuperada com sucesso");

            request.setAttribute("viagemEdicao", viagemEdicao);

            if (tipoFunc.equals("ADMINISTRADOR")) {

                RequestDispatcher rd = request.getRequestDispatcher("adm_altera_viagem.jsp");
                rd.forward(request, response);
            } else {

                RequestDispatcher rd = request.getRequestDispatcher("aprovador_comum_detalhe_viagem.jsp");
                rd.forward(request, response);
            }

        } catch (Exception e) {
            GravaLogs.registraLog("Erro na servlet ControleViagem: " + e.getMessage());
        }

    }

    private void alteraVeicAdm(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            int idVeic = Integer.parseInt(Validacao.Formulario(request.getParameter("boxIdVeic")));
            int idViagem = Integer.parseInt(Validacao.Formulario(request.getParameter("txtIdViagem")));
            int idVeicTrocado = Integer.parseInt(Validacao.Formulario(request.getParameter("txtIdVeic")));

            Veiculo veiculo = new Veiculo();
            Veiculo veiculo2 = new Veiculo();
            Viagem viagem = new Viagem();

            veiculo.setId(idVeic);
            viagem.setId(idViagem);

            ViagemDAO.alterarVeiculoAdm(veiculo, viagem);
            veiculo.setStatus("INDISPONIVEL");
            VeiculoDAO.alteraStatusVeiculo(veiculo);

            veiculo2.setId(idVeicTrocado);
            veiculo2.setStatus("DISPONIVEL");
            VeiculoDAO.alteraStatusVeiculo(veiculo2);

            GravaLogs.registraLog("O veiculo da viagem " + viagem.getId() + " alterado com sucesso(servlet)");

            RequestDispatcher rd = request.getRequestDispatcher("adm_principal.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            GravaLogs.registraLog("Erro na servlet ControleViagem metodo de alterar veiculo adm: " + e.getMessage());

        }

    }
    

    private void checkList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //dados para atualizar veiculo
        String placa_veic = Validacao.Formulario(request.getParameter("txtPlaca"));
        int km_veic = Integer.parseInt(Validacao.Formulario(request.getParameter("txtKm_veic")));

        //dados para cadastrar check-list
        int id_viagem = Integer.parseInt(Validacao.Formulario(request.getParameter("txtId_viagem")));
        String status_viagem = Validacao.Formulario(request.getParameter("txtStatus_viagem"));
        String avaria_parachoque = Validacao.Formulario(request.getParameter("txtAvaria_para"));
        String avaria_pintura = Validacao.Formulario(request.getParameter("txtAvaria_pintura"));
        String avaria_pneu = Validacao.Formulario(request.getParameter("txtAvaria_pneu"));
        String avaria_vidro = Validacao.Formulario(request.getParameter("txtAvaria_vidro"));

        Veiculo veiculo = new Veiculo();
        CheckList check = new CheckList();
        Viagem viagem = new Viagem();

        veiculo.setPlaca(placa_veic);
        veiculo.setKm_veic(km_veic);

        viagem.setId(id_viagem);

        check.setAvaria_parachoque(avaria_parachoque);
        check.setAvaria_pintura(avaria_pintura);
        check.setAvaria_pneu(avaria_pneu);
        check.setAvaria_vidro(avaria_vidro);

        if (status_viagem.equals("LIBERADO")) {

            CheckListDAO.cadastraCheckList(check);
            ViagemDAO.checkListSaida(check, viagem);

        } else {
            CheckListDAO.cadastraCheckList(check);
            ViagemDAO.checkListEntrada(check, viagem);
        }

        VeiculoDAO.alteraKmVeic(veiculo);

        GravaLogs.registraLog("Vigem check list e km atualizados com sucesso");

        RequestDispatcher rd = request.getRequestDispatcher("portaria_principal.jsp");
        rd.forward(request, response);

    }

    private void adicionaOcorrencia(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        int id = Integer.parseInt(Validacao.Formulario(request.getParameter("txtId_viagem")));
        String ocorrencia = Validacao.Formulario(request.getParameter("areaOcorrencia"));
        String status_viagem = Validacao.Formulario(request.getParameter("txtStatus_viagem"));

        Viagem viagem = new Viagem();

        viagem.setId(id);

        if (status_viagem.equals("LIBERADO")) {
            viagem.setOcorrencia_saida(ocorrencia);
            ViagemDAO.adicionaOcorrenciaSaida(viagem);

        } else {
            viagem.setOcorrencia_chegada(ocorrencia);
            ViagemDAO.adicionaOcorrenciaChegada(viagem);
        }

        RequestDispatcher rd = request.getRequestDispatcher("portaria_principal.jsp");
        rd.forward(request, response);

    }

    private void alteraStatusPortaria(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        int id = Integer.parseInt(Validacao.Formulario(request.getParameter("txtIdViagem")));
        String portaria = Validacao.Formulario(request.getParameter("txtPorteiro"));
        String status = Validacao.Formulario(request.getParameter("txtStatus_viagem"));
        int idVeic = Integer.parseInt(Validacao.Formulario(request.getParameter("txtIdVeic")));

        Viagem viagem = new Viagem();
        Veiculo veiculo = new Veiculo();
        viagem.setId(id);

        if (status.equals("LIBERADO")) {

            viagem.setPortaria_saida(portaria);
            viagem.setStatus("ANDAMENTO");
            viagem.setGrava_hora(Boolean.FALSE);
            ViagemDAO.alteraStatusAndamento(viagem);
            ViagemDAO.registraHorario(viagem);

        } else {
            viagem.setPortaria_chegada(portaria);
            viagem.setStatus("ENCERRADO");
            viagem.setGrava_hora(Boolean.TRUE);
            ViagemDAO.alteraStatusEncerrado(viagem);
            ViagemDAO.registraHorario(viagem);

            //Disponibiliza veiculo ao final da viagem
            veiculo.setId(idVeic);
            veiculo.setStatus("DISPONIVEL");

            VeiculoDAO.alteraStatusVeiculo(veiculo);

        }

        RequestDispatcher rd = request.getRequestDispatcher("portaria_principal.jsp");
        rd.forward(request, response);

    }

    private void consultaViagemEncerradoPorId(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            int id = Integer.parseInt(Validacao.Formulario(request.getParameter("txtId")));

            Viagem viagem = new Viagem();
            viagem.setId(id);
            viagem.setStatus("ENCERRADO");

            Viagem viagemConsulta = ViagemDAO.listaViagemEncerradaId(viagem);

            GravaLogs.registraLog("Vigem " + viagemConsulta.getId() + " recuperada com sucesso");

            request.setAttribute("viagemConsulta", viagemConsulta);
            RequestDispatcher rd = request.getRequestDispatcher("adm_consulta_viagens_encerradas_id.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            GravaLogs.registraLog("Erro na servlet ControleViagem: " + e.getMessage());
        }

    }

    private void teste(HttpServletRequest request, HttpServletResponse response) {

    }

    private void cancelaViagem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            int idViagem = Integer.parseInt(Validacao.Formulario(request.getParameter("txtIdViagem")));
            int idVeiculo = Integer.parseInt(Validacao.Formulario(request.getParameter("txtIdVeic")));
            String tipoFunc = Validacao.Formulario(request.getParameter("txtTipo_func"));
            String usuario_cancelador = Validacao.Formulario(request.getParameter("txtUsuario_cancelador"));

            Viagem viagem = new Viagem();
            Veiculo veiculo = new Veiculo();

            viagem.setId(idViagem);
            viagem.setUsuario_cancelador(usuario_cancelador);
            viagem.setStatus("CANCELADO");

            ViagemDAO.alteraStatusCancelado(viagem);

            veiculo.setId(idVeiculo);
            veiculo.setStatus("DISPONIVEL");

            VeiculoDAO.alteraStatusVeiculo(veiculo);

            if (tipoFunc.equals("ADMINISTRADOR")) {
                RequestDispatcher rd = request.getRequestDispatcher("adm_principal.jsp");
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("aprovador_comum_principal.jsp");
                rd.forward(request, response);
            }

        } catch (Exception e) {
            GravaLogs.registraLog("Erro ao deletar viagem: " + e.getMessage());
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import DAO.FuncionarioDAO;
import DAO.VeiculoDAO;
import Modelo.Email;
import Modelo.Funcionario;
import Modelo.Veiculo;
import Util.GravaLogs;
import Util.Validacao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author acesso
 */
public class ControleVeiculo extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = Validacao.Formulario(request.getParameter("acao"));
            
             if (acao.equals("Cadastrar")) {
                cadastraVeiculo(request, response);

            }else if(acao.equals("Excluir")){
                excluiVeiculo(request, response);
            }else if(acao.equals("buscaVeicEdit")){
                buscaVeicEdit(request, response);
            }
            else if(acao.equals("Alterar")){
                alteraVeiculo(request, response);
            
            }else if(acao.equals("Manut")){
                manutVeiculo(request,response);
            }else if(acao.equals("StatusManut")){
                alteraStatusVeic(request,response);
            }else if (acao.equals("ReqManut")){
                requisitaManutencao(request, response);
            }
            
        } catch (Exception e) {
            GravaLogs.registraLog("Erro na servlet ControleVeiculo, não passou para os metodos: " + e.getMessage());
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

    private void cadastraVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
        String renavam = Validacao.Formulario(request.getParameter("txtRenavam"));
        String placa = Validacao.Formulario(request.getParameter("txtPlaca"));
        String marca = Validacao.Formulario(request.getParameter("txtMarca"));
        String cor = Validacao.Formulario(request.getParameter("txtCor"));
        String responsavel = Validacao.Formulario(request.getParameter("txtFunc"));
        String oleo = Validacao.Formulario(request.getParameter("txtOleo"));
        String revisao = Validacao.Formulario(request.getParameter("txtRevisao"));
        String ano = Validacao.Formulario(request.getParameter("txtAno"));
        String modelo = Validacao.Formulario(request.getParameter("txtModelo"));
        String grupo = Validacao.Formulario(request.getParameter("txtGrupo"));
        int km_tr_oleo = Integer.parseInt(Validacao.Formulario(request.getParameter("txtKm_tr_oleo")));
        int km_veic = Integer.parseInt(Validacao.Formulario(request.getParameter("txtKm_veic")));
        
        
        Funcionario funcionario = new Funcionario();
       
        //Passo o nome para o metodo, para receber o ID do funcionario selecionado
        funcionario.setNome(responsavel);
        FuncionarioDAO.idResponsavelNome(funcionario);
        
        //Instancio Veiculo e seto os atributos vindos do front
        
        Veiculo veiculo = new Veiculo();
        
        veiculo.setRenavam(renavam);
        veiculo.setPlaca(placa);
        veiculo.setMarca(marca);
        veiculo.setCor(cor);
        veiculo.setData_tr_oleo(oleo);
        veiculo.setData_manut(revisao);
        veiculo.setAno(ano);
        veiculo.setGrupo(grupo);
        veiculo.setModelo(modelo);
        veiculo.setKm_veic(km_veic);
        veiculo.setKm_veic_tr_oleo(km_tr_oleo);
        veiculo.setStatus("DISPONIVEL"); // ja estou deixando o veículo disponível assim que o funcionario faz o cadastro
        //veiculo.setFuncionario(funcionario);
        
        VeiculoDAO.cadastraVeiculo(veiculo, funcionario);
        
        GravaLogs.registraLog("Veiculo placa: " + veiculo.getPlaca() + "cadastrado com sucesso");
         RequestDispatcher rd = request.getRequestDispatcher("adm_gerencia_veic.jsp");
         rd.forward(request, response);
        
        } catch (Exception e) {
            GravaLogs.registraLog("Erro na servler ControleCliente no metodo cadastrarCliente: " +e.getMessage());
        }
        
        
        
        
        
    }

    private void excluiVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
    
        try {
            int id = Integer.parseInt(Validacao.Formulario(request.getParameter("txtId")));
            Veiculo veiculo = new Veiculo();
    
            veiculo.setId(id);
    
            VeiculoDAO.excluiVeiculo(veiculo);
    
            GravaLogs.registraLog("Veiculo excluido com sucesso");
            
            RequestDispatcher rd = request.getRequestDispatcher("adm_gerencia_veic.jsp");
            rd.forward(request, response);
            
            
        } catch (Exception e) {
            GravaLogs.registraLog("Erro na servlet ControleVeiculo metodo exclui veiculo " +e.getMessage());
        }
    
    
    }

    private void buscaVeicEdit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(Validacao.Formulario(request.getParameter("txtId")));
        
        Veiculo veiculo = new Veiculo();
        
        veiculo.setId(id);
        
        Veiculo veiculoEdicao = VeiculoDAO.veiculoEdicao(veiculo);
        
        request.setAttribute("veiculoEdicao", veiculoEdicao);
        RequestDispatcher rd =  request.getRequestDispatcher("adm_altera_veic.jsp");
        rd.forward(request, response);
        
        
    }

    private void alteraVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        int id = Integer.parseInt(Validacao.Formulario(request.getParameter("txtId")));
        String renavam = Validacao.Formulario(request.getParameter("txtRenavam"));
        String placa = Validacao.Formulario(request.getParameter("txtPlaca"));
        String marca = Validacao.Formulario(request.getParameter("txtMarca"));
        String cor = Validacao.Formulario(request.getParameter("txtCor"));
        String responsavel = Validacao.Formulario(request.getParameter("txtFunc"));
        String oleo = Validacao.Formulario(request.getParameter("txtOleo"));
        String revisao = Validacao.Formulario(request.getParameter("txtRevisao"));
        String ano = Validacao.Formulario(request.getParameter("txtAno"));
        String modelo = Validacao.Formulario(request.getParameter("txtModelo"));
        String grupo = Validacao.Formulario(request.getParameter("txtGrupo"));
        int km_veic = Integer.parseInt(Validacao.Formulario(request.getParameter("txtKm_veic")));

        
        Funcionario funcionario = new Funcionario();
        
        funcionario.setNome(responsavel);
        FuncionarioDAO.idResponsavelNome(funcionario);
        
        Veiculo veiculo = new Veiculo();
        
        veiculo.setRenavam(renavam);
        veiculo.setPlaca(placa);
        veiculo.setMarca(marca);
        veiculo.setCor(cor);
        veiculo.setData_tr_oleo(oleo);
        veiculo.setData_manut(revisao);
        veiculo.setAno(ano);
        veiculo.setModelo(modelo);
        veiculo.setId(id);
        veiculo.setGrupo(grupo);
        veiculo.setKm_veic(km_veic);
        
        VeiculoDAO.alteraVeiculo(veiculo, funcionario);
        
        GravaLogs.registraLog("Veiculo: " + veiculo.getPlaca() + "Alterado com sucesso");
        
        RequestDispatcher rd = request.getRequestDispatcher("adm_gerencia_veic.jsp");
        rd.forward(request, response);
        
        
        
        
    }

    private void manutVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
       int id = Integer.parseInt(Validacao.Formulario(request.getParameter("txtId_veic")));
       int km_oleo = Integer.parseInt(Validacao.Formulario(request.getParameter("txtKmOleo")));
       String data_oleo = Validacao.Formulario(request.getParameter("txtData_tr_oleo"));
       String data_manut = Validacao.Formulario(request.getParameter("txtData_ult_manut")); 
       
       Veiculo veiculo = new Veiculo();
       
       veiculo.setId(id);
       veiculo.setKm_veic_tr_oleo(km_oleo);
       veiculo.setData_tr_oleo(data_oleo);
       veiculo.setData_manut(data_manut);
       
       VeiculoDAO.alteraDadosManutencao(veiculo);
       
       veiculo.setStatus("DISPONIVEL");
       VeiculoDAO.alteraStatusVeiculo(veiculo);
      
        GravaLogs.registraLog("Veiculo: " + veiculo.getPlaca() + "Alterado com sucesso");
        
        RequestDispatcher rd = request.getRequestDispatcher("adm_manutencao_veic.jsp");
        rd.forward(request, response);
        
    }

    private void alteraStatusVeic(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        
        int id = Integer.parseInt(Validacao.Formulario(request.getParameter("txtId")));
        
        Veiculo veiculo = new Veiculo();
        veiculo.setId(id);
        
       veiculo.setStatus("MANUTENCAO");
       VeiculoDAO.alteraStatusVeiculo(veiculo);
       
       GravaLogs.registraLog("Veiculo: " + veiculo.getPlaca() + "Alterado status para MANUTENCAO");
        
        RequestDispatcher rd = request.getRequestDispatcher("adm_manutencao_veic.jsp");
        rd.forward(request, response);
    }

    private void requisitaManutencao(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        try {
        
        String placa = Validacao.Formulario(request.getParameter("txtPlaca"));
        String modelo = Validacao.Formulario(request.getParameter("txtModelo"));
        String motivo = Validacao.Formulario(request.getParameter("txtMotivo"));
        String usuario = Validacao.Formulario(request.getParameter("txtNome"));
        
        
      
        List<Funcionario> f = new ArrayList<>();
        
        f = FuncionarioDAO.verificaAdm();
        
        for(int i = 0;  i<f.size(); i++){
            
            String email = f.get(i).getEmail();
            String nome = f.get(i).getNome();
            
            Email e = new Email();
            e.setEmailDestinatario(email);
            e.setNomeDestinatario(nome);
            e.setAssunto("VLOG INFORMA: ");
            e.setMensagem("Prezado(a) "+nome+"."
                    + " O usuário "+usuario+" está requisitando manutenção no veiculo "+modelo+" e placa "+placa+" pelo seguinte motivo: "
                            +motivo);
            e.enviar();
            
        }
        
        GravaLogs.registraLog("O usuario: "+usuario+"requisitou manutencao para o veiculo placa: "+placa+" para os administradores");
        
        RequestDispatcher rd = request.getRequestDispatcher("aprovador_comum_principal.jsp");
        rd.forward(request, response);
        
        
            
        } catch (Exception e) {
            GravaLogs.registraLog("Erro na servlet ControleVeiculo metodo requisitaManutencao " +e.getMessage());
        }
        
        
        
        
        
        
        
        
    }
    

}

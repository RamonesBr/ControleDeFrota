/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import DAO.FuncionarioDAO;
import Modelo.Funcionario;
import Modelo.PerfilDeAcesso;
import Util.GravaLogs;
import Util.Validacao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author acesso
 */
public class ControleFuncionario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = Validacao.Formulario(request.getParameter("acao"));

            if (acao.equals("Cadastrar")) {
                cadastraFuncionario(request, response);

            } else if (acao.equals("Alterar")) {

                alterarFuncionario(request, response);

            } else if (acao.equals("Excluir")) {

                excluiFuncionario(request, response);
            } else if (acao.equals("FuncEdit")) {
                buscaFuncEdit(request, response);
                
            }else if (acao.equals("TrocaSenha")){
                alteraSenha(request,response);
            }

        } catch (Exception e) {
            GravaLogs.registraLog("Erro na servlet Controle Func, antes de passar para os metodos: " + e.getMessage());
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

    private void cadastraFuncionario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            String cpf = Validacao.Formulario(request.getParameter("txtCpf"));
            String cnh = Validacao.Formulario(request.getParameter("txtCnh"));
            String nome = Validacao.Formulario(request.getParameter("txtNome"));
            String data_nasc = Validacao.Formulario(request.getParameter("txtData_Nasc"));
            String departamento = Validacao.Formulario(request.getParameter("txtDepartamento"));
            String tipo = Validacao.Formulario(request.getParameter("txtTipo"));
            String login = Validacao.Formulario(request.getParameter("txtLogin"));
            String senha = Validacao.Formulario(request.getParameter("txtSenha"));
            String email = Validacao.Formulario(request.getParameter("txtEmail"));

            Funcionario f = new Funcionario();

            if (tipo.equalsIgnoreCase("administrador")) {
                f.setTipo(PerfilDeAcesso.ADMINISTRADOR);

            } else if (tipo.equalsIgnoreCase("portaria")) {
                f.setTipo(PerfilDeAcesso.PORTARIA);
            }else if (tipo.equalsIgnoreCase("administrador_comum")) {
                f.setTipo(PerfilDeAcesso.ADMINISTRADOR_COMUM);
            } else {
                f.setTipo(PerfilDeAcesso.COMUM);
            }

            f.setCpf(cpf);
            f.setCnh(cnh);
            f.setNome(nome);
            f.setData_nasc(data_nasc);
            f.setDepartamento(departamento);
            f.setLogin(login);
            f.setSenha(senha);
            f.setEmail(email);
            f.setStatus("ATIVO");

            FuncionarioDAO.cadastraFuncionario(f);
            GravaLogs.registraLog("Funcionario: " + f.getNome() + "Cadastrado com sucesso");

            RequestDispatcher rd = request.getRequestDispatcher("adm_gerencia_func.jsp");;
            rd.forward(request, response);

        } catch (Exception e) {
            GravaLogs.registraLog("Erro  no metodo cadastra cliente servlet: " + e.getMessage());
        }

    }

    private void alterarFuncionario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(Validacao.Formulario(request.getParameter("txtId")));
            String cpf = Validacao.Formulario(request.getParameter("txtCpf"));
            String cnh = Validacao.Formulario(request.getParameter("txtCnh"));
            String nome = Validacao.Formulario(request.getParameter("txtNome"));
            String data_nasc = Validacao.Formulario(request.getParameter("txtData_nasc"));
            String departamento = Validacao.Formulario(request.getParameter("txtDepartamento"));
            String tipo = Validacao.Formulario(request.getParameter("txtTipo"));
            String login = Validacao.Formulario(request.getParameter("txtLogin"));
            String senha = Validacao.Formulario(request.getParameter("txtSenha"));
            String email = Validacao.Formulario(request.getParameter("txtEmail"));
            String status = Validacao.Formulario(request.getParameter("txtStatus"));
            

            Funcionario f = new Funcionario();

            if (tipo.equalsIgnoreCase("administrador")) {
                f.setTipo(PerfilDeAcesso.ADMINISTRADOR);

            } else if (tipo.equalsIgnoreCase("comum")) {
                f.setTipo(PerfilDeAcesso.COMUM);
            } else if (tipo.equalsIgnoreCase("administrador_comum")) {
                f.setTipo(PerfilDeAcesso.ADMINISTRADOR_COMUM);
            } else {
                f.setTipo(PerfilDeAcesso.PORTARIA);
            }

            f.setId(id);
            f.setCnh(cnh);
            f.setCpf(cpf);
            f.setNome(nome);
            f.setData_nasc(data_nasc);
            f.setDepartamento(departamento);
            f.setLogin(login);
            f.setSenha(senha);
            f.setEmail(email);
            f.setStatus(status);

            FuncionarioDAO.alteraFuncionario(f);

            GravaLogs.registraLog("Funcionario: " + f.getNome() + "Alterado com sucesso");
            RequestDispatcher rd = request.getRequestDispatcher("adm_gerencia_func.jsp");
            rd.forward(request, response);
            

        } catch (Exception e) {
            GravaLogs.registraLog("Erro servlet que altera funcionario " + e.getMessage());
        }

    }

    private void excluiFuncionario(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {

        try {
            int id = Integer.parseInt(Validacao.Formulario(request.getParameter("txtId")));

            Funcionario f = new Funcionario();

            f.setId(id);

            FuncionarioDAO.excluirFuncionario(f);

            GravaLogs.registraLog("Funcionario id:" + f.getId() + "excluido com sucesso");

            RequestDispatcher rd = request.getRequestDispatcher("adm_gerencia_func.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            GravaLogs.registraLog("Erro servlet que exclui funcionario " + e.getMessage());
        }

    }

    private void buscaFuncEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(Validacao.Formulario(request.getParameter("txtId")));

            Funcionario f = new Funcionario();

            f.setId(id);
           
            Funcionario funcionarioEdicao = FuncionarioDAO.funcionarioEdicao(f);

            GravaLogs.registraLog("Funcionario "+funcionarioEdicao.getNome()+ " recuperado para edicao");
            
            request.setAttribute("funcionarioEdicao", funcionarioEdicao);
            RequestDispatcher rd =  request.getRequestDispatcher("adm_altera_func.jsp");
           rd.forward(request, response);

        } catch (Exception e) {
            GravaLogs.registraLog("Erro servlet que busca func para edição " + e.getMessage());
        }
        
    }

    private void alteraSenha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        try {
        String senha = Validacao.Formulario(request.getParameter("txtSenha"));   
        String novaSenha = Validacao.Formulario(request.getParameter("txtNovaSenha"));
        String senhaAtual = Validacao.Formulario(request.getParameter("txtSenhaAtual"));
        String confirmaSenha = Validacao.Formulario(request.getParameter("txtConfirmaSenha"));
        int id = Integer.parseInt(Validacao.Formulario(request.getParameter("txtId")));
        
        
        if(senha.equals(senhaAtual)){
            if(novaSenha.equals(confirmaSenha)){
                
                Funcionario funcionario = new Funcionario();
        
                funcionario.setId(id);
                funcionario.setSenha(novaSenha);
        
                FuncionarioDAO.alteraSenha(funcionario);
        
                GravaLogs.registraLog("Senha do funcionario alterada");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
                
            }else{
                request.setAttribute("msg", "Senhas diferentes  !");
                RequestDispatcher rd =  request.getRequestDispatcher("altera_senha.jsp");
                rd.forward(request, response);
            }
            
        }else{
           request.setAttribute("msg", "Senha atual não confere !");
           RequestDispatcher rd =  request.getRequestDispatcher("altera_senha.jsp");
           rd.forward(request, response);
        }
        
       
            
        } catch (Exception e) {
            
            GravaLogs.registraLog("Erro servlet que altera senha funcionario " + e.getMessage());
        }
        
        
        
    }

}

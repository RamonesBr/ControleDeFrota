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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author acesso
 */
public class ControleAcesso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            
            
            String acao = Validacao.Formulario(request.getParameter("acao"));
            
            if(acao.equals("Entrar")){
                Funcionario funcionario = new Funcionario();
                funcionario.setLogin(Validacao.Formulario(request.getParameter("txtLogin")));
                funcionario.setSenha(Validacao.Formulario(request.getParameter("txtSenha")));
                
                
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                Funcionario funcionarioAutenticado = funcionarioDAO.autenticaFuncionario(funcionario);
                
                //se o funcionario existe no banco de dados 
                if(funcionarioAutenticado !=null){
                    
                    //cria uma sessao para o funcionario 
                    
                    HttpSession sessaoFuncionario = request.getSession();
                    sessaoFuncionario.setAttribute("funcionarioAutenticado", funcionarioAutenticado);
                    
                    //redireciona para página principal
                    if (funcionarioAutenticado.getTipo().equals(PerfilDeAcesso.ADMINISTRADOR)) {
                    response.sendRedirect("adm_principal.jsp");  //Redirecionar para jsp de adm
                        
                    } 
                    
                    
                    else if (funcionarioAutenticado.getTipo().equals(PerfilDeAcesso.PORTARIA)) {
                       response.sendRedirect("portaria_principal.jsp"); //Redirecionar para jsp de portaria  
                    }
                    else if (funcionarioAutenticado.getTipo().equals(PerfilDeAcesso.ADMINISTRADOR_COMUM)) {
                       response.sendRedirect("aprovador_comum_principal.jsp"); //Redirecionar para jsp de adm_comum  
                    }
                    
                    else{
                        response.sendRedirect("comum_principal.jsp"); //Redirecionar para jsp de user comum 
                    }
                }else{
                   
                    request.setAttribute("msg","Login/Senha incorretos ou talvez o usuario esteja inativo");
                    RequestDispatcher rd =  request.getRequestDispatcher("login.jsp");
                    rd.forward(request, response);
                }
                
            }else {
                
                
                if(acao.equals("Sair")){
                    HttpSession sessaoFuncionario = request.getSession();
                    sessaoFuncionario.removeAttribute("funcionarioAutenticado");
                    
                    
                    response.sendRedirect("login.jsp");
                }
            }
            
            
            
        } catch (Exception e) {
            GravaLogs.registraLog("Erro na servlet ControleAcesso, não entrou nos metodos: " + e.getMessage());
            request.setAttribute("msg","Login ou Senha incorreto !");
            RequestDispatcher rd =  request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
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

   

}

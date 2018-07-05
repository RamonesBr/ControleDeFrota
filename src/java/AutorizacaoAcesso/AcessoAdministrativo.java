/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutorizacaoAcesso;

import Modelo.Funcionario;
import Modelo.PerfilDeAcesso;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author acesso
 */
public class AcessoAdministrativo {
    
    public void init(FilterConfig filterConfig) throws ServletException {
       
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        //recuperar a sessao 
        
    HttpSession sessaoFuncionario = ((HttpServletRequest)request).getSession();
        
        Funcionario funcionario = (Funcionario) sessaoFuncionario.getAttribute("funcionarioAutenticado");
        
        if(funcionario!=null && funcionario.getTipo().equals(PerfilDeAcesso.ADMINISTRADOR)){
            chain.doFilter(request, response);
            
            
        }else{
            
            ((HttpServletResponse)response).sendRedirect("acessoNegado.jsp");
        }
        
    }

    public void destroy() {
        
    }
    
    
    
}

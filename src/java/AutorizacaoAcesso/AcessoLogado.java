/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutorizacaoAcesso;

import Modelo.Funcionario;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author acesso
 */
public class AcessoLogado {
    public void init(FilterConfig filterConfig) throws ServletException {
       
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        
        Funcionario funcionario = (Funcionario) req.getSession().getAttribute("funcionarioAutenticado");
        
       
        if (funcionario != null) {
        chain.doFilter(request, response); //deixa passar
        } 
        
      
        else {
            ((HttpServletResponse) response).sendRedirect("erro.jsp");
        }
    }

    public void destroy() {
        
    }
    
}

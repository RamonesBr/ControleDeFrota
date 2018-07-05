<%@page import="Modelo.Funcionario" %>
<%

    HttpSession h = request.getSession();
    if(h.isNew()){
        response.sendRedirect("login.jsp");
    }else if(h.getAttribute("funcionarioAutenticado") == null){
            
        response.sendRedirect("login.jsp");
    }else{
            Funcionario funcionarioAutenticado  = (Funcionario) h.getAttribute("funcionarioAutenticado");
    }
%>
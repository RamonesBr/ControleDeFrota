<%-- 
    Document   : erro
    Created on : 17/04/2018, 11:14:51
    Author     : acesso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error page</title>
    </head>
    <body>
        <h1>Erro !!!</h1>
        <%= ((Exception) request.getAttribute("erro")).getMessage() %>
                
        
    </body>
</html>


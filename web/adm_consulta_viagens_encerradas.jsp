<%@include file="include/valida_sessao.jsp" %>
<%@page import="Controle.Auxiliar" %>
<%@page import="java.util.List"%>
<%@page import="Modelo.Viagem"%>
<%
            Funcionario funcionarioAutenticado  = (Funcionario) h.getAttribute("funcionarioAutenticado");
            List<Viagem> v = Auxiliar.viagensEncerradas();

%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Frota Vepan</title>

        <!-- Vendor styles -->
        <link rel="stylesheet" href="vendors/bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="vendors/bower_components/animate.css/animate.min.css">
        <link rel="stylesheet" href="vendors/bower_components/jquery.scrollbar/jquery.scrollbar.css">
        <link rel="stylesheet" href="vendors/bower_components/fullcalendar/dist/fullcalendar.min.css">

        <!-- App styles -->
        <link rel="stylesheet" href="css/app.min.css">
    </head>

    <body data-sa-theme="1">
        <main class="main">                                                                                     
            <div class="page-loader">
                <div class="page-loader__spinner">
                    <svg viewBox="25 25 50 50">
                    <circle cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10" />
                    </svg>
                </div>
            </div>


            <header class="header">
                <div class="navigation-trigger hidden-xl-up" data-sa-action="aside-open" data-sa-target=".sidebar">
                    <i class="zmdi zmdi-menu"></i>
                </div>

                <div class="logo hidden-sm-down">
                    <h1><a href="index.html">V E P A N </a></h1>
                </div>

                <form class="search">
                    <div class="search__inner">
                        <input type="text" class="search__text" placeholder="Search for people, files, documents...">
                        <i class="zmdi zmdi-search search__helper" data-sa-action="search-close"></i>
                    </div>
                </form>

               

                <div class="clock hidden-md-down">
                    <div class="time">
                        <span class="time__hours"></span>
                        <span class="time__min"></span>
                        <span class="time__sec"></span>
                    </div>
                </div>
            </header>

            <aside class="sidebar">
                <div class="scrollbar-inner">

                    <div class="user">
                        <div class="user__info" data-toggle="dropdown">
                            <img class="user__img" src="demo/img/profile-pics/2.jpg" alt="">
                            <div>
                                <div class="user__name">Bem-Vindo <br> <%=funcionarioAutenticado.getLogin()%></div>
                                <div class="user__email"><%=funcionarioAutenticado.getEmail()%></div>
                            </div>
                        </div>

                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="altera_senha.jsp">Alterar Senha</a>
                            
                        </div>
                    </div>

                    <ul class="navigation">
                        <li class="navigation__active"><a href="adm_principal.jsp"><i class="zmdi zmdi-home"></i> Home</a></li>

                        <li class="navigation__sub">
                            <a href=""><i class="zmdi zmdi-account"></i> Funcionarios</a>
                            <ul>
                                <li><a href="adm_funcionarios_cadastrar.jsp">Cadastrar Funcionarios</a></li>
                                <li><a href="adm_gerencia_func.jsp">Gerenciar Funcionarios</a></li>

                            </ul>

                        </li>
                        <li class="navigation__sub">
                            <a href=""><i class="zmdi zmdi-car"></i> Veiculos</a>

                            <ul>
                                <li><a href="adm_veiculos_cadastrar.jsp">Cadastrar Veiculos</a></li>
                                <li><a href="adm_gerencia_veic.jsp">Gerenciar Veiculos</a></li>
                                <li><a href="adm_manutencao_veic.jsp">Manuten��o Veiculos</a></li>
                            </ul>


                        </li>
                        <li class="navigation__sub">
                            <a href=""><i class="zmdi zmdi-navigation"></i> Viagens</a>

                            <ul>
                                <li><a href="adm_cadastra_viagem.jsp">Cadastrar Viagem</a></li>
                                <li><a href="adm_consulta_viagens_encerradas.jsp">Viagens Encerradas</a></li>
                                <li><a href="adm_cancelar_viagem.jsp">Cancelar Viagens</a></li>
                                
                            </ul>


                        </li>
                        <li class="navigation__active"><a href="ControleAcesso?acao=Sair"><i class="zmdi zmdi-arrow-missed"></i> Sair</a></li>


                    </ul>
                </div>
            </aside>


            <div class="themes">
                <div class="scrollbar-inner">
                    <a href="" class="themes__item active" data-sa-value="1"><img src="img/bg/1.jpg" alt=""></a>
                    <a href="" class="themes__item" data-sa-value="2"><img src="img/bg/2.jpg" alt=""></a>
                    <a href="" class="themes__item" data-sa-value="3"><img src="img/bg/3.jpg" alt=""></a>
                    <a href="" class="themes__item" data-sa-value="4"><img src="img/bg/4.jpg" alt=""></a>
                    <a href="" class="themes__item" data-sa-value="5"><img src="img/bg/5.jpg" alt=""></a>
                    <a href="" class="themes__item" data-sa-value="6"><img src="img/bg/6.jpg" alt=""></a>
                    <a href="" class="themes__item" data-sa-value="7"><img src="img/bg/7.jpg" alt=""></a>
                    <a href="" class="themes__item" data-sa-value="8"><img src="img/bg/8.jpg" alt=""></a>
                    <a href="" class="themes__item" data-sa-value="9"><img src="img/bg/9.jpg" alt=""></a>
                    <a href="" class="themes__item" data-sa-value="10"><img src="img/bg/10.jpg" alt=""></a>
                </div>
            </div>

            <section class="content">
                <header class="content__title">
                    <h1>Gerenciamento de frotas </h1>
                    <small>Gerencie suas viagens ...</small>

                   
                </header>
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Viagens</h4>


                        <div class="table-responsive">
                            <table id="data-table" class="table">
                                <thead>
                                    <tr>
                                        <th>Status</th>
                                        <th>Data Viagem</th>
                                        <th>Nome</th>
                                        <th>Modelo</th>
                                        <th>Placa</th>
                                        <th>Hora Saida</th>
                                        <th>Hora Retorno</th>
                                        <th>Assunto</th>                                       
                                        <th>Detalhes</th>
                                        
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                    for(int i =0; i < v.size(); i++){
                                    
                                    
                                    %>

                                    <tr>
                                        <td><%=v.get(i).getStatus()%> </td>
                                        <td><%=v.get(i).getData_viagem()%></td>
                                        <td><%=v.get(i).getFuncionario().getNome()%> </td>
                                        <td><%=v.get(i).getVeiculo().getModelo()%> </td>
                                        <td><%=v.get(i).getVeiculo().getPlaca()%> </td>
                                        <td><%=v.get(i).getHr_saida()%> </td>
                                        <td><%=v.get(i).getHr_retorno()%></td>
                                        <td><%=v.get(i).getAssunto()%></td>                                       
                                        <td><form action="ControleViagem" method="POST">  <input type="hidden" value="<%=v.get(i).getId()%>" name="txtId">
                                                <button type="submit" name="acao" value="ConsultaViagemEncerradoPorId"  class="btn btn-dark btn--icon">  <i class="zmdi zmdi-eye zmdi-hc-fw"></i> </button>  </form>

                                            
                                        </td>
                                    </tr>

                                    <%}%>
                                </tbody>

                            </table>


                        </div>
                       
                    </div>
                </div>  


            </section>
        </main>

        <!-- Older IE warning message -->
        <!--[if IE]>
            <div class="ie-warning">
                <h1>Warning!!</h1>
                <p>You are using an outdated version of Internet Explorer, please upgrade to any of the following web browsers to access this website.</p>

                <div class="ie-warning__downloads">
                    <a href="http://www.google.com/chrome">
                        <img src="img/browsers/chrome.png" alt="">
                    </a>

                    <a href="https://www.mozilla.org/en-US/firefox/new">
                        <img src="img/browsers/firefox.png" alt="">
                    </a>

                    <a href="http://www.opera.com">
                        <img src="img/browsers/opera.png" alt="">
                    </a>

                    <a href="https://support.apple.com/downloads/safari">
                        <img src="img/browsers/safari.png" alt="">
                    </a>

                    <a href="https://www.microsoft.com/en-us/windows/microsoft-edge">
                        <img src="img/browsers/edge.png" alt="">
                    </a>

                    <a href="http://windows.microsoft.com/en-us/internet-explorer/download-ie">
                        <img src="img/browsers/ie.png" alt="">
                    </a>
                </div>
                <p>Sorry for the inconvenience!</p>
            </div>
        <![endif]-->

        <!-- Javascript -->
        <!-- Vendors -->
        <script src="vendors/bower_components/jquery/dist/jquery.min.js"></script>
        <script src="vendors/bower_components/popper.js/dist/umd/popper.min.js"></script>
        <script src="vendors/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
        <script src="vendors/bower_components/jquery.scrollbar/jquery.scrollbar.min.js"></script>
        <script src="vendors/bower_components/jquery-scrollLock/jquery-scrollLock.min.js"></script>

        <!-- Vendors: Data tables -->
        <script src="vendors/bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
        <script src="vendors/bower_components/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
        <script src="vendors/bower_components/datatables.net-buttons/js/buttons.print.min.js"></script>
        <script src="vendors/bower_components/jszip/dist/jszip.min.js"></script>
        <script src="vendors/bower_components/datatables.net-buttons/js/buttons.html5.min.js"></script>


        <!-- App functions and actions -->
        <script src="js/app.min.js"></script> 
    </body>
</html>
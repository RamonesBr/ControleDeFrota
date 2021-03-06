<%@include file="include/valida_sessao.jsp" %>
<%@page import="Controle.Auxiliar" %>
<%@page import="java.util.List"%>
<%@page import="Modelo.Viagem"%>
<%
            Funcionario funcionarioAutenticado  = (Funcionario) h.getAttribute("funcionarioAutenticado");
            List<Viagem> v = Auxiliar.viagensPortaria();

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

                            </div>
                        </div>

                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="altera_senha.jsp">Alterar Senha</a>
                            
                        </div>
                    </div>

                    <ul class="navigation">
                        <li class="navigation__active"><a href="portaria_principal.jsp"><i class="zmdi zmdi-home"></i>Principal</a></li>


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
                    <small>Viagens aguardando Check-List</small>

                    
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
                                        <th>Data Retorno</th>
                                        <th>Nome</th>
                                        <th>Modelo</th>
                                        <th>Placa</th>
                                        <th>Hora Saida</th>
                                        <th>Hora Retorno</th>
                                        <th>Check-List</th>
                                        <th>Ocorrencia</th>
                                        <th>Confirma</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                    for(int i =0; i < v.size(); i++){
                                    
                                    
                                    %>

                                    <tr>
                                        <td><%=v.get(i).getStatus()%> </td>
                                        <td><%=v.get(i).getData_viagem()%></td>
                                        <td><%=v.get(i).getData_retorno()%></td>
                                        <td><%=v.get(i).getFuncionario().getNome()%> </td>
                                        <td><%=v.get(i).getVeiculo().getModelo()%> </td>
                                        <td><%=v.get(i).getVeiculo().getPlaca()%> </td>
                                        <td><%=v.get(i).getHr_saida()%> </td>
                                        <td><%=v.get(i).getHr_retorno()%></td>
                                        <td>

                                            <button type="submit" class="btn btn-dark btn--icon" data-toggle="modal" data-target="#myModal<%=i%>">
                                                <i class="zmdi zmdi-assignment zmdi-hc-fw"></i> </button>

                                        </td>
                                        <td>
                                            
                                            <button type="submit"  class="btn btn-dark btn--icon" data-toggle="modal" data-target="#ModalOcorrencia<%=i%>">  
                                                <i class="zmdi zmdi-comment-edit zmdi-hc-fw"></i> </button>  
                                        </td>
                                        <td>
                                            <form action="ControleViagem"modal method="POST">
                                            <input type="hidden" value="<%=v.get(i).getStatus()%>" name="txtStatus_viagem">
                                            <input type="hidden" value="<%=v.get(i).getId()%>" name="txtIdViagem">
                                            <input type="hidden" value="<%=v.get(i).getVeiculo().getId()%>" name="txtIdVeic">
                                            <button type="submit" name="acao" value="AlteraStatusPortaria"  onclick="return confirm('Deseja realmente LIBERAR a viagem?')" class="btn btn-dark btn--icon">  <i class="zmdi zmdi-thumb-up zmdi-hc-fw"></i> </button> 
                                            <input type="text" hidden name="txtPorteiro" value="<%=funcionarioAutenticado.getLogin()%>"> </form>

                                        </td>
                                    </tr>
                                    <!-- Modal -->

                                <div id="myModal<%=i%>" class="modal fade" role="dialog">
                                    <div class="modal-dialog">      

                                        <!-- Modal content-->
                                        <div class="modal-content">
                                            <form action="ControleViagem" method="POST">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                    <h4 class="modal-title">CHECK-LIST</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <input type="text" required="" hidden class="form-control"  name="txtPlaca"   value="<%=v.get(i).getVeiculo().getPlaca()%>">
                                                    <i class="form-group__bar"></i>

                                                    <input type="text" required="" hidden class="form-control"  name="txtStatus_viagem"   value="<%=v.get(i).getStatus()%>">
                                                    <i class="form-group__bar"></i>


                                                    <input type="text" required="" hidden class="form-control"  name="txtId_viagem"   value="<%=v.get(i).getId()%>">
                                                    <i class="form-group__bar"></i>

                                                    <div class="col-sm-8">
                                                        <div class="form-group">
                                                            <label>Avaria Parachoque</label>
                                                            <input type="text" required="" class="form-control"  name="txtAvaria_para">
                                                            <i class="form-group__bar"></i>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-8">
                                                        <div class="form-group">
                                                            <label>Avaria Pintura</label>
                                                            <input type="text" required="" class="form-control"  name="txtAvaria_pintura">
                                                            <i class="form-group__bar"></i>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-8">
                                                        <div class="form-group">
                                                            <label>Avaria Pneu</label>
                                                            <input type="text" required="" class="form-control"  name="txtAvaria_pneu">
                                                            <i class="form-group__bar"></i>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-8">
                                                        <div class="form-group">
                                                            <label>Avaria Vidro</label>
                                                            <input type="text" required="" class="form-control"  name="txtAvaria_vidro">
                                                            <i class="form-group__bar"></i>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-8">
                                                        <div class="form-group">
                                                            <label>Quilometragem veiculo</label>
                                                            <input type="number" required="" class="form-control" value="<%=v.get(i).getVeiculo().getKm_veic()%>"  name="txtKm_veic">
                                                            <i class="form-group__bar"></i>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                    <button type="submit" name="acao" value="CheckList" class="btn btn btn-primary" data-submit="modal">Salvar Alterações</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>

                                </div>

                                <div id="ModalOcorrencia<%=i%>" class="modal fade" role="dialog">
                                    <div class="modal-dialog">      

                                        <!-- Modal content-->
                                        <div class="modal-content">
                                            <form action="ControleViagem" method="POST">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                    <h4 class="modal-title">INFORME A OCORRENCIA</h4>
                                                </div>
                                                <div class="modal-body">

                                                    <input type="text" required="" hidden class="form-control"  name="txtId_viagem"   value="<%=v.get(i).getId()%>">
                                                    <i class="form-group__bar"></i>
                                                    
                                                    <input type="text" required="" hidden class="form-control"  name="txtStatus_viagem"   value="<%=v.get(i).getStatus()%>">
                                                    <i class="form-group__bar"></i>

                                                    <div class="form-group">
                                                        <textarea class="form-control" rows="5" placeholder="Informe aqui a ocorrencia" name="areaOcorrencia"></textarea>
                                                        <i class="form-group__bar"></i>
                                                    </div>

                                                </div>

                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                    <button type="submit" name="acao" value="Ocorrencia" class="btn btn btn-primary" data-submit="modal">Salvar Alterações</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>

                                </div>
                                <%}%>
                                </tbody>

                            </table>


                        </div>


                    </div>

                </div>  


            </section>

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
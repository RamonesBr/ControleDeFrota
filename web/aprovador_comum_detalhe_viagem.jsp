<%@include file="include/valida_sessao.jsp" %>
<%@page import="Controle.Auxiliar" %>
<%@page import="java.util.List"%>
<%@page import="Modelo.Viagem"%>
<%@page import="Modelo.Veiculo"%>
<%
            Funcionario funcionarioAutenticado  = (Funcionario) h.getAttribute("funcionarioAutenticado");
            Viagem viagem = (Viagem) request.getAttribute("viagemEdicao");
            List<Veiculo> veiculo2 = Auxiliar.todosVeiculosDisponiveisPorDepto(funcionarioAutenticado);
            
            

%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Vendor styles -->
        <link rel="stylesheet" href="vendors/bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="vendors/bower_components/animate.css/animate.min.css">
        <link rel="stylesheet" href="vendors/bower_components/jquery.scrollbar/jquery.scrollbar.css">

        <!-- App styles -->
        <link rel="stylesheet" href="css/app.min.css">
        <link rel="stylesheet" href="css/meucss.css">

        <!-- Meu JavaScript  <script src="js/meujs.js"></script>-->



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
                        <li class="navigation__active"><a href="aprovador_comum_principal.jsp"><i class="zmdi zmdi-home"></i> Home</a></li>

                        <li class="navigation__sub">
                            <a href=""><i class="zmdi zmdi-navigation"></i> Viagens</a>
                            <ul>
                                <li><a href="aprovador_comum_cadastra_viagem.jsp">Cadastrar Viagem</a></li>
                                <li><a href="aprovador_comum_cancelar_viagem.jsp">Cancelar Viagens</a></li>


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

                    <h1 class="card-body__title">Detalhes da viagem</h1>


                </header>

                <div class="card">
                    <form action="ControleViagem" method="POST" >
                        <div class="card-body">

                            <div class="card">
                                <div class="card-body">

                                    <h3 class="card-body__title">ENDERE�O DE DESTINO DA VIAGEM</h3>

                                    <div class="row">

                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>CEP </label>
                                                <input type="text" required="" readonly class="form-control"  name="txtCep" value="<%=viagem.getEnderecos().getCep()%>" ><br/>

                                            </div>
                                        </div>

                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Rua </label>
                                                <input type="text" required readonly class="form-control"name="txtRua" value="<%=viagem.getEnderecos().getRua()%>"><br/>
                                            </div>
                                        </div>

                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Numero </label>
                                                <input type="text" required readonly class="form-control"  name="txtNum" value="<%=viagem.getEnderecos().getNumero()%>"><br/>
                                            </div>
                                        </div>

                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Bairro </label>
                                                <input type="text" required readonly class="form-control"  name="txtBairro" value="<%=viagem.getEnderecos().getBairro()%>"><br/>
                                            </div>
                                        </div>

                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Cidade </label>
                                                <input type="text" required readonly class="form-control" name="txtCidade" value="<%=viagem.getEnderecos().getCidade()%>"><br/>
                                            </div>
                                        </div>

                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Estado</label>
                                                <input type="text" required readonly class="form-control" name="txtEstado" value="<%=viagem.getEnderecos().getEstado()%>"><br/>
                                            </div>
                                        </div>


                                    </div>


                                </div>

                            </div>
                        </div>

                        <div class="card-body">

                            <div class="card">
                                <div class="card-body">
                                    <h3 class="card-body__title">DADOS DA VIAGEM</h3>
                                    <div class="row">

                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <input type="text" required="" readonly class="form-control"  name="txtCliente" value="<%=viagem.getCliente()%>">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        <div class="col-sm-8">
                                            <div class="form-group">
                                                <input  type="text" required="" readonly class="form-control" name="txtAssunto" value="<%=viagem.getAssunto()%>" >
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Hora Saida</label>
                                                <input type="time" required="" readonly class="form-control" name="txtHoraSaida" value="<%=viagem.getHr_saida()%>">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Hora Retorno</label>
                                                <input type="time" required="" readonly class="form-control" name="txtHoraRetorno" value="<%=viagem.getHr_retorno()%>">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Status Viagem</label>
                                                <input type="text" required="" readonly class="form-control" name="txtStatus" value="<%=viagem.getStatus()%>">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Funcionario</label>
                                                <input  type="text" readonly required=""  class="form-control" name="txtFunc" value="<%=viagem.getFuncionario().getNome()%>">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Departamento</label>
                                                <input type="text" readonly required="" class="form-control" name="txtDepartamento" value="<%=viagem.getFuncionario().getDepartamento()%>">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Modelo Veiculo</label>
                                                <input type="text" readonly required="" class="form-control" name="txtPlaca" value="<%=viagem.getVeiculo().getModelo()%>">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Marca Veiculo</label>
                                                <input type="text" readonly required="" class="form-control" name="txtPlaca" value="<%=viagem.getVeiculo().getModelo()%>">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        
                                          <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Data Viagem</label>
                                                <input type="text" readonly required="" class="form-control" name="txtData_viagem" value="<%=viagem.getData_viagem()%>">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Placa Veiculo</label>
                                                <input type="text" readonly required="" class="form-control" name="txtPlaca" value="<%=viagem.getVeiculo().getPlaca()%>">

                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                     <div class="col-sm-3">
                                            <div class="form-group">


                                                <button type="button" class="btn btn-dark btn--icon" data-toggle="modal" data-target="#myModal">  <i class="zmdi zmdi-edit zmdi-hc-fw"></i> </button> 
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                    <!-- Modal -->
                                        <div id="myModal" class="modal fade" role="dialog">
                                            <div class="modal-dialog">      

                                                <!-- Modal content-->
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                        <h4 class="modal-title">Selecione um ve�culo</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        
                                                        <div class="table-responsive">
                                                            <table id="data-table" class="table">
                                                                <thead>
                                                                    <tr>
                                                                        
                                                                        
                                                                        <th> </th>
                                                                        <th>Placa</th>
                                                                        <th>Marca</th>
                                                                        <th>Modelo</th>
                                                                        <th>Status</th>
                                                                        <th>Responsavel</th>




                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <%
                                                                    for(int i =0; i < veiculo2.size(); i++){
                                    
                                    
                                                                    %>

                                                                    <tr>
                                                                        <td class=" "><input type="radio"  name="boxIdVeic" value="<%=veiculo2.get(i).getId()%>" </td>
                                                                        <td><%=veiculo2.get(i).getPlaca()%> </td>
                                                                        <td><%=veiculo2.get(i).getMarca()%> </td>
                                                                        <td><%=veiculo2.get(i).getModelo()%> </td>
                                                                        <td><%=veiculo2.get(i).getStatus()%></td>
                                                                        <td><%=veiculo2.get(i).getResponsavel().getNome()%> </td>



                                                                    </tr>

                                                                    <%}%>
                                                                </tbody>

                                                            </table>
                                                                
                                                                <input type="text" hidden name="txtIdViagem" value="<%=viagem.getId()%>">
                                                                <input type="text" hidden name="txtIdVeic" value="<%=viagem.getVeiculo().getId()%>">

                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                        <button type="submit" name="acao" value="AlteraVeicAdm" class="btn btn btn-primary" data-submit="modal">Salvar Altera��es</button>
                                                    </div>
                                                               
                                                </div>

                                            </div>
                                        </div>
                                 

                                    </div>
                                   

                                       

                                    </div>

                                </div>

                            </div>
                    </form>
                </div>

                </div>

                <footer class="footer hidden-xs-down">
                    <p>© Super Admin Responsive. All rights reserved.</p>

                    <ul class="nav footer__nav">
                        <a class="nav-link" href="">Homepage</a>

                        <a class="nav-link" href="">Company</a>

                        <a class="nav-link" href="">Support</a>

                        <a class="nav-link" href="">News</a>

                        <a class="nav-link" href="">Contacts</a>
                    </ul>
                </footer>

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

        <!-- App functions and actions -->
        <script src="js/app.min.js"></script>



    </body>
</html>
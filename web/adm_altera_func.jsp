<%@include file="include/valida_sessao.jsp" %>
<%
            Funcionario funcionarioAutenticado  = (Funcionario) h.getAttribute("funcionarioAutenticado");
            Funcionario f = (Funcionario) request.getAttribute("funcionarioEdicao");
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
                                <li><a href="adm_manutencao_veic.jsp">Manutenção Veiculos</a></li>
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



                </header>

                <div class="card">
                    <div class="card-body">
                        <div class="card">
                            <div class="card-body">

                                <h3 class="card-body__title">Alterar Funcionario</h3>
                                <form action="ControleFuncionario" method="POST" >
                                    <div class="row">

                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <small>CPF</small>
                                                <input type="number" required="" class="form-control"  name="txtCpf" value="<%=f.getCpf()%>">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                 <small>CNH</small>
                                                <input type="number" required="" class="form-control" name="txtCnh" value="<%=f.getCnh()%>">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                 <small>Nome</small>
                                                <input type="text" required="" class="form-control" name="txtNome" value="<%=f.getNome()%>">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <small>Data Nascimento</small> 
                                                <input type="date" required="" class="form-control" value="<%=f.getData_nasc()%>" name="txtData_nasc">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <small>Departamento</small> 
                                                <select class="form-control" name="txtDepartamento">
                                                    <option style="color: black"><%=f.getDepartamento()%></option>
                                                    <option style="color: black">VENDAS</option>
                                                    <option  style="color: black">LOGISTICA</option>
                                                    <option  style="color: black">BARRAMENTO</option>
                                                    <option  style="color: black">ASSISTENCIA TECNICA</option>
                                                    <option  style="color: black">MECANICA</option>
                                                    <option  style="color: black">AUTOMACAO</option>
                                                    <option  style="color: black">LIVRE</option>
                                                    
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <small>Tipo Funcionario</small> 
                                                <select class="form-control" name="txtTipo" placeholder="Tipo">
                                                    <option style="color: black"><%=f.getTipo()%></option>
                                                    <option style="color: black">ADMINISTRADOR</option>
                                                    <option style="color: black">ADMINISTRADOR_COMUM</option>
                                                    <option  style="color: black">PORTARIA</option>
                                                    <option  style="color: black">COMUM</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                 <small>Email</small> 
                                                <input type="email" required="" class="form-control"  name="txtEmail" value="<%=f.getEmail()%>">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                 <small>LOGIN</small>
                                                <input type="text" required="" class="form-control" name="txtLogin" value="<%=f.getLogin()%>">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                 <small>SENHA</small>
                                                <input type="password" required="" class="form-control" value="<%=f.getSenha()%>" name="txtSenha">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                       <div class="col-sm-3">
                                            <div class="form-group">
                                                <small>STATUS</small>
                                                <select class="form-control" name="txtStatus" >
                                                    <option style="color: black"><%=f.getStatus()%></option>
                                                    <option style="color: black">ATIVO</option>
                                                    <option style="color: black">INATIVO</option>
                                                    
                                                </select>
                                            </div>
                                        </div> 
                                       

                                    </div>
                                                <input type="hidden" name="txtId" value="<%=f.getId()%>" >
                                    <div>
                                        <input type="submit" value="Alterar" class="btn btn-outline-primary" name="acao" />

                                    </div>
                                </form>
                            </div>

                        </div>
                    </div>
                </div>

                <footer class="footer hidden-xs-down">
                    <p>Â© Super Admin Responsive. All rights reserved.</p>

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
        <!-- Cliente cadastrado com sucesso -->
         <script>
             function Alerta(){
          alert("Cliente cadastrado com sucesso !")
      }
        </script>
    </body>
</html>
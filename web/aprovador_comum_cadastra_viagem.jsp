<%@include file="include/valida_sessao.jsp" %>
<%@page import="Controle.Auxiliar" %>
<%@page import="java.util.List"%>
<%
            Funcionario funcionarioAutenticado  = (Funcionario) h.getAttribute("funcionarioAutenticado");
            List<Funcionario> f = Auxiliar.funcionariosResponsaveisDepto(funcionarioAutenticado);

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

        <!-- Meu JavaScript  <script src="js/meujs.js"></script>-->

        <script type="text/javascript">

            function limpa_formulário_cep() {
                //Limpa valores do formulário de cep.
                document.getElementById('rua').value = ("");
                document.getElementById('bairro').value = ("");
                document.getElementById('cidade').value = ("");
                document.getElementById('estado').value = ("");

            }

            function meu_callback(conteudo) {
                if (!("erro" in conteudo)) {
                    //Atualiza os campos com os valores.
                    document.getElementById('rua').value = (conteudo.logradouro);
                    document.getElementById('bairro').value = (conteudo.bairro);
                    document.getElementById('cidade').value = (conteudo.localidade);
                    document.getElementById('estado').value = (conteudo.uf);

                } //end if.
                else {
                    //CEP não Encontrado.
                    limpa_formulário_cep();
                    alert("CEP não encontrado.");
                }
            }

            function pesquisacep(valor) {

                //Nova variável "cep" somente com dígitos.
                var cep = valor.replace(/\D/g, '');

                //Verifica se campo cep possui valor informado.
                if (cep != "") {

                    //Expressão regular para validar o CEP.
                    var validacep = /^[0-9]{8}$/;

                    //Valida o formato do CEP.
                    if (validacep.test(cep)) {

                        //Preenche os campos com "..." enquanto consulta webservice.
                        document.getElementById('rua').value = "...";
                        document.getElementById('bairro').value = "...";
                        document.getElementById('cidade').value = "...";
                        document.getElementById('estado').value = "...";



                        //Cria um elemento javascript.
                        var script = document.createElement('script');

                        //Sincroniza com o callback.
                        script.src = 'http://viacep.com.br/ws/' + cep + '/json/?callback=meu_callback';

                        //Insere script no documento e carrega o conteúdo.
                        document.body.appendChild(script);
                        document.getElementById("num").focus();



                    } //end if.
                    else {
                        //cep é inválido.
                        limpa_formulário_cep();
                        alert("Formato de CEP inválido.");
                    }
                } //end if.
                else {
                    //cep sem valor, limpa formulário.
                    limpa_formulário_cep();
                }
            }
            ;
        </script>

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
                        <li class="navigation__sub">
                            <a href=""><i class="zmdi zmdi-car"></i> Veiculos</a>
                            <ul>
                                <li><a href="aprovador_comum_manutencao.jsp">Manutenção Veiculo</a></li>
                                
                                

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

                    <h1 class="card-body__title">CADASTRE SUA VIAGEM</h1>


                </header>

                <div class="card">
                    <form action="ControleViagem" method="POST" >
                        <div class="card-body">

                            <div class="card">
                                <div class="card-body">

                                    <h3 class="card-body__title">INFORME SEU DESTINO</h3>

                                    <div class="row">

                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label onkeypress="this.value = ''" for="campo1">CEP </label>
                                                <input type="text" required="" class="form-control" id="cep" name="txtCep"
                                                       size="10" maxlength="9" onblur="pesquisacep(this.value);"><br/>
                                            </div>
                                        </div>

                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label for="campo2">Rua </label>
                                                <input type="text" required class="form-control" id="rua" name="txtRua"><br/>
                                            </div>
                                        </div>

                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label for="campo3">Numero </label>
                                                <input type="number" required class="form-control" id="num" name="txtNum"><br/>
                                            </div>
                                        </div>

                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label for="campo4">Bairro </label>
                                                <input type="text" required class="form-control" id="bairro" name="txtBairro"><br/>
                                            </div>
                                        </div>

                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label for="campo5">Cidade </label>
                                                <input type="text" required class="form-control" id="cidade" name="txtCidade"><br/>
                                            </div>
                                        </div>

                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label for="campo6">Estado</label>
                                                <input type="text" required class="form-control" id="estado" name="txtEstado"><br/>
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
                                                <input type="text" required="" class="form-control"  name="txtCliente"   placeholder="Referencia (Ex: CLiente, Fornecedor, Estabelecimento)">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        <div class="col-sm-8">
                                            <div class="form-group">
                                                <input  type="text" required="" class="form-control" name="txtAssunto" placeholder="Assunto  (Descreva brevemente o motivo de sua viagem)">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Responsável Veículo</label>
                                                <select class="form-control" name="txtFunc" placeholder="Responsavel Veiculo">
                                                   <option style="color: black"> </option>
                                                    <%
                                                    for(int i =0; i < f.size(); i++){
                                                    %>
                                                    
                                                    <option style="color: black"><%=f.get(i).getNome() %> </option>
                                                    
                                                    <% } %>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Hora Saida</label>
                                                <input type="time" required="" class="form-control" placeholder="Hora de Saida" name="txtHoraSaida">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Hora Retorno</label>
                                                <input type="time" required="" class="form-control" placeholder="Retorno Previsto" name="txtHoraRetorno">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Data da viagem</label>
                                                <input type="date" required="" class="form-control" placeholder="Retorno Previsto" name="txtData_viagem">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                        
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Data de retorno</label>
                                                <input type="date" required="" class="form-control" placeholder="Retorno Previsto" name="txtData_retorno">
                                                <i class="form-group__bar"></i>
                                            </div>
                                        </div>
                                     

                                    </div>
                                                <input type="hidden" name="txtIdFunc" value="<%=funcionarioAutenticado.getId()%>"/>
                                                <input type="hidden" name="txtTipoFunc" value="<%=funcionarioAutenticado.getTipo()%>"/>

                                    <div>
                                        <input type="submit" value="Cadastrar" class="btn btn-outline-primary" name="acao" />

                                    </div>

                                </div>

                            </div>
                           </form>
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
      
        

    </body>
</html>
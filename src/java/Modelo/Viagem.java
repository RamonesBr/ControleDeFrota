/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author acesso
 */
public class Viagem {

    private int id;
    private Endereco enderecos = new Endereco();
    private String cliente;
    private String assunto;
    private String hr_saida;
    private String hr_retorno;
    private Funcionario funcionario = new Funcionario();
    private Veiculo veiculo = new Veiculo();
    private String status;
    private String ocorrencia_saida;
    private String ocorrencia_chegada;
    private String data_viagem;
    private String adm_aprovador;
    private String portaria_saida;
    private String portaria_chegada;
    private CheckList check_list_saida = new CheckList();
    private CheckList check_list_chegada = new CheckList();
    private Boolean grava_hora;
    private String data_retorno;
    private String log_chegada;
    private String log_saida;
    private String usuario_cancelador;

    public String getUsuario_cancelador() {
        return usuario_cancelador;
    }

    public void setUsuario_cancelador(String usuario_cancelador) {
        this.usuario_cancelador = usuario_cancelador;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Endereco getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Endereco enderecos) {
        this.enderecos = enderecos;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getHr_saida() {
        return hr_saida;
    }

    public void setHr_saida(String hr_saida) {
        this.hr_saida = hr_saida;
    }

    public String getHr_retorno() {
        return hr_retorno;
    }

    public void setHr_retorno(String hr_retorno) {
        this.hr_retorno = hr_retorno;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData_viagem() {
        return data_viagem;
    }

    public void setData_viagem(String data_viagem) {
        this.data_viagem = data_viagem;
    }

    public String getAdm_aprovador() {
        return adm_aprovador;
    }

    public void setAdm_aprovador(String adm_aprovador) {
        this.adm_aprovador = adm_aprovador;
    }

    public String getPortaria_saida() {
        return portaria_saida;
    }

    public void setPortaria_saida(String portaria_saida) {
        this.portaria_saida = portaria_saida;
    }

    public String getPortaria_chegada() {
        return portaria_chegada;
    }

    public void setPortaria_chegada(String portaria_chegada) {
        this.portaria_chegada = portaria_chegada;
    }

    public String getOcorrencia_saida() {
        return ocorrencia_saida;
    }

    public void setOcorrencia_saida(String ocorrencia_saida) {
        this.ocorrencia_saida = ocorrencia_saida;
    }

    public String getOcorrencia_chegada() {
        return ocorrencia_chegada;
    }

    public void setOcorrencia_chegada(String ocorrencia_chegada) {
        this.ocorrencia_chegada = ocorrencia_chegada;
    }

    public CheckList getCheck_list_saida() {
        return check_list_saida;
    }

    public void setCheck_list_saida(CheckList check_list_saida) {
        this.check_list_saida = check_list_saida;
    }

    public CheckList getCheck_list_chegada() {
        return check_list_chegada;
    }

    public void setCheck_list_chegada(CheckList check_list_chegada) {
        this.check_list_chegada = check_list_chegada;
    }

    public Boolean getGrava_hora() {
        return grava_hora;
    }

    public void setGrava_hora(Boolean grava_hora) {
        this.grava_hora = grava_hora;
    }

    public String getData_retorno() {
        return data_retorno;
    }

    public void setData_retorno(String data_retorno) {
        this.data_retorno = data_retorno;
    }

    public String getLog_chegada() {
        return log_chegada;
    }

    public void setLog_chegada(String log_chegada) {
        this.log_chegada = log_chegada;
    }

    public String getLog_saida() {
        return log_saida;
    }

    public void setLog_saida(String log_saida) {
        this.log_saida = log_saida;
    }
    
    

}

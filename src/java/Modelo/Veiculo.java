/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Ramones
 */
public class Veiculo {
    
    private int id;
    private String renavam;
    private String placa;
    private String marca;
    private String modelo;
    private String cor;
    private Funcionario responsavel = new Funcionario();
    private Debitos_Veic debitos;
    private String data_tr_oleo;
    private String data_manut;
    private String status;
    private String ano;
    private String grupo;
    private int km_veic;
    private int km_veic_tr_oleo;
     
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Funcionario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Funcionario responsavel) {
        this.responsavel = responsavel;
    }

    

    public String getData_tr_oleo() {
        return data_tr_oleo;
    }

    public void setData_tr_oleo(String data_tr_oleo) {
        this.data_tr_oleo = data_tr_oleo;
    }

    public String getData_manut() {
        return data_manut;
    }

    public void setData_manut(String data_manut) {
        this.data_manut = data_manut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Debitos_Veic getDebitos() {
        return debitos;
    }

    public void setDebitos(Debitos_Veic debitos) {
        this.debitos = debitos;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public int getKm_veic() {
        return km_veic;
    }

    public void setKm_veic(int km_veic) {
        this.km_veic = km_veic;
    }

    public int getKm_veic_tr_oleo() {
        return km_veic_tr_oleo;
    }

    public void setKm_veic_tr_oleo(int km_veic_tr_oleo) {
        this.km_veic_tr_oleo = km_veic_tr_oleo;
    }
    
    
    
    
    
}

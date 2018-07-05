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
public class Debitos_Veic {
    private int id;
    private String[] multas;
    private String licenciamento;
    private String ipva;

    public String[] getMultas() {
        return multas;
    }

    public void setMultas(String[] multas) {
        this.multas = multas;
    }

    public String getLicenciamento() {
        return licenciamento;
    }

    public void setLicenciamento(String licenciamento) {
        this.licenciamento = licenciamento;
    }

    public String getIpva() {
        return ipva;
    }

    public void setIpva(String ipva) {
        this.ipva = ipva;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}

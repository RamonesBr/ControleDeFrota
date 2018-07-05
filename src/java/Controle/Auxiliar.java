/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import DAO.FuncionarioDAO;
import DAO.VeiculoDAO;
import DAO.ViagemDAO;
import Modelo.Funcionario;
import Modelo.Veiculo;
import Modelo.Viagem;
import Util.GravaLogs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acesso
 */
public class Auxiliar {

    public static List<Funcionario> funcionariosResponsaveis() throws IOException {
        List<Funcionario> f = new ArrayList<>();
        try {
            f = FuncionarioDAO.FuncionarioResponsavel();
        } catch (Exception e) {
            GravaLogs.registraLog("Erro no metodo que manda os funcionarios responsaveis para jsp adm_veiculos_cadastrar: " + e.getMessage());
        }
        return f;
    }
     public static List<Funcionario> funcionariosResponsaveisDepto(Funcionario funcionario) throws IOException {
        List<Funcionario> f = new ArrayList<>();
        try {
            f = FuncionarioDAO.FuncionarioResponsavelDepto(funcionario);
        } catch (Exception e) {
            GravaLogs.registraLog("Erro no metodo que manda os funcionarios responsaveis para jsp adm_veiculos_cadastrar: " + e.getMessage());
        }
        return f;
    }

    public static List<Veiculo> todosVeiculos() throws IOException {
        List<Veiculo> v = new ArrayList<>();
        try {
            v = VeiculoDAO.recuperaTodosVeiculos();
        } catch (Exception e) {
            GravaLogs.registraLog("Erro no metodo que recuperar todos os veiculos: " + e.getMessage());
        }

        return v;
    }

    public static List<Funcionario> todosFuncionarios() throws IOException {
        List<Funcionario> f = new ArrayList<>();
        try {
            f = FuncionarioDAO.todosFuncionarios();
        } catch (Exception e) {
            GravaLogs.registraLog("Erro no metodo que recuperar todos os funcionarios: " + e.getMessage());
        }

        return f;
    }

    public static List<Viagem> todasViagens() throws IOException {
        List<Viagem> v = new ArrayList<>();
        try {
            v = ViagemDAO.listaTodasViagens();
        } catch (Exception e) {
            GravaLogs.registraLog("Erro na controle.auxiliar no metodo todaViatens() : " + e.getMessage());
        }

        return v;
    }

    public static List<Veiculo> todosVeiculosDisponiveis() throws IOException {
        List<Veiculo> v = new ArrayList<>();
        try {
            v = VeiculoDAO.recuperaTodosVeiculosDisponiveis();
        } catch (Exception e) {
            GravaLogs.registraLog("Erro no metodo que recuperar todos os veiculos disponiveis: " + e.getMessage());
        }

        return v;
    }
    public static List<Veiculo> todosVeiculosDisponiveisPorDepto(Funcionario funcionario) throws IOException {
        List<Veiculo> v = new ArrayList<>();
        try {
            v = VeiculoDAO.recuperaTodosVeiculosDisponiveisPorDepto(funcionario);
        } catch (Exception e) {
            GravaLogs.registraLog("Erro no metodo que recuperar todos os veiculos disponiveis: " + e.getMessage());
        }

        return v;
    }

    public static List<Viagem> viagensPortaria() throws IOException {
        List<Viagem> v = new ArrayList<>();
        try {
            v = ViagemDAO.listaTodasViagensPortaria();
        } catch (Exception e) {
            GravaLogs.registraLog("Erro na controle.auxiliar no metodo todaViatens() : " + e.getMessage());
        }

        return v;
    }
    
    public static List<Viagem> viagensEncerradas() throws IOException {
        List<Viagem> v = new ArrayList<>();
        try {
            v = ViagemDAO.listaViagensEncerradas();
        } catch (Exception e) {
            GravaLogs.registraLog("Erro na controle.auxiliar no metodo todaViatens() : " + e.getMessage());
        }

        return v;
    }
     public static List<Veiculo> veiculosManutencao() throws IOException {
        List<Veiculo> v = new ArrayList<>();
        try {
            v = VeiculoDAO.veiculosManutencao();
        } catch (Exception e) {
            GravaLogs.registraLog("Erro no metodo que recuperar todos os veiculos para modulo manutenção: " + e.getMessage());
        }

        return v;
    }
     public static List<Veiculo> veiculosManutencaoPorDepto(Funcionario funcionario) throws IOException {
        List<Veiculo> v = new ArrayList<>();
        try {
            v = VeiculoDAO.veiculosManutencaoPorDepto(funcionario);
        } catch (Exception e) {
            GravaLogs.registraLog("Erro no metodo que recuperar todos os veiculos para modulo manutenção: " + e.getMessage());
        }

        return v;
    }
     public static List<Viagem> todasViagensAdmComum(Funcionario funcionario) throws IOException {
        List<Viagem> v = new ArrayList<>();
        try {
            v = ViagemDAO.listaTodasViagensPorGrupo(funcionario);
        } catch (Exception e) {
            GravaLogs.registraLog("Erro na controle.auxiliar no metodo todaViatensAdmComum() : " + e.getMessage());
        }

        return v;
    }
     
     public static List<Viagem> listaViagensFuncionarioComum(Funcionario funcionario ) throws IOException{
        List<Viagem> v = new ArrayList<>();
        try {
            v = ViagemDAO.listaViagensFuncionarioComum(funcionario);
        } catch (Exception e) {
            GravaLogs.registraLog("Erro na controle.auxiliar no metodo listaViagensFuncionarioComum() : " + e.getMessage());
        }

        return v;  
         
     }

}

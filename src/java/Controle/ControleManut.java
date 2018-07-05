/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import DAO.FuncionarioDAO;
import DAO.VeiculoDAO;
import Modelo.Email;
import Modelo.Funcionario;
import Modelo.Veiculo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author acesso
 */
public class ControleManut extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {

            List<Veiculo> v = new ArrayList<>();
            List<Funcionario> f = new ArrayList<>();

            v = VeiculoDAO.recuperaTodosVeiculos();

            for (int i = 0; i < v.size(); i++) {

                int kmTrFinalI = v.get(i).getKm_veic_tr_oleo() + 4000;
                int kmTrFinalF = v.get(i).getKm_veic_tr_oleo() + 5000;
                
                 f = FuncionarioDAO.verificaAdm();
                 
                  
               
                if (VeiculoDAO.verificaDataOleo(v.get(i))) {
                    
                    for (int j = 0; j < f.size(); j++) {
                        String email = f.get(j).getEmail();
                        String nome = f.get(j).getNome();
                       
                        Email e = new Email();
                        e.setNomeDestinatario(nome);
                        e.setEmailDestinatario(email);
                        e.setAssunto("VLOG INFORMA");
                        e.setMensagem("A data da troca de oleo do veiculo placa: "+v.get(i).getPlaca()+" !\n "
                                + "Modelo: "+v.get(i).getModelo()+"\n"
                                + "chegou, por favor realize a troca ! \n"
                                + "Acesso o módulo de manutenção: http://localhost:8080/ControleDeFrota/adm_manutencao_veic.jsp");
                        e.enviar();

                    }

                } 
                
                if (v.get(i).getKm_veic() >= kmTrFinalI && v.get(i).getKm_veic() <= kmTrFinalF) {

                    int kmRestante = kmTrFinalF - v.get(i).getKm_veic()  ;
                    
                    for (int j = 0; j < f.size(); j++) {
                        String email = f.get(j).getEmail();
                        String nomeCli = f.get(j).getNome();
                    
                      
                        Email e = new Email();
                        e.setNomeDestinatario(nomeCli);
                        e.setEmailDestinatario(email);
                        e.setAssunto("VLOG INFORMA");
                        e.setMensagem("O veiculo placa: "+v.get(i).getPlaca()+" Modelo: "+v.get(i).getModelo()+"\n"
                                + "atingiu a km: "+v.get(i).getKm_veic()+"\n"
                                + "e faltam km:"+kmRestante+" para atingir o limite da troca \n" 
                                + "Acesso o módulo de manutenção para realizar a troca: \n"
                                + " http://localhost:8080/ControleDeFrota/adm_principal.jsp");
                        e.enviar();
                    }
                    

                } else if (v.get(i).getKm_veic() > kmTrFinalF) {
                    
                    for (int j = 0; j < f.size(); j++) {
                        String email = f.get(j).getEmail();
                        String nomeCli = f.get(j).getNome();
                        
                     Email e = new Email();
                        e.setNomeDestinatario(nomeCli);
                        e.setEmailDestinatario(email);
                        e.setAssunto("VLOG INFORMA");
                        e.setMensagem("O veiculo placa: "+v.get(i).getPlaca()+" Modelo: "+v.get(i).getModelo()+"\n"
                                + "atingiu a km limite para troca de óleo, e foi indisponibilizado  \n"
                                + "Acesso o módulo de manutenção para realizar a troca e disponibilizar o veiculo novamente: \n"
                                + " http://localhost:8080/ControleDeFrota/adm_principal.jsp");
                        e.enviar();
                        
                    }
                      
                      v.get(i).setStatus("MANUTENCAO");
                      VeiculoDAO.alteraStatusVeiculo(v.get(i));
                    
                }
                
                
            }

        } catch (Exception e) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}

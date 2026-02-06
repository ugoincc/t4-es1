package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import unioeste.geral.endereco.bo.Endereco;
import unioeste.geral.endereco.manager.UCEnderecoGeralServicos;

@WebServlet(name = "TesteEnderecoServlet", urlPatterns = {"/teste-endereco"})
public class TesteEnderecoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        UCEnderecoGeralServicos servico = new UCEnderecoGeralServicos();
        String resultado = "";
        
        try {
            // Teste: Busca CEP na API Externa
            Endereco end = servico.obterEnderecoExterno("85869390"); 
            resultado = "Encontrado API: " + end.getLogradouro().getNome() + ", " + end.getBairro().getNome();
            
            // Aqui você adicionaria o código para tentar SALVAR (cadastrarEndereco)
            
        } catch (Exception e) {
            resultado = "Erro: " + e.getMessage();
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Teste T3B</title></head><body>");
            out.println("<h1>Teste Serviço Endereço</h1>");
            out.println("<p>" + resultado + "</p>");
            out.println("</body></html>");
        }
    }
}
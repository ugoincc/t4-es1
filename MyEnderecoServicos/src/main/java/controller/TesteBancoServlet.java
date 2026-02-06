package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import unioeste.geral.endereco.bo.*;
import unioeste.geral.endereco.manager.UCEnderecoGeralServicos;

@WebServlet(name = "TesteBancoServlet", urlPatterns = {"/teste-banco"})
public class TesteBancoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        UCEnderecoGeralServicos servico = new UCEnderecoGeralServicos();
        
        try {
            out.println("<html><body>");
            out.println("<h1>Teste CRUD Banco de Dados</h1>");
            
            // ===============================================================
            // TESTE 1: Cadastrar um Novo Endereço (INSERT)
            // ===============================================================
            out.println("<h3>1. Tentando Cadastrar Endereço...</h3>");
            
// ... (dentro do try do TesteBancoServlet)

            Endereco novoEnd = new Endereco();
            novoEnd.setCep("99999000"); 

            // 1. Configurando Cidade (Completa para passar no CidadeCOL)
            Cidade cid = new Cidade(); 
            cid.setIdCidade(1);
            cid.setNome("Foz do Iguaçu"); // Necessário para validação
            
            UnidadeFederacao uf = new UnidadeFederacao();
            uf.setSigla("PR");
            uf.setNome("Paraná");
            cid.setUf(uf); // Necessário pois CidadeCOL valida o UF
            
            novoEnd.setCidade(cid);

            // 2. Configurando Bairro (Completo)
            Bairro bai = new Bairro(); 
            bai.setIdBairro(1); 
            bai.setNome("Centro"); // Necessário
            novoEnd.setBairro(bai);

            // 3. Configurando Logradouro e Tipo (Completos)
            Logradouro log = new Logradouro(); 
            log.setIdLogradouro(1); 
            log.setNome("Brasil"); 
            
            TipoLogradouro tipo = new TipoLogradouro();
            tipo.setCod(2);
            tipo.setNome("Avenida");
            log.setTipo(tipo); // Necessário pois LogradouroCOL valida o Tipo
            
            novoEnd.setLogradouro(log);
            
            // Chama o serviço
//            Endereco endCadastrado = servico.cadastrarEndereco(novoEnd);
//            out.println("<p style='color:green'>Sucesso! Endereço cadastrado.</p>");
//            out.println("ID Gerado: " + endCadastrado.getIdEndereco() + "<br>");
//            out.println("CEP: " + endCadastrado.getCep() + "<br>");
            // ===============================================================
            // TESTE 2: Consultar por ID (SELECT)
            // ===============================================================
            out.println("<h3>2. Consultando o endereço recém criado pelo ID...</h3>");
            
            
            Endereco endConsultado = servico.obterEnderecoPorID(1);

            //Endereco endConsultado = servico.obterEnderecoPorID(endCadastrado.getIdEndereco());
            
            if(endConsultado != null){
                out.println("<p>Endereço recuperado do banco:</p>");
                out.println("Cidade: " + endConsultado.getCidade().getNome() + "<br>");
                out.println("Bairro: " + endConsultado.getBairro().getNome() + "<br>");
                out.println("Logradouro: " + endConsultado.getLogradouro().getNome() + "<br>");
            } else {
                out.println("<p style='color:red'>Erro: Retornou nulo ao buscar por ID.</p>");
            }

        } catch (Exception e) {
            out.println("<h2 style='color:red'>ERRO FATAL:</h2>");
            out.println("<pre>");
            e.printStackTrace(out); 
            out.println("</pre>");
        } finally {
            out.println("</body></html>");
        }
    }
}
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="unioeste.geral.receita.bo.*"%>
<%@page import="unioeste.geral.pessoa.bo.*"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
    <title>Protótipo T3A - Receita Médica</title>
</head>
<body>
    <h1>Protótipo: Emissão de Receita</h1>
    <%
        // 1. Instanciando os Atores (Simulação)
        Medico medico = new Medico();
        medico.setNome("Dr. Gregory House");
        medico.setCrm("12345-PR");
        
        Paciente paciente = new Paciente();
        paciente.setNome("João da Silva");
        paciente.setCpf("000.111.222-33");
        
        // 2. Criando a Receita
        ReceitaMedica receita = new ReceitaMedica();
        receita.setNumeroReceita(1001);
        receita.setDataEmissao(new Date());
        receita.setMedico(medico);
        receita.setPaciente(paciente);
        
        // 3. Adicionando Itens (Prescrição)
        Medicamento med1 = new Medicamento();
        med1.setNomeGenerico("Dipirona 500mg");
        
        Prescricao presc1 = new Prescricao();
        presc1.setMedicamento(med1);
        presc1.setPosologia("1 comprimido a cada 6 horas se houver dor");
        presc1.setPeriodoUso("3 dias");
        
        receita.adicionarPrescricao(presc1);
    %>
    
    <h3>Dados da Receita Gerada (Objeto Java):</h3>
    <p><b>Médico:</b> <%= receita.getMedico().getNome() %> (CRM: <%= receita.getMedico().getCrm() %>)</p>
    <p><b>Paciente:</b> <%= receita.getPaciente().getNome() %></p>
    <p><b>Data:</b> <%= receita.getDataEmissao() %></p>
    
    <hr/>
    <h3>Itens Prescritos:</h3>
    <ul>
    <% for(Prescricao p : receita.getPrescricoes()) { %>
        <li>
            <b><%= p.getMedicamento().getNomeGenerico() %></b>: 
            <%= p.getPosologia() %> (Por: <%= p.getPeriodoUso() %>)
        </li>
    <% } %>
    </ul>
</body>
</html>
# API - Sistema de Servicos

## Servicos

| Servico | Base URL |
|---------|----------|
| MyEnderecoServicos | `http://localhost:8080/MyEnderecoServicos/resources` |
| MyOrdemServicos | `http://localhost:8080/MyOrdemServicos/resources` |
| MyReceitaServicos | `http://localhost:8080/MyReceitaServicos/resources` |

---

## Endpoints

### MyEnderecoServicos

| Metodo | Endpoint | Descricao |
|--------|----------|-----------|
| GET | `/endereco/id/{id}` | Buscar endereco por ID |
| GET | `/endereco/cep/{cep}` | Buscar endereco por CEP |
| GET | `/endereco/cidade/{id}` | Buscar cidade por ID |
| GET | `/endereco/externo/{cep}` | Consultar CEP via ViaCEP |
| POST | `/endereco/criar` | Cadastrar endereco |

### MyOrdemServicos

| Metodo | Endpoint | Descricao |
|--------|----------|-----------|
| GET | `/cliente/cpf/{cpf}` | Buscar cliente por CPF |
| POST | `/cliente/criar` | Cadastrar novo cliente |
| GET | `/atendente` | Listar atendentes |
| GET | `/atendente/{id}` | Buscar atendente por ID |
| GET | `/servico` | Listar servicos |
| GET | `/ordem-servico` | Listar ordens de servico |
| GET | `/ordem-servico/{nro}` | Buscar OS por numero |
| POST | `/ordem-servico/criar` | Criar ordem de servico |

### MyReceitaServicos

| Metodo | Endpoint | Descricao |
|--------|----------|-----------|
| GET | `/paciente/cpf/{cpf}` | Buscar paciente por CPF |
| POST | `/paciente/criar` | Cadastrar novo paciente |
| GET | `/medico` | Listar medicos |
| GET | `/medico/{id}` | Buscar medico por ID |
| GET | `/cid/{codigo}` | Buscar CID por codigo |
| GET | `/medicamento` | Listar medicamentos |
| GET | `/medicamento?nome={nome}` | Filtrar medicamentos |
| GET | `/receita-medica` | Listar receitas medicas |
| GET | `/receita-medica/{numero}` | Buscar receita por numero |
| POST | `/receita-medica/criar` | Criar receita medica |

---

## Exemplos de Requisicao

### Criar Ordem de Servico
```json
POST /ordem-servico/criar
{
  "descricao": "Instalacao de rede",
  "cliente": { "idPessoa": 1 },
  "atendente": { "idPessoa": 1 },
  "servicos": [{ "cod": 3 }, { "cod": 7 }]
}
```

### Criar Receita Medica
```json
POST /receita-medica/criar
{
  "paciente": { "idPessoa": 1 },
  "medico": { "idPessoa": 1 },
  "cid": { "codigo": "J00" },
  "prescricoes": [{
    "medicamento": { "idMedicamento": 1 },
    "posologia": "1 comp a cada 6h",
    "periodoUso": "5 dias"
  }]
}
```

### Cadastrar Paciente
```json
POST /paciente/criar
{
  "cpf": "99988877766",
  "nome": "Fulano de Tal",
  "emails": [{ "enderecoEmail": "fulano@email.com" }],
  "telefones": [{ "numero": "999887766", "ddd": { "codigo": "45" } }],
  "endereco": {
    "numero": 100,
    "complemento": "Apto 1",
    "endereco": {
      "cep": "85801000",
      "logradouro": { "nome": "Rua Brasil" },
      "bairro": { "nome": "Centro" },
      "cidade": { "nome": "Cascavel", "uf": { "sigla": "PR" } }
    }
  }
}
```

### Cadastrar Cliente
```json
POST /cliente/criar
{
  "cpf": "99988877766",
  "nome": "Fulano de Tal",
  "emails": [{ "enderecoEmail": "fulano@email.com" }],
  "telefones": [{ "numero": "999887766", "ddd": { "codigo": "45" } }],
  "endereco": {
    "numero": 100,
    "complemento": "Apto 1",
    "endereco": {
      "cep": "85801000",
      "logradouro": { "nome": "Rua Brasil" },
      "bairro": { "nome": "Centro" },
      "cidade": { "nome": "Cascavel", "uf": { "sigla": "PR" } }
    }
  }
}
```

### Cadastrar Endereco
```json
POST /endereco/criar
{
  "cep": "85801000",
  "logradouro": { "idLogradouro": 1 },
  "bairro": { "idBairro": 1 },
  "cidade": { "idCidade": 1 }
}
```

---

## Dados de Teste

### Clientes/Pacientes (CPF)
| CPF | Nome |
|-----|------|
| 12345678901 | Maria Silva Santos |
| 23456789012 | Joao Pedro Oliveira |
| 34567890123 | Ana Paula Costa |

### Servicos
| Cod | Tipo | Valor |
|-----|------|-------|
| 1 | Consultoria Tecnica | 150.00 |
| 2 | Manutencao Preventiva | 200.00 |
| 3 | Instalacao de Equipamento | 350.00 |
| 4 | Suporte Remoto | 80.00 |
| 5 | Treinamento | 500.00 |
| 6 | Reparo de Hardware | 250.00 |
| 7 | Configuracao de Rede | 180.00 |

### CIDs
| Codigo | Descricao |
|--------|-----------|
| J00 | Nasofaringite aguda |
| J06 | Infeccoes vias aereas |
| J11 | Influenza |
| G43 | Enxaqueca |
| I10 | Hipertensao |
| E11 | Diabetes tipo 2 |

### Medicamentos
| ID | Nome | Fabricante |
|----|------|------------|
| 1 | Paracetamol 750mg | Medley |
| 2 | Ibuprofeno 600mg | EMS |
| 3 | Amoxicilina 500mg | Eurofarma |
| 4 | Omeprazol 20mg | Germed |
| 5 | Dipirona 500mg | Sanofi |
| 6 | Loratadina 10mg | EMS |

---

## Codigos HTTP

| Codigo | Descricao |
|--------|-----------|
| 200 | Sucesso |
| 400 | Requisicao invalida |
| 404 | Nao encontrado |
| 500 | Erro interno |

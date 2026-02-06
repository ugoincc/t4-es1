-- ============================================
-- Script de Inicializacao do Banco de Dados
-- Database: t3-es1 (PostgreSQL)
-- ============================================

-- Criar banco de dados (executar separadamente se necessario)
-- CREATE DATABASE "t3-es1";

-- ============================================
-- TABELAS DE ENDERECO
-- ============================================

CREATE TABLE IF NOT EXISTS unidade_federacao (
    id_uf SERIAL PRIMARY KEY,
    sigla VARCHAR(2) NOT NULL UNIQUE,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS cidade (
    id_cidade SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    id_uf INTEGER REFERENCES unidade_federacao(id_uf)
);

CREATE TABLE IF NOT EXISTS tipo_logradouro (
    id_tipo_logradouro SERIAL PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS bairro (
    id_bairro SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    id_cidade INTEGER REFERENCES cidade(id_cidade)
);

CREATE TABLE IF NOT EXISTS logradouro (
    id_logradouro SERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    id_tipo_logradouro INTEGER REFERENCES tipo_logradouro(id_tipo_logradouro)
);

CREATE TABLE IF NOT EXISTS endereco (
    id_endereco SERIAL PRIMARY KEY,
    cep VARCHAR(8) NOT NULL,
    id_logradouro INTEGER REFERENCES logradouro(id_logradouro),
    id_bairro INTEGER REFERENCES bairro(id_bairro),
    id_cidade INTEGER REFERENCES cidade(id_cidade)
);

-- ============================================
-- TABELAS DE PESSOA
-- ============================================

CREATE TABLE IF NOT EXISTS pessoa (
    id_pessoa SERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    nome_social VARCHAR(200),
    id_endereco INTEGER REFERENCES endereco(id_endereco),
    numero_casa INTEGER,
    complemento VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS pessoa_fisica (
    id_pessoa INTEGER PRIMARY KEY REFERENCES pessoa(id_pessoa),
    cpf VARCHAR(11) NOT NULL UNIQUE,
    primeiro_nome VARCHAR(100),
    sobrenome VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS email (
    id_email SERIAL PRIMARY KEY,
    id_pessoa INTEGER REFERENCES pessoa(id_pessoa),
    email VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS telefone (
    id_telefone SERIAL PRIMARY KEY,
    id_pessoa INTEGER REFERENCES pessoa(id_pessoa),
    telefone VARCHAR(20) NOT NULL
);

-- ============================================
-- TABELAS DE ORDEM DE SERVICO
-- ============================================

CREATE TABLE IF NOT EXISTS cliente (
    id_pessoa INTEGER PRIMARY KEY REFERENCES pessoa_fisica(id_pessoa)
);

CREATE TABLE IF NOT EXISTS atendente (
    id_pessoa INTEGER PRIMARY KEY REFERENCES pessoa_fisica(id_pessoa)
);

CREATE TABLE IF NOT EXISTS servico (
    cod_servico SERIAL PRIMARY KEY,
    tipo_servico VARCHAR(100) NOT NULL,
    valor DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS ordem_servico (
    nro_ordem SERIAL PRIMARY KEY,
    descricao TEXT,
    data_emissao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL DEFAULT 0,
    id_cliente INTEGER REFERENCES cliente(id_pessoa),
    id_atendente INTEGER REFERENCES atendente(id_pessoa)
);

CREATE TABLE IF NOT EXISTS ordem_servico_servico (
    nro_ordem INTEGER REFERENCES ordem_servico(nro_ordem),
    cod_servico INTEGER REFERENCES servico(cod_servico),
    PRIMARY KEY (nro_ordem, cod_servico)
);

-- ============================================
-- TABELAS DE RECEITA MEDICA
-- ============================================

CREATE TABLE IF NOT EXISTS medico (
    id_pessoa INTEGER PRIMARY KEY REFERENCES pessoa_fisica(id_pessoa),
    crm VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS paciente (
    id_pessoa INTEGER PRIMARY KEY REFERENCES pessoa_fisica(id_pessoa)
);

CREATE TABLE IF NOT EXISTS cid (
    codigo VARCHAR(10) PRIMARY KEY,
    descricao TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS medicamento (
    id_medicamento SERIAL PRIMARY KEY,
    nome_generico VARCHAR(200) NOT NULL,
    fabricante VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS receita_medica (
    numero_receita SERIAL PRIMARY KEY,
    data_emissao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_medico INTEGER REFERENCES medico(id_pessoa),
    id_paciente INTEGER REFERENCES paciente(id_pessoa),
    codigo_cid VARCHAR(10) REFERENCES cid(codigo)
);

CREATE TABLE IF NOT EXISTS prescricao (
    id_prescricao SERIAL PRIMARY KEY,
    numero_receita INTEGER REFERENCES receita_medica(numero_receita),
    id_medicamento INTEGER REFERENCES medicamento(id_medicamento),
    posologia VARCHAR(500),
    periodo_uso VARCHAR(200)
);

-- ============================================
-- DADOS DE EXEMPLO - ENDERECO
-- ============================================

INSERT INTO unidade_federacao (sigla, nome) VALUES
('PR', 'Parana'),
('SP', 'Sao Paulo'),
('RJ', 'Rio de Janeiro'),
('MG', 'Minas Gerais'),
('SC', 'Santa Catarina');

INSERT INTO cidade (nome, id_uf) VALUES
('Cascavel', 1),
('Curitiba', 1),
('Sao Paulo', 2),
('Rio de Janeiro', 3),
('Belo Horizonte', 4);

INSERT INTO tipo_logradouro (descricao) VALUES
('Rua'),
('Avenida'),
('Travessa'),
('Alameda'),
('Praca');

INSERT INTO bairro (nome, id_cidade) VALUES
('Centro', 1),
('Centro', 2),
('Alto Alegre', 1),
('Santa Cruz', 1),
('Jardim Paulista', 3);

INSERT INTO logradouro (nome, id_tipo_logradouro) VALUES
('Brasil', 1),
('Parana', 2),
('das Flores', 1),
('XV de Novembro', 1),
('Paulista', 2);

INSERT INTO endereco (cep, id_logradouro, id_bairro, id_cidade) VALUES
('85801000', 1, 1, 1),
('85802100', 2, 3, 1),
('80010000', 4, 2, 2),
('01310100', 5, 5, 3),
('85803200', 3, 4, 1);

-- ============================================
-- DADOS DE EXEMPLO - PESSOAS
-- ============================================

INSERT INTO pessoa (nome, nome_social, id_endereco, numero_casa, complemento) VALUES
('Maria Silva Santos', NULL, 1, 100, 'Apto 201'),
('Joao Pedro Oliveira', NULL, 2, 250, NULL),
('Ana Paula Costa', NULL, 3, 500, 'Casa'),
('Carlos Eduardo Lima', NULL, 4, 1500, 'Sala 301'),
('Fernanda Souza Almeida', NULL, 5, 75, NULL),
('Dr. Roberto Mendes', NULL, 1, 200, 'Consultorio 5'),
('Paulo Henrique Dias', NULL, 2, 300, NULL);

INSERT INTO pessoa_fisica (id_pessoa, cpf, primeiro_nome, sobrenome) VALUES
(1, '12345678901', 'Maria', 'Santos'),
(2, '23456789012', 'Joao', 'Oliveira'),
(3, '34567890123', 'Ana', 'Costa'),
(4, '45678901234', 'Carlos', 'Lima'),
(5, '56789012345', 'Fernanda', 'Almeida'),
(6, '67890123456', 'Roberto', 'Mendes'),
(7, '78901234567', 'Paulo', 'Dias');

INSERT INTO email (id_pessoa, email) VALUES
(1, 'maria.silva@email.com'),
(2, 'joao.oliveira@email.com'),
(3, 'ana.costa@email.com'),
(6, 'dr.roberto@clinica.com');

INSERT INTO telefone (id_pessoa, telefone) VALUES
(1, '45999001122'),
(2, '45988112233'),
(3, '41977223344'),
(6, '45966334455');

-- ============================================
-- DADOS DE EXEMPLO - ORDEM DE SERVICO
-- ============================================

INSERT INTO cliente (id_pessoa) VALUES (1), (2), (3);

INSERT INTO atendente (id_pessoa) VALUES (4), (5);

INSERT INTO servico (tipo_servico, valor) VALUES
('Consultoria Tecnica', 150.00),
('Manutencao Preventiva', 200.00),
('Instalacao de Equipamento', 350.00),
('Suporte Remoto', 80.00),
('Treinamento', 500.00),
('Reparo de Hardware', 250.00),
('Configuracao de Rede', 180.00);

INSERT INTO ordem_servico (descricao, data_emissao, total, id_cliente, id_atendente) VALUES
('Instalacao de computadores novos', '2024-01-15 10:30:00', 550.00, 1, 4),
('Manutencao mensal de servidores', '2024-01-20 14:00:00', 200.00, 2, 5);

INSERT INTO ordem_servico_servico (nro_ordem, cod_servico) VALUES
(1, 3), (1, 4),
(2, 2);

-- ============================================
-- DADOS DE EXEMPLO - RECEITA MEDICA
-- ============================================

INSERT INTO medico (id_pessoa, crm) VALUES (6, 'CRM-PR-12345');

INSERT INTO paciente (id_pessoa) VALUES (1), (2), (7);

INSERT INTO cid (codigo, descricao) VALUES
('J00', 'Nasofaringite aguda (resfriado comum)'),
('J06', 'Infeccoes agudas das vias aereas superiores'),
('J11', 'Influenza (gripe) devida a virus nao identificado'),
('R50', 'Febre de origem desconhecida'),
('K30', 'Dispepsia funcional'),
('M54', 'Dorsalgia (dor nas costas)'),
('G43', 'Enxaqueca'),
('I10', 'Hipertensao essencial (primaria)'),
('E11', 'Diabetes mellitus tipo 2'),
('F32', 'Episodio depressivo');

INSERT INTO medicamento (nome_generico, fabricante) VALUES
('Paracetamol 750mg', 'Medley'),
('Ibuprofeno 600mg', 'EMS'),
('Amoxicilina 500mg', 'Eurofarma'),
('Omeprazol 20mg', 'Germed'),
('Dipirona 500mg', 'Sanofi'),
('Loratadina 10mg', 'EMS'),
('Azitromicina 500mg', 'Medley'),
('Metformina 850mg', 'Merck'),
('Losartana 50mg', 'Biosintetica'),
('Sertralina 50mg', 'Eurofarma');

INSERT INTO receita_medica (data_emissao, id_medico, id_paciente, codigo_cid) VALUES
('2024-01-25 09:00:00', 6, 1, 'J00'),
('2024-01-26 11:30:00', 6, 2, 'G43');

INSERT INTO prescricao (numero_receita, id_medicamento, posologia, periodo_uso) VALUES
(1, 1, 'Tomar 1 comprimido a cada 6 horas se houver dor ou febre', '5 dias'),
(1, 6, 'Tomar 1 comprimido ao dia', '7 dias'),
(2, 2, 'Tomar 1 comprimido a cada 8 horas durante as crises', '3 dias');

-- ============================================
-- VIEWS UTEIS PARA CONSULTA
-- ============================================

CREATE OR REPLACE VIEW v_clientes AS
SELECT
    pf.id_pessoa,
    pf.cpf,
    p.nome,
    p.nome_social,
    e.cep,
    l.nome as logradouro,
    b.nome as bairro,
    c.nome as cidade,
    uf.sigla as uf,
    p.numero_casa,
    p.complemento
FROM cliente cl
JOIN pessoa_fisica pf ON cl.id_pessoa = pf.id_pessoa
JOIN pessoa p ON pf.id_pessoa = p.id_pessoa
LEFT JOIN endereco e ON p.id_endereco = e.id_endereco
LEFT JOIN logradouro l ON e.id_logradouro = l.id_logradouro
LEFT JOIN bairro b ON e.id_bairro = b.id_bairro
LEFT JOIN cidade c ON e.id_cidade = c.id_cidade
LEFT JOIN unidade_federacao uf ON c.id_uf = uf.id_uf;

CREATE OR REPLACE VIEW v_pacientes AS
SELECT
    pf.id_pessoa,
    pf.cpf,
    p.nome,
    p.nome_social,
    e.cep,
    l.nome as logradouro,
    b.nome as bairro,
    c.nome as cidade,
    uf.sigla as uf,
    p.numero_casa,
    p.complemento
FROM paciente pa
JOIN pessoa_fisica pf ON pa.id_pessoa = pf.id_pessoa
JOIN pessoa p ON pf.id_pessoa = p.id_pessoa
LEFT JOIN endereco e ON p.id_endereco = e.id_endereco
LEFT JOIN logradouro l ON e.id_logradouro = l.id_logradouro
LEFT JOIN bairro b ON e.id_bairro = b.id_bairro
LEFT JOIN cidade c ON e.id_cidade = c.id_cidade
LEFT JOIN unidade_federacao uf ON c.id_uf = uf.id_uf;

-- ============================================
-- FIM DO SCRIPT
-- ============================================

SELECT 'Banco de dados inicializado com sucesso!' AS status;

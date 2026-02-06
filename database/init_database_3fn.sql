-- ============================================
-- Script de Inicializacao do Banco de Dados
-- Database: t3-es1 (PostgreSQL)
-- Terceira Forma Normal
-- ============================================

-- Limpar tabelas existentes
DROP TABLE IF EXISTS prescricao CASCADE;
DROP TABLE IF EXISTS receita_medica CASCADE;
DROP TABLE IF EXISTS ordem_servico_servico CASCADE;
DROP TABLE IF EXISTS ordem_servico CASCADE;
DROP TABLE IF EXISTS servico CASCADE;
DROP TABLE IF EXISTS medicamento CASCADE;
DROP TABLE IF EXISTS cid CASCADE;
DROP TABLE IF EXISTS cliente_telefone CASCADE;
DROP TABLE IF EXISTS cliente_email CASCADE;
DROP TABLE IF EXISTS paciente_telefone CASCADE;
DROP TABLE IF EXISTS paciente_email CASCADE;
DROP TABLE IF EXISTS medico_telefone CASCADE;
DROP TABLE IF EXISTS medico_email CASCADE;
DROP TABLE IF EXISTS atendente_telefone CASCADE;
DROP TABLE IF EXISTS atendente_email CASCADE;
DROP TABLE IF EXISTS telefone CASCADE;
DROP TABLE IF EXISTS email CASCADE;
DROP TABLE IF EXISTS ddd CASCADE;
DROP TABLE IF EXISTS ddi CASCADE;
DROP TABLE IF EXISTS medico CASCADE;
DROP TABLE IF EXISTS paciente CASCADE;
DROP TABLE IF EXISTS atendente CASCADE;
DROP TABLE IF EXISTS cliente CASCADE;
DROP TABLE IF EXISTS endereco CASCADE;
DROP TABLE IF EXISTS logradouro CASCADE;
DROP TABLE IF EXISTS tipo_logradouro CASCADE;
DROP TABLE IF EXISTS bairro CASCADE;
DROP TABLE IF EXISTS cidade CASCADE;
DROP TABLE IF EXISTS estado CASCADE;

-- ============================================
-- TABELAS DE ENDERECO
-- ============================================

CREATE TABLE estado (
    sigla_estado VARCHAR(2) PRIMARY KEY,
    nome_estado VARCHAR(50) NOT NULL
);

CREATE TABLE cidade (
    id_cidade SERIAL PRIMARY KEY,
    nome_cidade VARCHAR(100) NOT NULL,
    sigla_estado VARCHAR(2) REFERENCES estado(sigla_estado)
);

CREATE TABLE tipo_logradouro (
    id_tipo_logradouro SERIAL PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL
);

CREATE TABLE bairro (
    id_bairro SERIAL PRIMARY KEY,
    nome_bairro VARCHAR(100) NOT NULL,
    id_cidade INTEGER REFERENCES cidade(id_cidade)
);

CREATE TABLE logradouro (
    id_logradouro SERIAL PRIMARY KEY,
    nome_logradouro VARCHAR(200) NOT NULL,
    id_tipo_logradouro INTEGER REFERENCES tipo_logradouro(id_tipo_logradouro)
);

CREATE TABLE endereco (
    id_endereco SERIAL PRIMARY KEY,
    cep VARCHAR(8) NOT NULL,
    id_logradouro INTEGER REFERENCES logradouro(id_logradouro),
    id_bairro INTEGER REFERENCES bairro(id_bairro),
    id_cidade INTEGER REFERENCES cidade(id_cidade)
);

-- ============================================
-- TABELAS DE TELEFONE (DDI -> DDD -> Telefone)
-- ============================================

CREATE TABLE ddi (
    id_ddi SERIAL PRIMARY KEY,
    codigo VARCHAR(5) NOT NULL UNIQUE,
    pais VARCHAR(100) NOT NULL
);

CREATE TABLE ddd (
    id_ddd SERIAL PRIMARY KEY,
    codigo VARCHAR(3) NOT NULL,
    id_ddi INTEGER REFERENCES ddi(id_ddi),
    regiao VARCHAR(100)
);

CREATE TABLE telefone (
    id_telefone SERIAL PRIMARY KEY,
    numero VARCHAR(15) NOT NULL,
    id_ddd INTEGER REFERENCES ddd(id_ddd)
);

-- ============================================
-- TABELA DE EMAIL
-- ============================================

CREATE TABLE email (
    id_email SERIAL PRIMARY KEY,
    endereco_email VARCHAR(200) NOT NULL
);

-- ============================================
-- TABELAS DE ENTIDADES
-- ============================================

CREATE TABLE cliente (
    id_cliente SERIAL PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    nome VARCHAR(200) NOT NULL,
    nome_social VARCHAR(200),
    id_endereco INTEGER REFERENCES endereco(id_endereco),
    numero_casa INTEGER,
    complemento VARCHAR(100)
);

CREATE TABLE atendente (
    id_atendente SERIAL PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    nome VARCHAR(200) NOT NULL,
    nome_social VARCHAR(200),
    id_endereco INTEGER REFERENCES endereco(id_endereco),
    numero_casa INTEGER,
    complemento VARCHAR(100)
);

CREATE TABLE paciente (
    id_paciente SERIAL PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    nome VARCHAR(200) NOT NULL,
    nome_social VARCHAR(200),
    id_endereco INTEGER REFERENCES endereco(id_endereco),
    numero_casa INTEGER,
    complemento VARCHAR(100)
);

CREATE TABLE medico (
    id_medico SERIAL PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    nome VARCHAR(200) NOT NULL,
    nome_social VARCHAR(200),
    crm VARCHAR(20) NOT NULL,
    id_endereco INTEGER REFERENCES endereco(id_endereco),
    numero_casa INTEGER,
    complemento VARCHAR(100)
);

-- ============================================
-- TABELAS DE RELACIONAMENTO (Telefone/Email)
-- ============================================

-- Cliente
CREATE TABLE cliente_telefone (
    id_cliente INTEGER REFERENCES cliente(id_cliente),
    id_telefone INTEGER REFERENCES telefone(id_telefone),
    PRIMARY KEY (id_cliente, id_telefone)
);

CREATE TABLE cliente_email (
    id_cliente INTEGER REFERENCES cliente(id_cliente),
    id_email INTEGER REFERENCES email(id_email),
    PRIMARY KEY (id_cliente, id_email)
);

-- Paciente
CREATE TABLE paciente_telefone (
    id_paciente INTEGER REFERENCES paciente(id_paciente),
    id_telefone INTEGER REFERENCES telefone(id_telefone),
    PRIMARY KEY (id_paciente, id_telefone)
);

CREATE TABLE paciente_email (
    id_paciente INTEGER REFERENCES paciente(id_paciente),
    id_email INTEGER REFERENCES email(id_email),
    PRIMARY KEY (id_paciente, id_email)
);

-- Medico
CREATE TABLE medico_telefone (
    id_medico INTEGER REFERENCES medico(id_medico),
    id_telefone INTEGER REFERENCES telefone(id_telefone),
    PRIMARY KEY (id_medico, id_telefone)
);

CREATE TABLE medico_email (
    id_medico INTEGER REFERENCES medico(id_medico),
    id_email INTEGER REFERENCES email(id_email),
    PRIMARY KEY (id_medico, id_email)
);

-- Atendente
CREATE TABLE atendente_telefone (
    id_atendente INTEGER REFERENCES atendente(id_atendente),
    id_telefone INTEGER REFERENCES telefone(id_telefone),
    PRIMARY KEY (id_atendente, id_telefone)
);

CREATE TABLE atendente_email (
    id_atendente INTEGER REFERENCES atendente(id_atendente),
    id_email INTEGER REFERENCES email(id_email),
    PRIMARY KEY (id_atendente, id_email)
);

-- ============================================
-- TABELAS DE ORDEM DE SERVICO
-- ============================================

CREATE TABLE servico (
    cod_servico SERIAL PRIMARY KEY,
    tipo_servico VARCHAR(100) NOT NULL,
    valor DECIMAL(10,2) NOT NULL
);

CREATE TABLE ordem_servico (
    nro_ordem SERIAL PRIMARY KEY,
    descricao TEXT,
    data_emissao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL DEFAULT 0,
    id_cliente INTEGER REFERENCES cliente(id_cliente),
    id_atendente INTEGER REFERENCES atendente(id_atendente)
);

CREATE TABLE ordem_servico_servico (
    nro_ordem INTEGER REFERENCES ordem_servico(nro_ordem),
    cod_servico INTEGER REFERENCES servico(cod_servico),
    PRIMARY KEY (nro_ordem, cod_servico)
);

-- ============================================
-- TABELAS DE RECEITA MEDICA
-- ============================================

CREATE TABLE cid (
    codigo VARCHAR(10) PRIMARY KEY,
    descricao TEXT NOT NULL
);

CREATE TABLE medicamento (
    id_medicamento SERIAL PRIMARY KEY,
    nome_generico VARCHAR(200) NOT NULL,
    fabricante VARCHAR(200)
);

CREATE TABLE receita_medica (
    numero_receita SERIAL PRIMARY KEY,
    data_emissao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_medico INTEGER REFERENCES medico(id_medico),
    id_paciente INTEGER REFERENCES paciente(id_paciente),
    codigo_cid VARCHAR(10) REFERENCES cid(codigo)
);

CREATE TABLE prescricao (
    id_prescricao SERIAL PRIMARY KEY,
    numero_receita INTEGER REFERENCES receita_medica(numero_receita),
    id_medicamento INTEGER REFERENCES medicamento(id_medicamento),
    posologia VARCHAR(500),
    periodo_uso VARCHAR(200)
);

-- ============================================
-- DADOS DE EXEMPLO - ENDERECO
-- ============================================

INSERT INTO estado (sigla_estado, nome_estado) VALUES
('PR', 'Parana'),
('SP', 'Sao Paulo'),
('RJ', 'Rio de Janeiro');

INSERT INTO cidade (nome_cidade, sigla_estado) VALUES
('Cascavel', 'PR'),
('Curitiba', 'PR'),
('Sao Paulo', 'SP');

INSERT INTO tipo_logradouro (descricao) VALUES
('Rua'),
('Avenida'),
('Travessa');

INSERT INTO bairro (nome_bairro, id_cidade) VALUES
('Centro', 1),
('Alto Alegre', 1),
('Santa Cruz', 1);

INSERT INTO logradouro (nome_logradouro, id_tipo_logradouro) VALUES
('Brasil', 1),
('Parana', 2),
('das Flores', 1);

INSERT INTO endereco (cep, id_logradouro, id_bairro, id_cidade) VALUES
('85801000', 1, 1, 1),
('85802100', 2, 2, 1),
('85803200', 3, 3, 1);

-- ============================================
-- DADOS DE EXEMPLO - DDI, DDD, TELEFONE, EMAIL
-- ============================================

INSERT INTO ddi (codigo, pais) VALUES
('+55', 'Brasil'),
('+1', 'Estados Unidos'),
('+351', 'Portugal');

INSERT INTO ddd (codigo, id_ddi, regiao) VALUES
('45', 1, 'Oeste do Parana'),
('41', 1, 'Curitiba e Regiao'),
('11', 1, 'Sao Paulo Capital'),
('21', 1, 'Rio de Janeiro');

INSERT INTO telefone (numero, id_ddd) VALUES
('999001122', 1),
('988112233', 1),
('977223344', 2),
('966334455', 1),
('955445566', 3);

INSERT INTO email (endereco_email) VALUES
('maria.silva@email.com'),
('joao.oliveira@email.com'),
('ana.costa@email.com'),
('dr.roberto@clinica.com'),
('carlos.lima@empresa.com');

-- ============================================
-- DADOS DE EXEMPLO - ENTIDADES
-- ============================================

INSERT INTO cliente (cpf, nome, nome_social, id_endereco, numero_casa, complemento) VALUES
('12345678901', 'Maria Silva Santos', NULL, 1, 100, 'Apto 201'),
('23456789012', 'Joao Pedro Oliveira', NULL, 2, 250, NULL),
('34567890123', 'Ana Paula Costa', NULL, 3, 500, 'Casa');

INSERT INTO atendente (cpf, nome, nome_social, id_endereco, numero_casa, complemento) VALUES
('45678901234', 'Carlos Eduardo Lima', NULL, 1, 1500, 'Sala 301'),
('56789012345', 'Fernanda Souza Almeida', NULL, 2, 75, NULL);

INSERT INTO paciente (cpf, nome, nome_social, id_endereco, numero_casa, complemento) VALUES
('12345678901', 'Maria Silva Santos', NULL, 1, 100, 'Apto 201'),
('23456789012', 'Joao Pedro Oliveira', NULL, 2, 250, NULL),
('78901234567', 'Paulo Henrique Dias', NULL, 3, 300, NULL);

INSERT INTO medico (cpf, nome, nome_social, crm, id_endereco, numero_casa, complemento) VALUES
('67890123456', 'Roberto Mendes', NULL, 'CRM-PR-12345', 1, 200, 'Consultorio 5');

-- ============================================
-- DADOS DE EXEMPLO - RELACIONAMENTOS TELEFONE/EMAIL
-- ============================================

-- Cliente telefones e emails
INSERT INTO cliente_telefone (id_cliente, id_telefone) VALUES
(1, 1), (2, 2), (3, 3);

INSERT INTO cliente_email (id_cliente, id_email) VALUES
(1, 1), (2, 2), (3, 3);

-- Paciente telefones e emails
INSERT INTO paciente_telefone (id_paciente, id_telefone) VALUES
(1, 1), (2, 2);

INSERT INTO paciente_email (id_paciente, id_email) VALUES
(1, 1), (2, 2);

-- Medico telefones e emails
INSERT INTO medico_telefone (id_medico, id_telefone) VALUES
(1, 4);

INSERT INTO medico_email (id_medico, id_email) VALUES
(1, 4);

-- Atendente telefones e emails
INSERT INTO atendente_telefone (id_atendente, id_telefone) VALUES
(1, 5);

INSERT INTO atendente_email (id_atendente, id_email) VALUES
(1, 5);

-- ============================================
-- DADOS DE EXEMPLO - SERVICOS
-- ============================================

INSERT INTO servico (tipo_servico, valor) VALUES
('Consultoria Tecnica', 150.00),
('Manutencao Preventiva', 200.00),
('Instalacao de Equipamento', 350.00),
('Suporte Remoto', 80.00),
('Treinamento', 500.00),
('Reparo de Hardware', 250.00),
('Configuracao de Rede', 180.00);

-- ============================================
-- DADOS DE EXEMPLO - CID E MEDICAMENTOS
-- ============================================

INSERT INTO cid (codigo, descricao) VALUES
('J00', 'Nasofaringite aguda (resfriado comum)'),
('J06', 'Infeccoes agudas das vias aereas superiores'),
('J11', 'Influenza (gripe)'),
('G43', 'Enxaqueca'),
('I10', 'Hipertensao essencial'),
('E11', 'Diabetes mellitus tipo 2');

INSERT INTO medicamento (nome_generico, fabricante) VALUES
('Paracetamol 750mg', 'Medley'),
('Ibuprofeno 600mg', 'EMS'),
('Amoxicilina 500mg', 'Eurofarma'),
('Omeprazol 20mg', 'Germed'),
('Dipirona 500mg', 'Sanofi'),
('Loratadina 10mg', 'EMS');

-- ============================================
-- FIM DO SCRIPT
-- ============================================

SELECT 'Banco de dados 3FN inicializado com sucesso!' AS status;

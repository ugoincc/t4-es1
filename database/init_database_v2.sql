-- ============================================
-- Script de Inicializacao do Banco de Dados v2
-- Database: t3-es1 (PostgreSQL)
-- Corrigido para corresponder aos DAOs existentes
-- ============================================

-- Limpar tabelas existentes (CUIDADO: apaga todos os dados!)
DROP TABLE IF EXISTS prescricao CASCADE;
DROP TABLE IF EXISTS receita_medica CASCADE;
DROP TABLE IF EXISTS ordem_servico_servico CASCADE;
DROP TABLE IF EXISTS ordem_servico CASCADE;
DROP TABLE IF EXISTS servico CASCADE;
DROP TABLE IF EXISTS medicamento CASCADE;
DROP TABLE IF EXISTS cid CASCADE;
DROP TABLE IF EXISTS medico CASCADE;
DROP TABLE IF EXISTS paciente CASCADE;
DROP TABLE IF EXISTS atendente CASCADE;
DROP TABLE IF EXISTS cliente CASCADE;
DROP TABLE IF EXISTS pessoa_fisica CASCADE;
DROP TABLE IF EXISTS pessoa CASCADE;
DROP TABLE IF EXISTS endereco CASCADE;
DROP TABLE IF EXISTS logradouro CASCADE;
DROP TABLE IF EXISTS tipo_logradouro CASCADE;
DROP TABLE IF EXISTS bairro CASCADE;
DROP TABLE IF EXISTS cidade CASCADE;
DROP TABLE IF EXISTS estado CASCADE;

-- ============================================
-- TABELAS DE ENDERECO (compativel com DAOs existentes)
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
-- TABELAS DE PESSOA
-- ============================================

CREATE TABLE pessoa (
    id_pessoa SERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    nome_social VARCHAR(200),
    id_endereco INTEGER REFERENCES endereco(id_endereco),
    numero_casa INTEGER,
    complemento VARCHAR(100)
);

CREATE TABLE pessoa_fisica (
    id_pessoa INTEGER PRIMARY KEY REFERENCES pessoa(id_pessoa),
    cpf VARCHAR(11) NOT NULL UNIQUE,
    primeiro_nome VARCHAR(100),
    sobrenome VARCHAR(100)
);

-- ============================================
-- TABELAS DE ORDEM DE SERVICO
-- ============================================

CREATE TABLE cliente (
    id_pessoa INTEGER PRIMARY KEY REFERENCES pessoa_fisica(id_pessoa)
);

CREATE TABLE atendente (
    id_pessoa INTEGER PRIMARY KEY REFERENCES pessoa_fisica(id_pessoa)
);

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
    id_cliente INTEGER REFERENCES cliente(id_pessoa),
    id_atendente INTEGER REFERENCES atendente(id_pessoa)
);

CREATE TABLE ordem_servico_servico (
    nro_ordem INTEGER REFERENCES ordem_servico(nro_ordem),
    cod_servico INTEGER REFERENCES servico(cod_servico),
    PRIMARY KEY (nro_ordem, cod_servico)
);

-- ============================================
-- TABELAS DE RECEITA MEDICA
-- ============================================

CREATE TABLE medico (
    id_pessoa INTEGER PRIMARY KEY REFERENCES pessoa_fisica(id_pessoa),
    crm VARCHAR(20) NOT NULL
);

CREATE TABLE paciente (
    id_pessoa INTEGER PRIMARY KEY REFERENCES pessoa_fisica(id_pessoa)
);

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
    id_medico INTEGER REFERENCES medico(id_pessoa),
    id_paciente INTEGER REFERENCES paciente(id_pessoa),
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
('RJ', 'Rio de Janeiro'),
('MG', 'Minas Gerais'),
('SC', 'Santa Catarina');

INSERT INTO cidade (nome_cidade, sigla_estado) VALUES
('Cascavel', 'PR'),
('Curitiba', 'PR'),
('Sao Paulo', 'SP'),
('Rio de Janeiro', 'RJ'),
('Belo Horizonte', 'MG');

INSERT INTO tipo_logradouro (descricao) VALUES
('Rua'),
('Avenida'),
('Travessa'),
('Alameda'),
('Praca');

INSERT INTO bairro (nome_bairro, id_cidade) VALUES
('Centro', 1),
('Centro', 2),
('Alto Alegre', 1),
('Santa Cruz', 1),
('Jardim Paulista', 3);

INSERT INTO logradouro (nome_logradouro, id_tipo_logradouro) VALUES
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
-- FIM DO SCRIPT
-- ============================================

SELECT 'Banco de dados inicializado com sucesso!' AS status;

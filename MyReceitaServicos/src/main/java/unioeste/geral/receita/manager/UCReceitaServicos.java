package unioeste.geral.receita.manager;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import unioeste.apoio.bd.ConexaoSGBD;
import unioeste.geral.receita.bo.CID;
import unioeste.geral.receita.bo.Medicamento;
import unioeste.geral.receita.bo.Medico;
import unioeste.geral.receita.bo.Paciente;
import unioeste.geral.receita.bo.Prescricao;
import unioeste.geral.receita.bo.ReceitaMedica;
import unioeste.geral.receita.col.PacienteCOL;
import unioeste.geral.receita.col.CIDCOL;
import unioeste.geral.receita.col.ReceitaMedicaCOL;
import unioeste.geral.receita.dao.CIDDAO;
import unioeste.geral.receita.dao.MedicamentoDAO;
import unioeste.geral.receita.dao.MedicoDAO;
import unioeste.geral.receita.dao.PacienteDAO;
import unioeste.geral.receita.dao.ReceitaMedicaDAO;

public class UCReceitaServicos {

    public Paciente obterPacientePorCPF(String cpf) throws ReceitaException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            if (!PacienteCOL.cpfValido(cpf)) {
                throw new ReceitaException("Formato de CPF invalido.");
            }
            return PacienteDAO.selectPacienteByCPF(cpf, conn);
        }
    }

    public Paciente cadastrarPaciente(Paciente paciente) throws ReceitaException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            if (!PacienteCOL.cpfValido(paciente.getCpf())) {
                throw new ReceitaException("Formato de CPF invalido.");
            }
            if (paciente.getNome() == null || paciente.getNome().trim().isEmpty()) {
                throw new ReceitaException("Nome do paciente e obrigatorio.");
            }
            if (PacienteCOL.pacienteCadastrado(paciente.getCpf(), conn)) {
                throw new ReceitaException("Paciente com este CPF ja esta cadastrado.");
            }
            return PacienteDAO.insertPaciente(paciente, conn);
        } catch (ReceitaException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReceitaException("Erro ao cadastrar paciente: " + e.getMessage());
        }
    }

    public List<Medico> listarMedicos() throws ReceitaException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            return MedicoDAO.selectAllMedicos(conn);
        } catch (Exception e) {
            throw new ReceitaException("Erro ao listar medicos: " + e.getMessage());
        }
    }

    public Medico obterMedicoPorId(int id) throws ReceitaException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            return MedicoDAO.selectMedicoById(id, conn);
        } catch (Exception e) {
            throw new ReceitaException("Erro ao buscar medico: " + e.getMessage());
        }
    }

    public CID obterCIDByCodigo(String codigo) throws ReceitaException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            CID cid = CIDDAO.selectCIDByCodigo(codigo, conn);
            if (cid == null) {
                cid = CIDDAO.selectCIDByCodigoLike(codigo, conn);
            }
            return cid;
        } catch (Exception e) {
            throw new ReceitaException("Erro ao buscar CID: " + e.getMessage());
        }
    }

    public List<Medicamento> listarMedicamentos(String nome) throws ReceitaException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            if (nome != null && !nome.isEmpty()) {
                return MedicamentoDAO.selectMedicamentosByNome(nome, conn);
            } else {
                return MedicamentoDAO.selectAllMedicamentos(conn);
            }
        } catch (Exception e) {
            throw new ReceitaException("Erro ao listar medicamentos: " + e.getMessage());
        }
    }

    public List<ReceitaMedica> listarReceitasMedicas() throws ReceitaException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            return ReceitaMedicaDAO.selectAllReceitasMedicas(conn);
        } catch (Exception e) {
            throw new ReceitaException("Erro ao listar receitas medicas: " + e.getMessage());
        }
    }

    public ReceitaMedica obterReceitaMedicaPorNumero(int numeroReceita) throws ReceitaException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            return ReceitaMedicaDAO.selectReceitaMedicaByNumero(numeroReceita, conn);
        } catch (Exception e) {
            throw new ReceitaException("Erro ao buscar receita medica: " + e.getMessage());
        }
    }

    public ReceitaMedica criarReceitaMedica(ReceitaMedica receita) throws ReceitaException, Exception {
        try (Connection conn = ConexaoSGBD.getConnection()) {
            if (receita.getDataEmissao() == null) {
                receita.setDataEmissao(new Date());
            }

            if (!ReceitaMedicaCOL.receitaMedicaValida(receita, conn)) {
                throw new ReceitaException("Dados da receita medica invalidos.");
            }

            int numeroReceita = ReceitaMedicaDAO.insertReceitaMedica(receita, conn);

            for (Prescricao p : receita.getPrescricoes()) {
                ReceitaMedicaDAO.insertPrescricao(numeroReceita, p, conn);
            }

            return ReceitaMedicaDAO.selectReceitaMedicaByNumero(numeroReceita, conn);

        } catch (ReceitaException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReceitaException("Erro ao criar receita medica: " + e.getMessage());
        }
    }
}

package resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import unioeste.geral.receita.bo.Paciente;
import unioeste.geral.receita.manager.ReceitaException;
import unioeste.geral.receita.manager.UCReceitaServicos;

@Path("paciente")
public class PacienteResources {

    private UCReceitaServicos servico = new UCReceitaServicos();

    @GET
    @Path("cpf/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterPacientePorCPF(@PathParam("cpf") String cpf) {
        try {
            Paciente paciente = servico.obterPacientePorCPF(cpf);
            if (paciente != null) {
                return Response.ok(paciente).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Paciente nao encontrado.").build();
            }
        } catch (ReceitaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("criar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarPaciente(Paciente paciente) {
        try {
            Paciente novoPaciente = servico.cadastrarPaciente(paciente);
            return Response.ok(novoPaciente).build();
        } catch (ReceitaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

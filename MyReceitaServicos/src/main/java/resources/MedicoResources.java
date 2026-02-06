package resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import unioeste.geral.receita.bo.Medico;
import unioeste.geral.receita.manager.ReceitaException;
import unioeste.geral.receita.manager.UCReceitaServicos;

@Path("medico")
public class MedicoResources {

    private UCReceitaServicos servico = new UCReceitaServicos();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarMedicos() {
        try {
            List<Medico> medicos = servico.listarMedicos();
            return Response.ok(medicos).build();
        } catch (ReceitaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterMedicoPorId(@PathParam("id") int id) {
        try {
            Medico medico = servico.obterMedicoPorId(id);
            if (medico != null) {
                return Response.ok(medico).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Medico nao encontrado.").build();
            }
        } catch (ReceitaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

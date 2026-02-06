package resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import unioeste.geral.os.bo.Atendente;
import unioeste.geral.os.manager.OrdemServicoException;
import unioeste.geral.os.manager.UCOrdemServicos;

@Path("atendente")
public class AtendenteResources {

    private UCOrdemServicos servico = new UCOrdemServicos();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarAtendentes() {
        try {
            List<Atendente> atendentes = servico.listarAtendentes();
            return Response.ok(atendentes).build();
        } catch (OrdemServicoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterAtendentePorId(@PathParam("id") int id) {
        try {
            Atendente atendente = servico.obterAtendentePorId(id);
            if (atendente != null) {
                return Response.ok(atendente).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Atendente nao encontrado.").build();
            }
        } catch (OrdemServicoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

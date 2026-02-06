package resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import unioeste.geral.os.bo.Servico;
import unioeste.geral.os.manager.OrdemServicoException;
import unioeste.geral.os.manager.UCOrdemServicos;

@Path("servico")
public class ServicoResources {

    private UCOrdemServicos servico = new UCOrdemServicos();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarServicos() {
        try {
            List<Servico> servicos = servico.listarServicos();
            return Response.ok(servicos).build();
        } catch (OrdemServicoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

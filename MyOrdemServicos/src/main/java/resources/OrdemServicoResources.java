package resources;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import unioeste.geral.os.bo.OrdemServico;
import unioeste.geral.os.manager.OrdemServicoException;
import unioeste.geral.os.manager.UCOrdemServicos;

@Path("ordem-servico")
public class OrdemServicoResources {

    private UCOrdemServicos servico = new UCOrdemServicos();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarOrdensServico() {
        try {
            List<OrdemServico> ordens = servico.listarOrdensServico();
            return Response.ok(ordens).build();
        } catch (OrdemServicoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{nro}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterOrdemServicoPorNumero(@PathParam("nro") int nro) {
        try {
            OrdemServico ordem = servico.obterOrdemServicoPorNumero(nro);
            if (ordem != null) {
                return Response.ok(ordem).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Ordem de servico nao encontrada.").build();
            }
        } catch (OrdemServicoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("criar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarOrdemServico(OrdemServico ordem) {
        try {
            OrdemServico novaOrdem = servico.criarOrdemServico(ordem);
            return Response.ok(novaOrdem).build();
        } catch (OrdemServicoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

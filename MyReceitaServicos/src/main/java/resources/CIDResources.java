package resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import unioeste.geral.receita.bo.CID;
import unioeste.geral.receita.manager.ReceitaException;
import unioeste.geral.receita.manager.UCReceitaServicos;

@Path("cid")
public class CIDResources {

    private UCReceitaServicos servico = new UCReceitaServicos();

    @GET
    @Path("{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterCIDByCodigo(@PathParam("codigo") String codigo) {
        try {
            CID cid = servico.obterCIDByCodigo(codigo);
            if (cid != null) {
                return Response.ok(cid).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("CID nao encontrado.").build();
            }
        } catch (ReceitaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

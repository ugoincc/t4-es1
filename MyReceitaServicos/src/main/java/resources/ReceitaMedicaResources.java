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

import unioeste.geral.receita.bo.ReceitaMedica;
import unioeste.geral.receita.manager.ReceitaException;
import unioeste.geral.receita.manager.UCReceitaServicos;

@Path("receita-medica")
public class ReceitaMedicaResources {

    private UCReceitaServicos servico = new UCReceitaServicos();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarReceitasMedicas() {
        try {
            List<ReceitaMedica> receitas = servico.listarReceitasMedicas();
            return Response.ok(receitas).build();
        } catch (ReceitaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{numero}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterReceitaMedicaPorNumero(@PathParam("numero") int numero) {
        try {
            ReceitaMedica receita = servico.obterReceitaMedicaPorNumero(numero);
            if (receita != null) {
                return Response.ok(receita).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Receita medica nao encontrada.").build();
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
    public Response criarReceitaMedica(ReceitaMedica receita) {
        try {
            ReceitaMedica novaReceita = servico.criarReceitaMedica(receita);
            return Response.ok(novaReceita).build();
        } catch (ReceitaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

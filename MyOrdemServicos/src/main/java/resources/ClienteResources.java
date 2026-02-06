package resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import unioeste.geral.os.bo.Cliente;
import unioeste.geral.os.manager.OrdemServicoException;
import unioeste.geral.os.manager.UCOrdemServicos;

@Path("cliente")
public class ClienteResources {

    private UCOrdemServicos servico = new UCOrdemServicos();

    @GET
    @Path("cpf/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterClientePorCPF(@PathParam("cpf") String cpf) {
        try {
            Cliente cliente = servico.obterClientePorCPF(cpf);
            if (cliente != null) {
                return Response.ok(cliente).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Cliente nao encontrado.").build();
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
    public Response criarCliente(Cliente cliente) {
        try {
            Cliente novoCliente = servico.cadastrarCliente(cliente);
            return Response.ok(novoCliente).build();
        } catch (OrdemServicoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

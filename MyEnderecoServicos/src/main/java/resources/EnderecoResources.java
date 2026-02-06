package resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import unioeste.geral.endereco.bo.Cidade;
import unioeste.geral.endereco.bo.Endereco;
import unioeste.geral.endereco.manager.EnderecoException;
import unioeste.geral.endereco.manager.UCEnderecoGeralServicos;

@Path("endereco") // Caminho base: /resources/endereco
public class EnderecoResources {

    private UCEnderecoGeralServicos servico = new UCEnderecoGeralServicos();

    // 1. Cadastrar Endereço
    // URL: POST http://localhost:8080/MyEnderecoServicos/resources/endereco/criar
    // Body (JSON): { "cep": "85851000", "cidade": { "idCidade": 1 }, ... }
    @POST
    @Path("criar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarEndereco(Endereco endereco) {
        try {
            Endereco novoEndereco = servico.cadastrarEndereco(endereco);
            return Response.ok(novoEndereco).build();
        } catch (EnderecoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // 2. Obter por CEP (Banco Local)
    // URL: GET http://localhost:8080/MyEnderecoServicos/resources/endereco/cep/85851000
    @GET
    @Path("cep/{cep}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterEnderecoPorCep(@PathParam("cep") String cep) {
        try {
            Endereco end = servico.obterEnderecoPorCEP(cep);
            if (end != null) {
                return Response.ok(end).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Endereço não encontrado no banco.").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // 3. Obter por ID
    // URL: GET http://localhost:8080/MyEnderecoServicos/resources/endereco/id/1
    @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterEnderecoPorID(@PathParam("id") int id) {
        try {
            Endereco end = servico.obterEnderecoPorID(id);
            if (end != null) {
                return Response.ok(end).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("ID não encontrado.").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // 4. Obter Endereço Externo (API ViaCEP)
    // URL: GET http://localhost:8080/MyEnderecoServicos/resources/endereco/externo/85851000
    @GET
    @Path("externo/{cep}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterEnderecoExterno(@PathParam("cep") String cep) {
        try {
            Endereco end = servico.obterEnderecoExterno(cep);
            return Response.ok(end).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro na API externa: " + e.getMessage()).build();
        }
    }

    // 5. Obter Cidade
    // URL: GET http://localhost:8080/MyEnderecoServicos/resources/endereco/cidade/1
    @GET
    @Path("cidade/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterCidade(@PathParam("id") int id) {
        try {
            Cidade cidade = servico.obterCidade(id);
            return Response.ok(cidade).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
package resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import unioeste.geral.receita.bo.Medicamento;
import unioeste.geral.receita.manager.ReceitaException;
import unioeste.geral.receita.manager.UCReceitaServicos;

@Path("medicamento")
public class MedicamentoResources {

    private UCReceitaServicos servico = new UCReceitaServicos();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarMedicamentos(@QueryParam("nome") String nome) {
        try {
            List<Medicamento> medicamentos = servico.listarMedicamentos(nome);
            return Response.ok(medicamentos).build();
        } catch (ReceitaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

package unioeste.geral.endereco.infra;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONTokener;
import org.json.JSONObject;

import unioeste.geral.endereco.bo.Bairro;
import unioeste.geral.endereco.bo.Cidade;
import unioeste.geral.endereco.bo.Endereco;
import unioeste.geral.endereco.bo.UnidadeFederacao;
import unioeste.geral.endereco.bo.Logradouro;



public class CepAPI {
	static String web_service = "http://viacep.com.br/ws/";
	
	
	public static Endereco getCEP (String CEP) throws Exception {
		String str_url = web_service + CEP + "/json";
		
		URL url = new URL(str_url);
		HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
		
		if(conexao.getResponseCode()!=200) throw new Exception("Não foi possível conectar à API");
		
		BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
		JSONTokener tokener = new JSONTokener(resposta);
		JSONObject jobject = new JSONObject(tokener);
		
		if(jobject.has("erro")) throw new Exception("CEP inexistente");
		
		Endereco endereco = new Endereco();
		UnidadeFederacao estado = new UnidadeFederacao();
		Cidade cidade = new Cidade();
		Bairro bairro = new Bairro();
		Logradouro logradouro = new Logradouro();
		
		estado.setSigla(jobject.getString("uf"));
		cidade.setIdCidade(-1);
		cidade.setNome(jobject.getString("localidade"));
		cidade.setUf(estado);
		bairro.setIdBairro(-1);
		bairro.setNome(jobject.getString("bairro"));
		logradouro.setIdLogradouro(-1);
		logradouro.setNome(jobject.getString("logradouro"));
		
		endereco.setIdEndereco(-1);
		endereco.setCep(CEP);
		endereco.setCidade(cidade);
		endereco.setBairro(bairro);
		endereco.setLogradouro(logradouro);
	
		return endereco;
		
	}
	
	public static void main (String[] args) throws Exception {
		getCEP("85870600");
	}
}

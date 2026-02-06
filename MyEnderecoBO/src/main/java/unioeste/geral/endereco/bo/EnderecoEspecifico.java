package unioeste.geral.endereco.bo;

import java.io.Serializable;

public class EnderecoEspecifico implements Serializable{
	public final static long serialVersionUID = 1;
	
	private Endereco endereco;
	private int nroCasa;
	private String complemento;
	
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public int getNumero() {
		return nroCasa;
	}
	public void setNumero(int nroCasa) {
		this.nroCasa = nroCasa;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
}

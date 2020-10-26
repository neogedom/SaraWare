package br.com.saraware.domain;

public class Receita {
	private Integer codigoReceita;
	private Double valor;
	private Boolean ativo;
	
	public Integer getCodigoReceita() {
		return codigoReceita;
	}
	
	public void setCodigoReceita(Integer codigoReceita) {
		this.codigoReceita = codigoReceita;
	}
	
	public Double getValor() {
		return valor;
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}

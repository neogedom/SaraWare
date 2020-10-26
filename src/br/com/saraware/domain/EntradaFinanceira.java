package br.com.saraware.domain;


public class EntradaFinanceira {
	private Integer codigoEntradaFinanceira;
	private String origem;
	private Receita receita = new Receita();
	
	
	public Integer getCodigoEntradaFinanceira() {
		return codigoEntradaFinanceira;
	}
	
	public void setCodigoEntradaFinanceira(Integer codigoEntradaFinanceira) {
		this.codigoEntradaFinanceira = codigoEntradaFinanceira;
	}
	
	public String getOrigem() {
		return origem;
	}
	
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
	public Receita getReceita() {
		return receita;
	}
	
	public void setReceita(Receita receita) {
		this.receita = receita;
	}
	

}

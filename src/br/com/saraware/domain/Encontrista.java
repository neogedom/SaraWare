package br.com.saraware.domain;

public class Encontrista extends Pessoa {
	private Integer codigoEncontrista;
	private Boolean reincidente;

	public Integer getCodigoEncontrista() {
		return codigoEncontrista;
	}
	
	public void setCodigoEncontrista(Integer codigoEncontrista) {
		this.codigoEncontrista = codigoEncontrista;
	}

	public Boolean getReincidente() {
		return reincidente;
	}

	public void setReincidente(Boolean reincidente) {
		this.reincidente = reincidente;
	}
}

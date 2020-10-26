package br.com.saraware.domain;

public class Intervalo {
	public Integer codigoIntervalo;
	public String descricaoIntervalo;
	public Integer minutos;
	
	public Integer getMinutos() {
		return minutos;
	}
	
	public void setMinutos(Integer minutos) {
		this.minutos = minutos;
	}
	
	public String getDescricaoIntervalo() {
		return descricaoIntervalo;
	}
	
	public void setDescricaoIntervalo(String descricaoIntervalo) {
		this.descricaoIntervalo = descricaoIntervalo;
	}
	
	public Integer getCodigoIntervalo() {
		return codigoIntervalo;
	}
	
	public void setCodigoIntervalo(Integer codigoIntervalo) {
		this.codigoIntervalo = codigoIntervalo;
	}

}

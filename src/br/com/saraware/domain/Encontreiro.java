package br.com.saraware.domain;

public class Encontreiro extends Membro {
	private Integer codigoEncontreiro;
	private Boolean checked = false;
	
	public Boolean getChecked() {
		return checked;
	}
	
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	public Integer getCodigoEncontreiro() {
		return codigoEncontreiro;
	}
	
	public void setCodigoEncontreiro(Integer codigoEncontreiro) {
		this.codigoEncontreiro = codigoEncontreiro;
	}
	
}

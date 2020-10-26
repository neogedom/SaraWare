package br.com.saraware.domain;

public class EncontristaReincidente extends Membro {
	private Integer codigoEncontristaReincidente;
	private Membro membro;

	public Integer getCodigoEncontristaReincidente() {
		return codigoEncontristaReincidente;
	}
	
	public void setCodigoEncontristaReincidente(Integer codigoEncontristaReincidente) {
		this.codigoEncontristaReincidente = codigoEncontristaReincidente;
	}
	
	public Membro getMembro() {
		return membro;
	}
	
	public void setMembro(Membro membro) {
		this.membro = membro;
	}
}

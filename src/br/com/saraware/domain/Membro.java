package br.com.saraware.domain;

import java.util.Date;

public class Membro extends Pessoa {
	private Integer codigoMembro;
	private Hierarquia hierarquia = new Hierarquia();
	private Date dataBatismo;
	private String statusIV;

	public Hierarquia getHierarquia() {
		return hierarquia;
	}

	public void setHierarquia(Hierarquia hierarquia) {
		this.hierarquia = hierarquia;
	}

	public Date getDataBatismo() {
		return dataBatismo;
	}

	public void setDataBatismo(Date dataBatismo) {
		this.dataBatismo = dataBatismo;
	}

	public String getStatusIV() {
		return statusIV;
	}

	public void setStatusIV(String statusIV) {
		this.statusIV = statusIV;
	}

	public Integer getCodigoMembro() {
		return codigoMembro;
	}

	public void setCodigoMembro(Integer codigoMembro) {
		this.codigoMembro = codigoMembro;
	}

}

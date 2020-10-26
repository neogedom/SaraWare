package br.com.saraware.domain;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class RevisaoDeVidas {
	private Integer codigoRevisaoDeVidas;
	private Date dataDeAcontecimento;
	public String dataFormatada = "";
	public Boolean dataVencida = false;
	
	public void setCodigoRevisaoDeVidas(Integer codigoRevisaoDeVidas) {
		this.codigoRevisaoDeVidas = codigoRevisaoDeVidas;
	}
	
	public Integer getCodigoRevisaoDeVidas() {
		return codigoRevisaoDeVidas;
	}
	
	public void setDataDeAcontecimento(Date dataDeAcontecimento) {
		this.dataDeAcontecimento = dataDeAcontecimento;
	}
	
	public Date getDataDeAcontecimento() {
		
			return dataDeAcontecimento;
	}
	
	public String getDataFormatada(){
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));
		if (dataDeAcontecimento != null)
			dataFormatada = df.format(dataDeAcontecimento);
		
		try {
			return (dataFormatada);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dataFormatada = "";
			return dataFormatada;
		}
	}
	
	public boolean getDataVencida (){
		Date hoje = new Date();
		if (hoje.after(dataDeAcontecimento))
		{
			dataVencida= true;
			return dataVencida;
		}
		else
		{
			dataVencida = false;
			return dataVencida;
		}
		
		}
	
	
}

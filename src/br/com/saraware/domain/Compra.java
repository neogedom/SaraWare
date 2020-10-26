package br.com.saraware.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Compra {

	private Integer codigoCompra;
	private Date data;
	private BigDecimal valor;
	private RevisaoDeVidas revisaoDeVidas = new RevisaoDeVidas();

	public RevisaoDeVidas getRevisaoDeVidas() {
		return revisaoDeVidas;
	}

	public void setRevisaoDeVidas(RevisaoDeVidas revisaoDeVidas) {
		this.revisaoDeVidas = revisaoDeVidas;
	}

	public Integer getCodigoCompra() {
		return codigoCompra;
	}

	public void setCodigoCompra(Integer codigoCompra) {
		this.codigoCompra = codigoCompra;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		valor.setScale(2, BigDecimal.ROUND_HALF_UP);
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}

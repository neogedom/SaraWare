package br.com.saraware.domain;

import java.util.Date;

public class ItemCronograma {
	public Integer codigoItemCronograma;
	public String descricao;
	public Date horaInicio;
	public Date horaFim;
	public Ministracao ministracao = new Ministracao();
	public Intervalo intervalo = new Intervalo();
	public Cronograma cronograma = new Cronograma();
	public String dia;
	
	public String getDia() {
		return dia;
	}
	
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getCodigoItemCronograma() {
		return codigoItemCronograma;
	}

	public void setCodigoItemCronograma(Integer codigoItemCronograma) {
		this.codigoItemCronograma = codigoItemCronograma;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(Date horaFim) {
		this.horaFim = horaFim;
	}

	public Ministracao getMinistracao() {
		return ministracao;
	}

	public void setMinistracao(Ministracao ministracao) {
		this.ministracao = ministracao;
	}

	public Intervalo getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Intervalo intervalo) {
		this.intervalo = intervalo;
	}

	public Cronograma getCronograma() {
		return cronograma;
	}

	public void setCronograma(Cronograma cronograma) {
		this.cronograma = cronograma;
	}

}

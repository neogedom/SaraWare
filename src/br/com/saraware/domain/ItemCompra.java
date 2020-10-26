package br.com.saraware.domain;

public class ItemCompra {
	private Integer codigoItemCompra;
	private Integer quantidade;
	private Double valorUnitario;
	private Double valorParcial;
	private Boolean estoque;
	private Boolean checked = false;
	private Compra compra;
	private Item item;
	
	public Double getValorParcial() {
		return valorParcial;
	}
	
	public void setValorParcial(Double valorParcial) {
		this.valorParcial = valorParcial;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public Compra getCompra() {
		return compra;
	}
	
	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Integer getCodigoItemCompra() {
		return codigoItemCompra;
	}

	public void setCodigoItemCompra(Integer codigoItemCompra) {
		this.codigoItemCompra = codigoItemCompra;
	}

	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public Double getValorUnitario() {
		return valorUnitario;
	}
	
	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	


	public Boolean getEstoque() {
		return estoque;
	}

	public void setEstoque(Boolean estoque) {
		this.estoque = estoque;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}

package br.com.saraware.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.saraware.dao.DAOCompra;
import br.com.saraware.dao.DAOItem;
import br.com.saraware.domain.Compra;
import br.com.saraware.domain.Item;
import br.com.saraware.domain.ItemCompra;
import br.com.saraware.domain.Planejamento;
import br.com.saraware.util.JSFUtil;

@ManagedBean(name = "MBCompra")
@ViewScoped
public class CompraBean {
	
	private Item produto;
	private Planejamento planejamento;
	private ArrayList<Item> itens;
	private ArrayList<Item> itensFiltrados;
	private ArrayList<ItemCompra> listaItensCompra;
	private Integer quantidadeCompra;
	private Double valorCompra;
	private Compra compra;
	
	public Planejamento getPlanejamento() {
		return planejamento;
	}
	
	public void setPlanejamento(Planejamento planejamento) {
		this.planejamento = planejamento;
	}
	
	
	public Compra getCompra() {
		if (compra == null)
		{	
			compra = new Compra();
			compra.setValor(new BigDecimal("0.00"));
		}
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	
	public Double getValorCompra() {
		
		return valorCompra;
	}
	
	public void setValorCompra(Double valorCompra) {
		this.valorCompra = valorCompra;
	}

	public Integer getQuantidadeCompra() {
		return quantidadeCompra;
	}
	public void setQuantidadeCompra(Integer quantidadeCompra) {
		this.quantidadeCompra = quantidadeCompra;
	}
	
	public Item getProduto() {
		return produto;
	}
	
	public void setProduto(Item produto) {
		this.produto = produto;
	}
	
	public ArrayList<ItemCompra> getListaItensCompra() {
		if (listaItensCompra == null)
		{
			listaItensCompra = new ArrayList<>();
		}
		return listaItensCompra;
	}
	public void setListaItensCompra(ArrayList<ItemCompra> listaItensCompra) {
		this.listaItensCompra = listaItensCompra;
	}
	
	public ArrayList<Item> getItens() {
		return itens;
	}
	
	public void setItens(ArrayList<Item> itens) {
		this.itens = itens;
	}
	
	public ArrayList<Item> getItensFiltrados() {
		return itensFiltrados;
	}
	
	public void setItensFiltrados(ArrayList<Item> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	@PostConstruct
	public void prepararPesquisa() {
		try {
			DAOItem dao = new DAOItem();
			itens = dao.listar();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void adicionar ()
	{
		ItemCompra itemCompra = new ItemCompra();
		itemCompra.setItem(produto);
		itemCompra.setQuantidade(quantidadeCompra);
		itemCompra.setValorUnitario(valorCompra);
		itemCompra.setValorParcial(valorCompra * quantidadeCompra);
		
		listaItensCompra.add(itemCompra);
		
		compra.setValor(compra.getValor().add(new BigDecimal(itemCompra.getValorParcial())).setScale(2, BigDecimal.ROUND_HALF_UP));;
	}
	
	public void remover (ItemCompra itemCompra)
	{
		int posicaoEncontrada = -1;
		
		for (int pos = 0; pos < listaItensCompra.size() && posicaoEncontrada < 0; pos++)
		{
			ItemCompra itemTemp = listaItensCompra.get(pos);
			if (itemTemp.getItem().equals(produto))
			{
				posicaoEncontrada = pos;
			}
		}
		
		if (posicaoEncontrada > -1)
		{
			System.out.println(itemCompra.getValorParcial());
			listaItensCompra.remove(posicaoEncontrada);
			compra.setValor(compra.getValor().subtract(new BigDecimal(itemCompra.getValorParcial())).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
	}

	public void carregarDadosDaCompra() {
		compra.setData(new Date());
	}
	
	public void salvar()
	{
		try {
			DAOCompra daoc = new DAOCompra();
			daoc.cadastrar(compra, listaItensCompra);
			
			compra = new Compra();
			compra.setValor(new BigDecimal("0.00"));
			
			listaItensCompra = new ArrayList<ItemCompra>();
			JSFUtil.adicionarMensagemSucesso("Compra salva com sucesso");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
}

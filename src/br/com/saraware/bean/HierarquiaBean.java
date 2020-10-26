package br.com.saraware.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.saraware.dao.DAOHierarquia;
import br.com.saraware.domain.Hierarquia;
import br.com.saraware.util.JSFUtil;

@ManagedBean(name = "MBHierarquia")
@ViewScoped
public class HierarquiaBean {
	private Hierarquia hierarquia;
	private ArrayList<Hierarquia> itens;
	private ArrayList<Hierarquia> itensFiltrados;

	public Hierarquia getHierarquia() {
		return hierarquia;
	}

	public void setHierarquia(Hierarquia hierarquia) {
		this.hierarquia = hierarquia;
	}

	public ArrayList<Hierarquia> getItens() {
		return itens;
	}
	
	public void setItens(ArrayList<Hierarquia> itens) {
		this.itens = itens;
	}
	
	public ArrayList<Hierarquia> getItensFiltrados() {
		return itensFiltrados;
	}
	
	public void setItensFiltrados(ArrayList<Hierarquia> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	@PostConstruct
	public void prepararPesquisa() {
		try {
			DAOHierarquia dao = new DAOHierarquia();
			itens = dao.listar();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		hierarquia = new Hierarquia();
	}

	public void novo() {
		try {
			DAOHierarquia dao = new DAOHierarquia();
			dao.cadastrar(hierarquia);
			
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Hierarquia salva com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			DAOHierarquia dao = new DAOHierarquia();
			dao.deletar(hierarquia);
			
			itens = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Hierarquia removida com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	
	public void editar() {
		try {
			DAOHierarquia dao = new DAOHierarquia();
			dao.alterar(hierarquia);
			
			itens = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Hierarquia alterada com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

}

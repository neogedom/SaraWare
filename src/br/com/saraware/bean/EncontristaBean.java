package br.com.saraware.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.saraware.dao.DAOEncontrista;
import br.com.saraware.domain.Encontrista;
import br.com.saraware.util.JSFUtil;

@ManagedBean(name = "MBEncontrista")
@ViewScoped
public class EncontristaBean {
	private Encontrista encontrista;
	private ArrayList<Encontrista> itens;
	private ArrayList<Encontrista> itensFiltrados;
	private ArrayList<Encontrista> comboEncontristas;

	public Encontrista getEncontrista() {
		return encontrista;
	}

	public void setEncontrista(Encontrista encontrista) {
		this.encontrista = encontrista;
	}

	public ArrayList<Encontrista> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Encontrista> itens) {
		this.itens = itens;
	}

	public ArrayList<Encontrista> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Encontrista> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	public ArrayList<Encontrista> getComboEncontristas() {
		return comboEncontristas;
	}

	public void setComboEncontristas(ArrayList<Encontrista> comboEncontristas) {
		this.comboEncontristas = comboEncontristas;
	}

	@PostConstruct
	public void prepararPesquisa() {
		try {
			DAOEncontrista dao = new DAOEncontrista();
			itens = dao.listar();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		encontrista = new Encontrista();
	}
	
	public void novo() {
		try {
			DAOEncontrista dao = new DAOEncontrista();
			encontrista.setReincidente(false);
			dao.cadastrar(encontrista);
			
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Encontrista salvo com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			DAOEncontrista dao = new DAOEncontrista();
			dao.deletar(encontrista);
			
			itens = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Encontrista removido com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	
	public void editar() {
		try {
			DAOEncontrista dao = new DAOEncontrista();
			dao.alterar(encontrista);
			
			itens = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Encontrista alterado com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
}

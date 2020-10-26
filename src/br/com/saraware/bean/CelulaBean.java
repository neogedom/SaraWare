package br.com.saraware.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.saraware.dao.DAOCelula;
import br.com.saraware.dao.DAOLider;
import br.com.saraware.domain.Celula;
import br.com.saraware.domain.Lider;
import br.com.saraware.domain.Membro;
import br.com.saraware.util.JSFUtil;

@ManagedBean(name = "MBCelula")
@ViewScoped
public class CelulaBean {
	private Celula celula = new Celula();
	private Membro membro;
	private ArrayList<Lider> comboLideres;
	private ArrayList<Celula> itens;
	private ArrayList<Celula> itensFiltrados;
	private ArrayList<Membro> membros;

	public Membro getMembro() {
		return membro;
	}
	
	public void setMembro(Membro membro) {
		this.membro = membro;
	}
	
	public Celula getCelula() {
		return celula;
	}

	public void setCelula(Celula celula) {
		this.celula = celula;
	}

	public ArrayList<Celula> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Celula> itens) {
		this.itens = itens;
	}

	public ArrayList<Celula> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Celula> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	public ArrayList<Lider> getComboLideres() {
		return comboLideres;
	}

	public void setComboLideres(ArrayList<Lider> comboLideres) {
		this.comboLideres = comboLideres;
	}

	public void setMembros(ArrayList<Membro> membros) {
		this.membros = membros;
	}

	public ArrayList<Membro> getMembros() {
		return membros;
	}

	@PostConstruct
	public void prepararPesquisa() {
		try {
			DAOCelula dao = new DAOCelula();
			itens = dao.listar();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		celula = new Celula();
		DAOLider dao = new DAOLider();
		try {
			comboLideres = dao.listar();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void novo() {
		try {
			DAOCelula dao = new DAOCelula();
			dao.cadastrar(celula);     
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Célula salva com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void excluir() {
		try {
			DAOCelula dao = new DAOCelula();
			dao.deletar(celula);

			itens = dao.listar();

			JSFUtil.adicionarMensagemSucesso("Celula removida com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararEditar() {
		DAOLider dao = new DAOLider();
		try {
			comboLideres = dao.listar();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void editar() {
		try {
			DAOCelula dao = new DAOCelula();
			dao.alterar(celula);

			itens = dao.listar();

			JSFUtil.adicionarMensagemSucesso("Célula alterada com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void prepararDetalhes(Object valor) {
		try {
			DAOCelula dao = new DAOCelula();
			Celula c = new Celula();
			c = dao.getByNome(((Celula)valor).getNome());
			membros = c.getMembros();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void inserirMembroCelula() {
		try {
			DAOCelula dao = new DAOCelula();
			dao.incluirMembroCelula(celula);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
}

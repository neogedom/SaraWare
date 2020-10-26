package br.com.saraware.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.saraware.dao.DAOEncontrista;
import br.com.saraware.dao.DAOEncontristaReincidente;
import br.com.saraware.dao.DAOMembro;
import br.com.saraware.dao.DAOPessoa;
import br.com.saraware.domain.Encontrista;
import br.com.saraware.domain.EncontristaReincidente;
import br.com.saraware.domain.Membro;
import br.com.saraware.domain.Pessoa;
import br.com.saraware.util.JSFUtil;

@ManagedBean(name = "MBEncontristaReincidente")
@ViewScoped
public class EncontristaReincidenteBean {
	private EncontristaReincidente encontristaReincidente = new EncontristaReincidente();
	private Pessoa pessoa = new Pessoa();
	private ArrayList<Pessoa> itens;
	private ArrayList<Pessoa> itensFiltrados;
	private ArrayList<EncontristaReincidente> comboEncontristaReincidentes;
	private ArrayList<Pessoa> comboPessoas;
	private String comboLabel;
	private Pessoa encontristaObject;
	private String radioValue;

	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public String getRadioValue() {
		return radioValue;
	}

	public void setRadioValue(String radioValue) {
		this.radioValue = radioValue;
	}

	public EncontristaReincidente getEncontrista() {
		return encontristaReincidente;
	}

	public void setEncontrista(EncontristaReincidente encontristaReincidente) {
		this.encontristaReincidente = encontristaReincidente;
	}

	public ArrayList<Pessoa> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Pessoa> itens) {
		this.itens = itens;
	}

	public ArrayList<Pessoa> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Pessoa> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	public ArrayList<EncontristaReincidente> getComboEncontristasReincidente() {
		return comboEncontristaReincidentes;
	}

	public void setComboEncontristaReincidentes(ArrayList<EncontristaReincidente> comboEncontristaReincidentes) {
		this.comboEncontristaReincidentes = comboEncontristaReincidentes;
	}

	public ArrayList<EncontristaReincidente> getComboEncontristaReincidentes() {
		return comboEncontristaReincidentes;
	}

	public void setEncontristaReincidente(EncontristaReincidente encontristaReincidente) {
		this.encontristaReincidente = encontristaReincidente;
	}

	public EncontristaReincidente getEncontristaReincidente() {
		return encontristaReincidente;
	}

	public ArrayList<Pessoa> getComboPessoas() {
		return comboPessoas;
	}

	public void setComboPessoas(ArrayList<Pessoa> comboPessoas) {
		this.comboPessoas = comboPessoas;
	}

	public String getComboLabel() {
		return comboLabel;
	}

	public void setComboLabel(String comboLabel) {
		this.comboLabel = comboLabel;
	}

	public Pessoa getEncontristaObject() {
		return encontristaObject;
	}

	public void setEncontristaObject(Pessoa encontristaObject) {
		this.encontristaObject = encontristaObject;
	}

	@PostConstruct
	public void prepararPesquisa() {
		try {
			DAOEncontristaReincidente dao = new DAOEncontristaReincidente();
			itens = dao.listarCompleto();

		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		encontristaObject = new Pessoa();
	}

	public void novo() {
		try {
			DAOEncontristaReincidente dao = new DAOEncontristaReincidente();
			if (comboLabel.contains("um membro")) {
				
				DAOMembro daom = new DAOMembro();
				Membro membro = daom.getByCodigoPessoa(encontristaObject.getCodigoPessoa());
				EncontristaReincidente encontrista = new EncontristaReincidente();
				encontrista.setCodigoPessoa(membro.getCodigoPessoa());
				encontrista.setNome(membro.getNome());
				encontrista.setEndereco(membro.getEndereco());
				encontrista.setTelefone(membro.getTelefone());
				encontrista.setDataNascimento(membro.getDataNascimento());
				encontrista.setNumeroDocumento(membro.getNumeroDocumento());
				encontrista.setTipoDocumento(membro.getTipoDocumento());
				encontrista.setEstadoCivil(membro.getEstadoCivil());
				encontrista.setSexo(membro.getSexo());
				encontrista.setDataBatismo(membro.getDataBatismo());
				encontrista.setStatusIV(membro.getStatusIV());
				encontrista.setHierarquia(membro.getHierarquia());
				dao.cadastrar(encontrista);
			} else {
				Encontrista encontrista = new Encontrista();
				Pessoa pessoa = new Pessoa();
				DAOEncontrista daoe = new DAOEncontrista();
				DAOPessoa daop = new DAOPessoa();
				
				pessoa = daop.getByCodigo(encontristaObject.getCodigoPessoa());
				
				encontrista.setCodigoPessoa(pessoa.getCodigoPessoa());
				encontrista.setNome(pessoa.getNome());
				encontrista.setEndereco(pessoa.getEndereco());
				encontrista.setTelefone(pessoa.getTelefone());
				encontrista.setDataNascimento(pessoa.getDataNascimento());
				encontrista.setNumeroDocumento(pessoa.getNumeroDocumento());
				encontrista.setTipoDocumento(pessoa.getTipoDocumento());
				encontrista.setEstadoCivil(pessoa.getEstadoCivil());
				encontrista.setSexo(pessoa.getSexo());
				encontrista.setReincidente(true);

				daoe.cadastrar(encontrista);
			}

			itens = dao.listarCompleto();
			JSFUtil.adicionarMensagemSucesso("Encontrista Reincidente salvo com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void excluir() {
		try {
			DAOEncontristaReincidente dao = new DAOEncontristaReincidente();
			dao.deletar(pessoa);

			itens = dao.listarCompleto();

			JSFUtil.adicionarMensagemSucesso("Encontrista Reincidente removido com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void mudaListagem() {
		encontristaObject = new Pessoa();
		try {
			DAOPessoa dao = new DAOPessoa();
			comboLabel = radioValue;
			if (comboLabel.contains("um membro")) {
				comboPessoas = dao.listarMembros();
				System.out.println(comboPessoas.size());
			}
			else{
				comboPessoas = dao.listarNaoMembros();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}

	}
}

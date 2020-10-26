package br.com.saraware.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.saraware.dao.DAOCelula;
import br.com.saraware.dao.DAOItemDoacao;
import br.com.saraware.dao.DAOMeta;
import br.com.saraware.dao.DAOPlanejamento;
import br.com.saraware.dao.DAORevisao;
import br.com.saraware.domain.Celula;
import br.com.saraware.domain.ItemDoacao;
import br.com.saraware.domain.Meta;
import br.com.saraware.domain.Planejamento;
import br.com.saraware.domain.RevisaoDeVidas;
import br.com.saraware.util.JSFUtil;

@ManagedBean(name = "MBMeta")
@ViewScoped
public class MetaBean {
	private Meta meta = new Meta();
	private Celula celula;
	private Planejamento planejamento = new Planejamento();
	private ArrayList<Celula> comboCelulas;
	private ItemDoacao itemDoacaoMeta = new ItemDoacao();
	private ArrayList<ItemDoacao> tabelaItensDoacao = new ArrayList<ItemDoacao>();
	private ArrayList<ItemDoacao> itensDoacaoSalvar = new ArrayList<ItemDoacao>();	
	private ArrayList<Meta> itens;
	private ArrayList<Meta> itensFiltrados;
	private ArrayList<ItemDoacao> itemsDoacaoMeta;
	private ArrayList<ItemDoacao> itemsDoacaoMetaFiltrado;
	private HashMap<Integer, Boolean> checkBoxMarcadoEditar = new HashMap<Integer, Boolean>();
	private java.util.List<ItemDoacao> items = new ArrayList<ItemDoacao>();
	private String dataRevisao;
	private Boolean checkBoxMarcado = false;
	private Integer quantidadeAlcancada;
	
	public Integer getQuantidadeAlcancada() {
		return quantidadeAlcancada;
	}
	
	public void setQuantidadeAlcancada(Integer quantidadeAlcancada) {
		this.quantidadeAlcancada = quantidadeAlcancada;
	}
	
	public Boolean getCheckBoxMarcado() {
		return checkBoxMarcado;
	}
	
	public void setCheckBoxMarcado(Boolean checkBoxMarcado) {
		this.checkBoxMarcado = checkBoxMarcado;
	}
	
	public java.util.List<ItemDoacao> getItems() {
		return items;
	}
	
	public void setItems(java.util.List<ItemDoacao> items) {
		this.items = items;
	}
	
	public ArrayList<ItemDoacao> getItemsDoacaoMetaFiltrado() {
		return itemsDoacaoMetaFiltrado;
	}
	
	public void setItemsDoacaoMetaFiltrado(ArrayList<ItemDoacao> itemsDoacaoMetaFiltrado) {
		this.itemsDoacaoMetaFiltrado = itemsDoacaoMetaFiltrado;
	}
	
	public ArrayList<ItemDoacao> getItensDoacaoSalvar() {
		return itensDoacaoSalvar;
	}
	
	public void setItensDoacaoSalvar(ArrayList<ItemDoacao> itensDoacaoSalvar) {
		this.itensDoacaoSalvar = itensDoacaoSalvar;
	}
	
	public ArrayList<ItemDoacao> getTabelaItensDoacao() {
		return tabelaItensDoacao;
	}
	
	public void setTabelaItensDoacao(ArrayList<ItemDoacao> tabelaItensDoacao) {
		this.tabelaItensDoacao = tabelaItensDoacao;
	}
	
	public String getDataRevisao() {
		return dataRevisao;
	}
	
	public void setDataRevisao(String dataRevisao) {
		this.dataRevisao = dataRevisao;
	}
	
	public HashMap<Integer, Boolean> getCheckBoxMarcadoEditar() {
		return checkBoxMarcadoEditar;
	}
	
	public void setCheckBoxMarcadoEditar(HashMap<Integer, Boolean> checkBoxMarcadoEditar) {
		this.checkBoxMarcadoEditar = checkBoxMarcadoEditar;
	}
	
	public Celula getCelula() {
		return celula;
	}
	
	public void setCelula(Celula celula) {
		this.celula = celula;
	}
	
	public Planejamento getPlanejamento() {
		return planejamento;
	}
	
	public void setPlanejamento(Planejamento planejamento) {
		this.planejamento = planejamento;
	}
	
	
	public ArrayList<Celula> getComboCelulas() {
		return comboCelulas;
	}
	
	public void setComboCelulas(ArrayList<Celula> comboCelulas) {
		this.comboCelulas = comboCelulas;
	}
	
	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public ItemDoacao getItemDoacaoMeta() {
		return itemDoacaoMeta;
	}

	public void setItemDoacaoMeta(ItemDoacao itemDoacaoMeta) {
		this.itemDoacaoMeta = itemDoacaoMeta;
	}

	public ArrayList<ItemDoacao> getTabelaItemsDoacao() {
		return tabelaItensDoacao;
	}

	public void setComboItemsDoacao(ArrayList<ItemDoacao> tabelaItemsDoacao) {
		this.tabelaItensDoacao = tabelaItemsDoacao;
	}

	public ArrayList<Meta> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Meta> itens) {
		this.itens = itens;
	}

	public ArrayList<Meta> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Meta> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	public ArrayList<ItemDoacao> getItemsDoacaoMeta() {
		return itemsDoacaoMeta;
	}

	public void setItemsDoacaoMeta(ArrayList<ItemDoacao> itemsDoacaoMeta) {
		this.itemsDoacaoMeta = itemsDoacaoMeta;
	}

	@PostConstruct
	public void prepararPesquisa() {
		try {
			DAOPlanejamento daop = new DAOPlanejamento();
			DAOMeta dao = new DAOMeta();
			itens = dao.listar();
			Calendar c = Calendar.getInstance();
			int ano = c.get(Calendar.YEAR);
			planejamento = daop.getByAno(ano);
						
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		meta = new Meta();
		DAOCelula daoc = new DAOCelula();
		DAOPlanejamento daop = new DAOPlanejamento();
		DAOItemDoacao daoid = new DAOItemDoacao();
		try {
			
			comboCelulas = daoc.listar();
			Calendar c = Calendar.getInstance();
			int ano = c.get(Calendar.YEAR);
			planejamento = daop.getByAno(ano);
			tabelaItensDoacao = daoid.listar();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void prepararNovoItem()
	{
		itemDoacaoMeta = new ItemDoacao();
	}

	public void novo() {
		try {
			DAOMeta dao = new DAOMeta();
			RevisaoDeVidas r = new RevisaoDeVidas();
			DAORevisao daor = new DAORevisao();
			
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(dataRevisao);
			r = daor.getByData(date);
			
		
			meta.setRevisaoDeVidas(r);
			meta.setItemsDoacaoMeta(itensDoacaoSalvar);
			dao.cadastrar(meta);     
			
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Meta salva com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void novoItemDoacao()
	{
		
		try {
		
			DAOItemDoacao dao = new DAOItemDoacao();
			dao.cadastrar(itemDoacaoMeta);
			
			tabelaItensDoacao = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Item de Doação salvo com sucesso!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void excluir() {
		try {
			DAOMeta dao = new DAOMeta();
			dao.deletar(meta);

			itens = dao.listar();

			JSFUtil.adicionarMensagemSucesso("Meta removida com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararEditar(Integer codigo) {
		DAOItemDoacao dao = new DAOItemDoacao();
		DAOCelula daoc = new DAOCelula();
		DAOPlanejamento daop = new DAOPlanejamento();
		try {
			comboCelulas = daoc.listar();
			Calendar c = Calendar.getInstance();
			int ano = c.get(Calendar.YEAR);
			planejamento = daop.getByAno(ano);
			
			tabelaItensDoacao = dao.listarEChecar(codigo);
			for (ItemDoacao id : getTabelaItemsDoacao()) {
				if (id.getChecked()) {
					checkBoxMarcadoEditar.put(id.getCodigoItemDoacao(), true);
				} else {
					checkBoxMarcadoEditar.put(id.getCodigoItemDoacao(), false);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void prepararMetaBatida(Integer codigo) {
		DAOItemDoacao dao = new DAOItemDoacao();
		DAOCelula daoc = new DAOCelula();
		DAOPlanejamento daop = new DAOPlanejamento();
		try {
			comboCelulas = daoc.listar();
			Calendar c = Calendar.getInstance();
			int ano = c.get(Calendar.YEAR);
			planejamento = daop.getByAno(ano);
			
			System.out.println("Código: " + codigo);
			tabelaItensDoacao = dao.listarEChecarMeta(codigo);
			for (ItemDoacao id : getTabelaItemsDoacao()) {
				if (id.getChecked()) {
					checkBoxMarcadoEditar.put(id.getCodigoItemDoacao(), true);
				} else {
					checkBoxMarcadoEditar.put(id.getCodigoItemDoacao(), false);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	


	public void editar() {
		try {
			DAOMeta dao = new DAOMeta();
			for (ItemDoacao id : getTabelaItemsDoacao()) {
				if (checkBoxMarcadoEditar.get(id.getCodigoItemDoacao())) {
					items.add(id);
				}
			}

			meta.setItemsDoacaoMeta((ArrayList<ItemDoacao>) items);
			dao.alterar(meta);

			itens = dao.listar();

			JSFUtil.adicionarMensagemSucesso("Meta alterada com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void editarItemDoacao()
	{
		try {
			DAOItemDoacao dao = new DAOItemDoacao();
			dao.alterar(itemDoacaoMeta);
			
			tabelaItensDoacao = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Item de Doação alterado com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void editarItemDoacaoEstoque(Integer codigo, Integer quantidade)
	{
		System.out.println(quantidade);
		try {
			DAOItemDoacao dao = new DAOItemDoacao();
			dao.addEstoque(codigo, quantidade);
			
			tabelaItensDoacao = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Item de Doação adicionado ao estoque!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}


	
	public void prepararDetalhes(Object valor) {
		try {
			DAOMeta dao = new DAOMeta();
			Meta m = new Meta();
			m = dao.getByNome(((Meta)valor).getCelula().getCodigoCelula(), ((Meta)valor).getRevisaoDeVidas().getCodigoRevisaoDeVidas() );
			System.out.println(m.getCelula().getNome());
			System.out.println(m.getItemsDoacaoMeta().size());
			itemsDoacaoMeta = m.getItemsDoacaoMeta();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void marcaAtivo(ItemDoacao id) {
		itemDoacaoMeta = id;
	}
	
	public void addItemDoacaoMeta() {
		if (checkBoxMarcado == true) {

			itensDoacaoSalvar.add(itemDoacaoMeta);
		} else {

			itensDoacaoSalvar.remove(itemDoacaoMeta);
		}

	}
	
	

}

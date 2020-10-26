package br.com.saraware.bean;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.omnifaces.util.Faces;
import org.primefaces.event.FlowEvent;

import br.com.saraware.dao.DAOEncontreiro;
import br.com.saraware.dao.DAOEncontrista;
import br.com.saraware.dao.DAOEncontristaReincidente;
import br.com.saraware.dao.DAOGrupoTrabalho;
import br.com.saraware.dao.DAOInscricao;
import br.com.saraware.dao.DAOIntervalo;
import br.com.saraware.dao.DAOItemCronograma;
import br.com.saraware.dao.DAOMembro;
import br.com.saraware.dao.DAOMinistracao;
import br.com.saraware.dao.DAOPessoa;
import br.com.saraware.dao.DAORevisao;
import br.com.saraware.dao.DAOTarefa;
import br.com.saraware.domain.Cronograma;
import br.com.saraware.domain.Encontreiro;
import br.com.saraware.domain.Encontrista;
import br.com.saraware.domain.GrupoTrabalho;
import br.com.saraware.domain.Inscricao;
import br.com.saraware.domain.Intervalo;
import br.com.saraware.domain.ItemCronograma;
import br.com.saraware.domain.Membro;
import br.com.saraware.domain.Ministracao;
import br.com.saraware.domain.Pessoa;
import br.com.saraware.domain.RevisaoDeVidas;
import br.com.saraware.domain.Tarefa;
import br.com.saraware.factory.ConexaoFactory;
import br.com.saraware.util.JSFUtil;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@ManagedBean(name = "MBRevisao")
@ViewScoped
public class RevisaoBean {
	private RevisaoDeVidas revisao;
	private ItemCronograma itemCronograma = new ItemCronograma();
	private Intervalo intervalo;
	private Inscricao inscricao = new Inscricao();
	private Membro membro = new Membro();
	private Encontreiro encontreiro = new Encontreiro();
	private GrupoTrabalho grupoTrabalho = new GrupoTrabalho();
	private Ministracao ministracao = new Ministracao();
	private Tarefa tarefa = new Tarefa();
	private ArrayList<Membro> membros;
	private ArrayList<Membro> membrosFiltrados;
	private ArrayList<Encontreiro> membrosEncontreiros;
	private ArrayList<Encontrista> encontristas;
	private ArrayList<Pessoa> encontristasReincidentes;
	private ArrayList<GrupoTrabalho> gruposTrabalho;
	private ArrayList<Tarefa> tarefas;
	private ArrayList<Encontreiro> comboEncontreiros;
	private ArrayList<Encontreiro> comboMinistrantes;
	private ArrayList<Encontreiro> comboMinistracao;
	private ArrayList<Encontreiro> tabelaEncontreiros = new ArrayList<Encontreiro>();;
	private ArrayList<Encontreiro> encontreirosSalvar = new ArrayList<Encontreiro>();
	private ArrayList<Encontreiro> encontreirosFiltrados;
	private ArrayList<Encontreiro> integrantes;
	private ArrayList<Encontreiro> alocadosTarefa;
	private ArrayList<Ministracao> ministracoes;
	private ArrayList<Ministracao> ministracoesFiltradas;
	private Boolean skip = false;
	private Boolean checkBoxMarcado = false;
	private HashMap<Integer, Boolean> checkBoxMarcadoEditar = new HashMap<Integer, Boolean>();
	private java.util.List<Encontreiro> items = new ArrayList<Encontreiro>();
	private String dataRevisao;
	private String inscricaoEncontrista;
	private String inscricaoEncontreiro;
	private ArrayList<ItemCronograma> itemsCronogramas = new ArrayList<ItemCronograma>();
	private boolean radioValue;
	private String comboLabel;
	private boolean renderIntervalo;
	private boolean renderMinistracao;

	public ArrayList<Encontreiro> getAlocadosTarefa() {
		return alocadosTarefa;
	}
	
	public void setAlocadosTarefa(ArrayList<Encontreiro> alocadosTarefa) {
		this.alocadosTarefa = alocadosTarefa;
	}
	
	public Tarefa getTarefa() {
		return tarefa;
	}
	
	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}
	
	public ArrayList<Tarefa> getTarefas() {
		return tarefas;
	}
	
	public void setTarefas(ArrayList<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}
	
	public Intervalo getIntervalo() {
		return intervalo;
	}
	
	public void setIntervalo(Intervalo intervalo) {
		this.intervalo = intervalo;
	}
	
	public ArrayList<Encontreiro> getComboMinistracao() {
		return comboMinistracao;
	}

	public void setComboMinistracao(ArrayList<Encontreiro> comboMinistracao) {
		this.comboMinistracao = comboMinistracao;
	}

	public boolean getRenderIntervalo() {
		return renderIntervalo;
	}
	
	public void setRenderMinistracao(boolean renderMinistracao) {
		this.renderMinistracao = renderMinistracao;
	}

	public void setRadioValue(boolean radioValue) {
		this.radioValue = radioValue;
	}

	public boolean getRenderMinistracao() {
		return renderMinistracao;
	}

	public void setRenderIntervalo(boolean renderIntervalo) {
		this.renderIntervalo = renderIntervalo;
	}

	public String getComboLabel() {
		return comboLabel;
	}

	public void setComboLabel(String comboLabel) {
		this.comboLabel = comboLabel;
	}

	public boolean getRadioValue() {
		return radioValue;
	}

	public void setItemsCronogramas(ArrayList<ItemCronograma> itemsCronogramas) {
		this.itemsCronogramas = itemsCronogramas;
	}

	public ArrayList<ItemCronograma> getItemsCronogramas() {
		return itemsCronogramas;
	}

	public void setItemCronograma(ItemCronograma itemCronograma) {
		this.itemCronograma = itemCronograma;
	}

	public ItemCronograma getItemCronograma() {
		return itemCronograma;
	}

	public void setCronograma(ItemCronograma itemCronograma) {
		this.itemCronograma = itemCronograma;
	}

	public String getInscricaoEncontreiro() {
		return inscricaoEncontreiro;
	}

	public void setInscricaoEncontreiro(String inscricaoEncontreiro) {
		this.inscricaoEncontreiro = inscricaoEncontreiro;
	}

	public String getInscricaoEncontrista() {
		return inscricaoEncontrista;
	}

	public void setInscricaoEncontrista(String inscricaoEncontrista) {
		this.inscricaoEncontrista = inscricaoEncontrista;
	}

	public String getDataRevisao() {
		return dataRevisao;
	}

	public void setDataRevisao(String dataRevisao) {
		this.dataRevisao = dataRevisao;
	}

	public ArrayList<Encontreiro> getComboMinistrantes() {
		return comboMinistrantes;
	}

	public void setComboMinistrantes(ArrayList<Encontreiro> comboMinistrantes) {
		this.comboMinistrantes = comboMinistrantes;
	}

	public Ministracao getMinistracao() {
		return ministracao;
	}

	public void setMinistracao(Ministracao ministracao) {
		this.ministracao = ministracao;
	}

	public ArrayList<Ministracao> getMinistracoesFiltradas() {
		return ministracoesFiltradas;
	}

	public void setMinistracoesFiltradas(ArrayList<Ministracao> ministracoesFiltradas) {
		this.ministracoesFiltradas = ministracoesFiltradas;
	}

	public ArrayList<Ministracao> getMinistracoes() {
		return ministracoes;
	}

	public void setMinistracoes(ArrayList<Ministracao> ministracoes) {
		this.ministracoes = ministracoes;
	}

	public java.util.List<Encontreiro> getItems() {
		return items;
	}

	public void setItems(java.util.List<Encontreiro> items) {
		this.items = items;
	}

	public HashMap<Integer, Boolean> getCheckBoxMarcadoEditar() {
		return checkBoxMarcadoEditar;
	}

	public void setCheckBoxMarcadoEditar(HashMap<Integer, Boolean> checkBoxMarcadoEditar) {
		this.checkBoxMarcadoEditar = checkBoxMarcadoEditar;
	}

	public ArrayList<Encontreiro> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(ArrayList<Encontreiro> integrantes) {
		this.integrantes = integrantes;
	}

	public Boolean getSkip() {
		return skip;
	}

	public void setSkip(Boolean skip) {
		this.skip = skip;
	}

	public ArrayList<Encontreiro> getTabelaEncontreiros() {
		return tabelaEncontreiros;
	}

	public void setTabelaEncontreiros(ArrayList<Encontreiro> tabelaEncontreiros) {
		this.tabelaEncontreiros = tabelaEncontreiros;
	}

	public ArrayList<Encontreiro> getEncontreirosFiltrados() {
		return encontreirosFiltrados;
	}

	public void setEncontreirosFiltrados(ArrayList<Encontreiro> encontreirosFiltrados) {
		this.encontreirosFiltrados = encontreirosFiltrados;
	}

	public ArrayList<Encontreiro> getEncontreirosSalvar() {
		return encontreirosSalvar;
	}

	public void setEncontreirosSalvar(ArrayList<Encontreiro> encontreirosSalvar) {
		this.encontreirosSalvar = encontreirosSalvar;
	}

	public Boolean getCheckBoxMarcado() {
		return checkBoxMarcado;
	}

	public void setCheckBoxMarcado(Boolean checkBoxMarcado) {
		this.checkBoxMarcado = checkBoxMarcado;
	}

	public ArrayList<Encontreiro> getComboEncontreiros() {
		return comboEncontreiros;
	}

	public void setComboEncontreiros(ArrayList<Encontreiro> comboEncontreiros) {
		this.comboEncontreiros = comboEncontreiros;
	}

	public GrupoTrabalho getGrupoTrabalho() {
		return grupoTrabalho;
	}

	public void setGrupoTrabalho(GrupoTrabalho grupoTrabalho) {
		this.grupoTrabalho = grupoTrabalho;
	}

	public ArrayList<GrupoTrabalho> getGruposTrabalho() {
		return gruposTrabalho;
	}

	public void setGruposTrabalho(ArrayList<GrupoTrabalho> gruposTrabalho) {
		this.gruposTrabalho = gruposTrabalho;
	}

	public ArrayList<Pessoa> getEncontristasReincidentes() {
		return encontristasReincidentes;
	}

	public void setEncontristasReincidentes(ArrayList<Pessoa> encontristasReincidentes) {
		this.encontristasReincidentes = encontristasReincidentes;
	}

	public ArrayList<Encontrista> getEncontristas() {
		return encontristas;
	}

	public void setEncontristas(ArrayList<Encontrista> encontristas) {
		this.encontristas = encontristas;
	}

	public Inscricao getInscricao() {
		return inscricao;
	}

	public void setInscricao(Inscricao inscricao) {
		this.inscricao = inscricao;
	}

	public ArrayList<Membro> getMembros() {
		return membros;
	}

	public void setMembros(ArrayList<Membro> membros) {
		this.membros = membros;
	}

	public RevisaoDeVidas getRevisao() {
		return revisao;
	}

	public void setRevisao(RevisaoDeVidas revisao) {
		this.revisao = revisao;
	}

	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}

	public ArrayList<Encontreiro> getMembrosEncontreiros() {
		return membrosEncontreiros;
	}

	public void setMembrosEncontreiros(ArrayList<Encontreiro> membrosEncontreiros) {
		this.membrosEncontreiros = membrosEncontreiros;
	}

	public void setMembrosFiltrados(ArrayList<Membro> membrosFiltrados) {
		this.membrosFiltrados = membrosFiltrados;
	}

	public ArrayList<Membro> getMembrosFiltrados() {
		return membrosFiltrados;
	}

	public Encontreiro getEncontreiro() {
		return encontreiro;
	}

	public void setEncontreiro(Encontreiro encontreiro) {
		this.encontreiro = encontreiro;
	}

	@PostConstruct
	public void prepararPesquisa() {
		try {
			DAOMembro dao = new DAOMembro();
			DAOEncontreiro daoe = new DAOEncontreiro();
			DAOEncontrista daoen = new DAOEncontrista();
			DAOEncontristaReincidente daoer = new DAOEncontristaReincidente();
			DAOGrupoTrabalho daogt = new DAOGrupoTrabalho();
			DAOMinistracao daom = new DAOMinistracao();
			DAOItemCronograma daoi = new DAOItemCronograma();
			DAOTarefa daot = new DAOTarefa();

			membros = dao.listar();
			membrosEncontreiros = daoe.listar();
			encontristasReincidentes = daoer.listarCompleto();
			encontristas = daoen.listar();
			gruposTrabalho = daogt.listar();
			ministracoes = daom.listar();
			itemsCronogramas = daoi.listar();
			tarefas = daot.listar();
			renderIntervalo = Boolean.FALSE;
			renderMinistracao  = Boolean.TRUE;
			radioValue = Boolean.TRUE;
		

		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void addMembros(Membro membro) {
		DAOEncontreiro dao = new DAOEncontreiro();
		try {
			encontreiro.setCodigoMembro(membro.getCodigoMembro());
			encontreiro.setCodigoPessoa(membro.getCodigoPessoa());
			encontreiro.setNome(membro.getNome());
			encontreiro.setEndereco(membro.getEndereco());
			encontreiro.setTelefone(membro.getTelefone());
			encontreiro.setDataBatismo(membro.getDataBatismo());
			encontreiro.setDataNascimento(membro.getDataNascimento());
			encontreiro.setNumeroDocumento(membro.getNumeroDocumento());
			encontreiro.setTipoDocumento(membro.getTipoDocumento());
			encontreiro.setHierarquia(membro.getHierarquia());
			encontreiro.setEstadoCivil(membro.getEstadoCivil());
			encontreiro.setStatusIV(membro.getStatusIV());
			encontreiro.setSexo(membro.getSexo());
			encontreiro.setAtivo(membro.getAtivo());

			dao.cadastrar(encontreiro);
			membrosEncontreiros = dao.listar();
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void excluirEncontreiro(Encontreiro encontreiro) {
		DAOEncontreiro dao = new DAOEncontreiro();
		try {
			dao.deletar(encontreiro);
			membrosEncontreiros = dao.listar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void excluirEncontrista(Pessoa encontrista) {
		DAOPessoa dao = new DAOPessoa();
		try {
			dao.deletarEncontrista(encontrista);
			DAOEncontrista daoen = new DAOEncontrista();
			DAOEncontristaReincidente daoer = new DAOEncontristaReincidente();
			encontristasReincidentes = daoer.listarCompleto();
			encontristas = daoen.listar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void inscreverEncontreiro() {

	}

	public void prepararNovoGrupoTrabalho() {
		grupoTrabalho = new GrupoTrabalho();
		DAOEncontreiro dao = new DAOEncontreiro();
		try {
			comboEncontreiros = dao.listar();
			tabelaEncontreiros = dao.listar();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void novoGrupoTrabalho() {
		try {
			DAOGrupoTrabalho dao = new DAOGrupoTrabalho();
			grupoTrabalho.setIntegrantes(encontreirosSalvar);
			dao.cadastrar(grupoTrabalho);

			gruposTrabalho = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Grupo de Trabalho salvo com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void novoMinistracao() {
		try {
			DAOMinistracao dao = new DAOMinistracao();
			dao.cadastrar(ministracao);

			ministracoes = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Ministração salva com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void novoItemCronograma() {
		try {
			DAOItemCronograma dao = new DAOItemCronograma();
			DAOMinistracao daom = new DAOMinistracao();
			DAOIntervalo daoi = new DAOIntervalo();
			Cronograma cronograma = new Cronograma();
			Ministracao m = new Ministracao();
			Intervalo i = new Intervalo();
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(dataRevisao);
			
			revisao = new RevisaoDeVidas();
			revisao.setDataDeAcontecimento(date);
			cronograma.setRevisaoDeVidas(revisao);
			itemCronograma.setCronograma(cronograma);
			
			if (radioValue) {
				
				m = daom.getByCodigo(ministracao.getCodigoMinistracao());
				
				itemCronograma.setMinistracao(m);
				itemCronograma.setDescricao("MINISTRAÇÃO");
				dao.cadastrarComMinistracao(itemCronograma);
				
			}else
			{
				daoi.cadastrar(intervalo);
				i = daoi.getByNome(intervalo.descricaoIntervalo);
				itemCronograma.setIntervalo(i);
				itemCronograma.setDescricao("INTERVALO");
				dao.cadastrar(itemCronograma);
			}
			
			itemsCronogramas = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Item de cronograma salvo com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void editarItemCronograma() {
		try {
			DAOItemCronograma dao = new DAOItemCronograma();
			DAOIntervalo daoi = new DAOIntervalo();
			
			daoi.alterar(itemCronograma.getIntervalo());
			dao.alterar(itemCronograma);

			itemsCronogramas = dao.listar();

			JSFUtil.adicionarMensagemSucesso("Item de Cronograma alterado com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	
	public void excluirItemCronograma() {
		try {
			DAOItemCronograma dao = new DAOItemCronograma();
			dao.deletar(itemCronograma);

			itemsCronogramas = dao.listar();

			JSFUtil.adicionarMensagemSucesso("Item de cronograma removido com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}


	public void marcaAtivo(Encontreiro e) {
		encontreiro = e;
	}

	public void addEncontreiroGrupoTrabalho() {
		if (checkBoxMarcado == true) {

			encontreirosSalvar.add(encontreiro);
		} else {

			encontreirosSalvar.remove(encontreiro);
		}

	}

	public void marcaEncontreiroGrupoTrabalho() {

		/*
		 * try { java.util.List<Encontreiro> checkedItems = new
		 * ArrayList<Encontreiro>(); System.out.println("TT"); for (Encontreiro
		 * item : items) {
		 * 
		 * if (checkBoxMarcadoEditar.get(item.getCodigoEncontreiro())) {
		 * Encontreiro e = new Encontreiro(); DAOEncontreiro daoe = new
		 * DAOEncontreiro(); e = daoe.getByCodigo(item.getCodigoEncontreiro());
		 * checkedItems.add(e); checkBoxMarcadoEditar.clear(); } } } catch
		 * (Exception e) { e.printStackTrace(); }
		 */

	}

	public void recarregaListaEncontreiros(AjaxBehaviorEvent ev) {
		Map<String, Object> map = ev.getComponent().getAttributes();
		Integer valor = (Integer) map.get("value");
		DAOEncontreiro dao = new DAOEncontreiro();
		try {
			tabelaEncontreiros = new ArrayList<Encontreiro>();
			tabelaEncontreiros = dao.listarSemCoordenador(valor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void prepararDetalhesGrupoTrabalho(GrupoTrabalho valor) {
		try {
			DAOGrupoTrabalho dao = new DAOGrupoTrabalho();
			GrupoTrabalho gt = new GrupoTrabalho();
			System.out.println(valor.getDescricao());
			gt = dao.getByNome(((GrupoTrabalho) valor).getDescricao());
			
			integrantes = gt.getIntegrantes();

		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararEditarGrupoTrabalho(Integer codigo) {

		DAOEncontreiro dao = new DAOEncontreiro();
		DAOGrupoTrabalho daogt = new DAOGrupoTrabalho();
		GrupoTrabalho gt = new GrupoTrabalho();
		try {
			gt = daogt.getByCodigo(codigo);
			comboEncontreiros = dao.listar();
			tabelaEncontreiros = dao.listarEChecar(codigo, gt.getCoordenador().getCodigoEncontreiro());
			for (Encontreiro en : getTabelaEncontreiros()) {
				if (en.getChecked()) {
					checkBoxMarcadoEditar.put(en.getCodigoEncontreiro(), true);
				} else {
					checkBoxMarcadoEditar.put(en.getCodigoEncontreiro(), false);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void excluirGrupoTrabalho() {
		try {
			DAOGrupoTrabalho dao = new DAOGrupoTrabalho();
			dao.deletar(grupoTrabalho);

			gruposTrabalho = dao.listar();

			JSFUtil.adicionarMensagemSucesso("Grupo de trabalho removido com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void editarGrupoTrabalho() {
		try {
			DAOGrupoTrabalho dao = new DAOGrupoTrabalho();
			for (Encontreiro en : getTabelaEncontreiros()) {
				if (checkBoxMarcadoEditar.get(en.getCodigoEncontreiro())) {
					items.add(en);
				}
			}

			grupoTrabalho.setIntegrantes((ArrayList<Encontreiro>) items);
			dao.alterar(grupoTrabalho);

			gruposTrabalho = dao.listar();

			JSFUtil.adicionarMensagemSucesso("Grupo de Trabalho alterado com sucesso!");

		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararEditarMinistracao() {
		DAOEncontreiro dao = new DAOEncontreiro();
		try {
			comboMinistrantes = dao.listarDoze();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void editarMinistracao() {
		try {
			DAOMinistracao dao = new DAOMinistracao();
			dao.alterar(ministracao);

			ministracoes = dao.listar();

			JSFUtil.adicionarMensagemSucesso("Ministração alterada com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void excluirMinistracao() {
		try {
			DAOMinistracao dao = new DAOMinistracao();
			dao.deletar(ministracao);

			ministracoes = dao.listar();

			JSFUtil.adicionarMensagemSucesso("Ministração removida com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovoMinistracao() {
		ministracao = new Ministracao();
		DAOEncontreiro dao = new DAOEncontreiro();
		try {
			comboMinistrantes = dao.listarDoze();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void novoRevisao() {
		revisao = new RevisaoDeVidas();
		inscricao = new Inscricao();
		Pessoa p = new Pessoa();
		DAOInscricao daoi = new DAOInscricao();
		DAORevisao dao = new DAORevisao();
		try {
			for (Encontreiro en : getMembrosEncontreiros()) {

				p.setCodigoPessoa(en.getCodigoPessoa());
				inscricao.setPessoa(p);
				daoi.cadastrar(inscricao, Double.parseDouble(inscricaoEncontreiro));
			}

			for (Encontrista enc : getEncontristas()) {

				p.setCodigoPessoa(enc.getCodigoPessoa());
				inscricao.setPessoa(p);
				daoi.cadastrar(inscricao, Double.parseDouble(inscricaoEncontrista));
			}

			for (Pessoa encr : getEncontristasReincidentes()) {

				p.setCodigoPessoa(encr.getCodigoPessoa());
				inscricao.setPessoa(p);
				daoi.cadastrar(inscricao, Double.parseDouble(inscricaoEncontrista));
			}

			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(dataRevisao);
			revisao.setDataDeAcontecimento(date);
			dao.alterar(revisao);
			JSFUtil.adicionarMensagemSucesso("Revisão de Vidas salvo com sucesso!");
			revisao.setDataDeAcontecimento(date);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}

	}

	public void prepararNovoItemCronograma() {
		
		itemCronograma = new ItemCronograma();
		intervalo = new Intervalo();
	}
	
	public void prepararEditarItemCronograma() {
		DAOMinistracao dao = new DAOMinistracao();
		try {
			ministracoes = dao.listar();
			radioValue = Boolean.TRUE;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void mudaListagemItemCronograma() {
		itemCronograma = new ItemCronograma();
		try {
			//DAOItemCronograma dao = new DAOItemCronograma();
			if (radioValue) {
				comboLabel = " uma ministração";
				renderMinistracao = Boolean.TRUE;
				renderIntervalo = Boolean.FALSE;
			} else {
				comboLabel = " um intervalo";
				renderMinistracao = Boolean.FALSE;
				renderIntervalo = Boolean.TRUE;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}

	}
	
	public void prepararDetalhesEscala(Object valor) {
		try {
			DAOTarefa dao = new DAOTarefa();
			Tarefa t = new Tarefa();
			t = dao.getByNome(((Tarefa) valor).getDescricaoTarefa());
			alocadosTarefa = t.getEncontreiros();

		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void prepararNovoEscala() {
		tarefa = new Tarefa();
		DAOEncontreiro dao = new DAOEncontreiro();
		encontreirosSalvar = new ArrayList<Encontreiro>();
		try {
			comboEncontreiros = dao.listar();
			tabelaEncontreiros = dao.listar();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void novoEscala() {
		try {
			DAOTarefa dao = new DAOTarefa();
			tarefa.setEncontreiros(encontreirosSalvar);
			dao.cadastrar(tarefa);

			tarefas = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Tarefa salva com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void carregaListaEncontreiros(AjaxBehaviorEvent ev) {
		Map<String, Object> map = ev.getComponent().getAttributes();
		Integer valor = (Integer) map.get("value");
		DAOGrupoTrabalho dao = new DAOGrupoTrabalho();
		GrupoTrabalho gt = new GrupoTrabalho();
		try {
			
			gt = dao.getByCodigo(valor);
			tabelaEncontreiros = new ArrayList<Encontreiro>();
			tabelaEncontreiros = gt.getIntegrantes();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void prepararEditarEscala(Integer codigo) {

		DAOEncontreiro dao = new DAOEncontreiro();
		try {
			tabelaEncontreiros = dao.listarEChecar(codigo);
			for (Encontreiro en : getTabelaEncontreiros()) {
				if (en.getChecked()) {
					checkBoxMarcadoEditar.put(en.getCodigoEncontreiro(), true);
				} else {
					checkBoxMarcadoEditar.put(en.getCodigoEncontreiro(), false);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void editarEscala() {
		try {
			DAOTarefa dao = new DAOTarefa();
			for (Encontreiro en : getTabelaEncontreiros()) {
				if (checkBoxMarcadoEditar.get(en.getCodigoEncontreiro())) {
					items.add(en);
				}
			}

			tarefa.setEncontreiros((ArrayList<Encontreiro>) items);
			dao.alterar(tarefa);

			tarefas = dao.listar();

			JSFUtil.adicionarMensagemSucesso("Tarefa alterada com sucesso!");

		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}


	public void excluirEscala() {
		try {
			DAOTarefa dao = new DAOTarefa();
			dao.deletar(tarefa);

			tarefas = dao.listar();

			JSFUtil.adicionarMensagemSucesso("Tarefa removida com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}


	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}
	
	
	public void imprimir ()
	{
		
		try {
			String caminho = Faces.getRealPath("/reports/cronograma.jasper");
			Map<String, Object> parametros = new HashMap<>();
			Connection conexao = ConexaoFactory.getConnection();
			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			JasperPrintManager.printReport(relatorio, true);
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
		
		
		
	}
	
	public void imprimirEscala ()
	{
		
		try {
			String caminho = Faces.getRealPath("/reports/teste.jasper");
			Map<String, Object> parametros = new HashMap<>();
			Connection conexao = ConexaoFactory.getConnection();
			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			JasperPrintManager.printReport(relatorio, true);
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
		
		
		
	}
	
}

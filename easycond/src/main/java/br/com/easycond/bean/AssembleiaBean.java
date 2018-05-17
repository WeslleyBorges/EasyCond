package br.com.easycond.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.PieChartModel;

import br.com.easycond.model.Assembleia;
import br.com.easycond.model.Enquete;
import br.com.easycond.model.Votos;
import br.com.easycond.rn.AssembleiaRN;
import br.com.easycond.rn.VotosRN;
import br.com.easycond.util.SpringUtil;

@ManagedBean(name = "assembleiaBean")
@RequestScoped
public class AssembleiaBean {
	
	private Assembleia assembleia = new Assembleia();
	private Enquete enquete = new Enquete();
	private Votos votos = new Votos();
	
	private String perguntaEnquete;
	
	private List<Assembleia> lista;
	private List<Votos> listaVotosContra;
	private List<Votos> listaVotosFavor;
	
	private PieChartModel graficoVotos;
	
	@PostConstruct
	public String novo() {
		this.assembleia = new Assembleia();
		this.enquete = new Enquete();
		perguntaEnquete = null;
		return "/adm/assembleia/cadastrar";
	}
	
	public String carregarEnquete() {
		this.assembleia = new Assembleia();
		this.votos = new Votos();
		AssembleiaRN assembleiaRN = new AssembleiaRN();
		assembleia = assembleiaRN.carregarAssembleia();
		return "/restrito/assembleia/votacao";
	}
	
	public String carregarListaVotos() {
		this.assembleia = new Assembleia();
		this.votos =  new Votos();
		AssembleiaRN assembleiaRN = new AssembleiaRN();
		VotosRN votosRN = new VotosRN();
		assembleia = assembleiaRN.carregarAssembleia();
		
		if (this.listaVotosContra == null) {
			
			this.listaVotosContra = votosRN.carregarVotosContraEnquete(assembleia.getEnquete().getId());
		}
		
		if (this.listaVotosFavor == null) {
			this.listaVotosFavor = votosRN.carregarVotosFavorEnquete(assembleia.getEnquete().getId());
		}
		
		carregarGrafico();
		
		return "/restrito/assembleia/resultado";
		
	}
	
	public PieChartModel carregarGrafico() {
		graficoVotos = new PieChartModel();
		
		graficoVotos.getData().put("A Favor", listaVotosFavor.size());
		graficoVotos.getData().put("Contra", listaVotosContra.size());
		
		graficoVotos.setTitle("Resultado");
		graficoVotos.setLegendPosition("w");
		
		return graficoVotos;
	}
	
	public String salvar() {
		
		if (enquete != null) {
			assembleia.setEnquete(enquete);
			enquete.setPergunta(perguntaEnquete);
			enquete.setAssembleia(assembleia);
		}
		
		AssembleiaRN assembleiaRN = new AssembleiaRN();
		assembleiaRN.salvar(this.assembleia);
		
		return "/adm/assembleia/lista";
	}
	
	public String enviarVoto() {
		AssembleiaRN assembleiaRN = new AssembleiaRN();
		assembleia = assembleiaRN.carregarAssembleia();
		votos.setIdEnquete(assembleia.getEnquete());
		votos.setUsuario(SpringUtil.obterUsuarioLogado());
		VotosRN votosRN = new VotosRN();
		votosRN.salvar(this.votos);
		
		carregarListaVotos();
		
		return "/restrito/assembleia/resultado";
		
	}
	
	public String editar() {
		perguntaEnquete = assembleia.getEnquete().getPergunta();
		return "/adm/assembleia/cadastrar";
	}
	
	public String excluir() {
		AssembleiaRN assembleiaRN = new AssembleiaRN();
		assembleiaRN.excluir(this.assembleia);
		this.lista = null;
		return null;
	}

	public Assembleia getAssembleia() {
		return assembleia;
	}

	public void setAssembleia(Assembleia assembleia) {
		this.assembleia = assembleia;
	}

	public Enquete getEnquete() {
		return enquete;
	}

	public void setEnquete(Enquete enquete) {
		this.enquete = enquete;
	}
	
	public Votos getVotos() {
		return votos;
	}

	public void setVotos(Votos votos) {
		this.votos = votos;
	}

	public String getPerguntaEnquete() {
		return perguntaEnquete;
	}

	public void setPerguntaEnquete(String perguntaEnquete) {
		this.perguntaEnquete = perguntaEnquete;
	}

	public PieChartModel getGraficoVotos() {
		return graficoVotos;
	}

	public void setGraficoVotos(PieChartModel graficoVotos) {
		this.graficoVotos = graficoVotos;
	}

	public List<Assembleia> getLista() {
		if (this.lista == null) {
			AssembleiaRN assembleiaRN = new AssembleiaRN();
			this.lista = assembleiaRN.listar();
		}
		return this.lista;
	}

	public List<Votos> getListaVotosContra() {
		return listaVotosContra;
	}

	public void setListaVotosContra(List<Votos> listaVotosContra) {
		this.listaVotosContra = listaVotosContra;
	}

	public List<Votos> getListaVotosFavor() {
		return listaVotosFavor;
	}

	public void setListaVotosFavor(List<Votos> listaVotosFavor) {
		this.listaVotosFavor = listaVotosFavor;
	}	
	
}

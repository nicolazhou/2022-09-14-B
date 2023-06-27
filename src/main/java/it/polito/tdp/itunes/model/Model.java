package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private ItunesDAO dao;
	
	//private List<Integer> lista;
	
	private Graph<Album, DefaultEdge> grafo;
	
	private List<Album> vertici;
	
	
	// Ricorsione
	private List<Album> valide;
	private List<Album> soluzione;
	private int massimo;
	
	public Model() {
		
		this.dao = new ItunesDAO();
		
		//this.lista = new ArrayList<>();
		
	}
	
	
	public void creaGrafo(int d) {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
	
		this.vertici = new ArrayList<>(this.dao.getVertici(d));
		
		// Vertici:
		Graphs.addAllVertices(this.grafo, this.vertici);

		
		
		// Archi
		for(Album a1 : this.grafo.vertexSet()) {
			for(Album a2: this.grafo.vertexSet()) {
				
				if(!a1.equals(a2)) {
					
					int num = this.dao.getNumPlaylist(a1, a2);
					
					if(num > 0) {
						
						this.grafo.addEdge(a1, a2);
						
					}
					
				}
				
			}
			
		}
		
		
	}
	
	public List<Album> getListaConnessa(Album a1) {
		
		ConnectivityInspector<Album, DefaultEdge> ci = new ConnectivityInspector<Album, DefaultEdge>(this.grafo);
		
		List<Album> result = new ArrayList<>(ci.connectedSetOf(a1));
		
		return result;
	}
	
	
	public int getNumBrani(Album a) {
		
		return this.dao.getNumBrani(a);
	}
	
	public int getNumBraniTot(List<Album> lista) {
		
		int res = 0;
		
		for(Album a : lista) {
			
			res += this.dao.getNumBrani(a);
		}
		
		return res;
	}
	
	
	public int getNNodes() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	public boolean isGrafoLoaded() {
		
		if(this.grafo == null)
			return false;
		
		return true;
	}


	public List<Album> getVertici() {
		return vertici;
	}
	
	
public Set<Album> trova(Album a1, int dTOT) {
		
		// Inizializzazione
		List<Album> parziale = new ArrayList<>();
		this.valide = this.getListaConnessa(a1);
		this.soluzione = new ArrayList<>();
		//this.massimo = 0;
		
		System.out.println(a1 + " tempo: " + a1.getDurata());
		System.out.println("dTOT: " + (dTOT*60000));
		if(a1.getDurata() > dTOT*60000) {
			
			return null;
			
		}
		
		
		// Avvia Ricorsione
		parziale.add(a1); // Contenga a1
		cerca(parziale, valide, dTOT);
		
		System.out.println("Soluzione " + soluzione);
		
		return new HashSet<Album>(this.soluzione);
		
	}
	
	
	public void cerca(List<Album> parziale, List<Album> valide, int dTOT) {
		
		//System.out.println("Valide size: " + valide.size());
		System.out.println(parziale);
		
		// Condizione di terminazione
		if(sommaTempo(parziale) > sommaTempo(this.soluzione) && parziale.size() >= this.soluzione.size()) { 
			
			this.soluzione = new ArrayList<>(parziale);
			
		}
		
		
		// Se non siamo nella condizione di terminazione, andiamo avanti
		for(Album a : valide) {
			
			if(!parziale.contains(a)) {
				
				parziale.add(a);
				
				if(sommaTempo(parziale) <= dTOT*60000) {

					cerca(parziale, valide, dTOT);
				}
				
				//backtracking
				parziale.remove(parziale.size()-1);
				
			}
				
		}
		
	}


	private double sommaTempo(List<Album> lista) {
		// TODO Auto-generated method stub
		double tempo = 0;
		
		for(Album a : lista) {
			
			System.out.println(a + " tempo: " + a.getDurata());
			
			tempo += a.getDurata();
			
		}
		
		return tempo;
	}
	
	
	
}

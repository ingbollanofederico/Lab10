package it.polito.tdp.porto.model;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

import java.util.*;

public class Model {
	//1. creo
	private Graph<Author, Adiacenza> grafo;
	private Map<Integer,Author> autoriIdMap;
	private List<Adiacenza> adiacenze;
	
	//2. inizializzo
	public Model() {
		this.autoriIdMap = new HashMap<Integer,Author>();
	}
	
	//grafo
	public void creaGrafo() {
		this.grafo = new SimpleGraph<>(Adiacenza.class);
		//completo idMap e cmbbox
		PortoDAO dao = new PortoDAO();
		dao.listAutori(autoriIdMap);
		
		//creo vertici
		Graphs.addAllVertices(grafo, this.autoriIdMap.values());
		
		//creo archi
		this.adiacenze = dao.listAdiacenze(autoriIdMap);
		for(Adiacenza a : adiacenze) {
			this.grafo.addEdge(a.getAutore1(), a.getAutore2(),a); //metodo per edge diversi da dafault
		}
		
		System.out.println("Grafo creato con vertici: "+grafo.vertexSet().size()+" e archi: "+grafo.edgeSet().size());
		
	}
	
	
	public List<Adiacenza> trovaCamminoMinimo(Author a1, Author a2){
		//creo algoritmo cammino minimo
		DijkstraShortestPath<Author,Adiacenza> dijkstra = new DijkstraShortestPath<>(this.grafo);
		
		//creo nuovo grafo di percorso minimo
		GraphPath<Author,Adiacenza> path = dijkstra.getPath(a1, a2);
		return path.getEdgeList();
	}
	
	public List<Author> coAutori(Author autore){
		return Graphs.neighborListOf(grafo, autore);
	}
	
	
	public List<Author> listaAutori(){
		PortoDAO dao = new PortoDAO();
		List<Author> autori = dao.listAutori(autoriIdMap);
		Collections.sort(autori);
		return autori;
	}

	public List<Author> noCoautori(Author value) {
		List<Author> coautori = this.coAutori(value);
		List<Author> nocoautori = new ArrayList<Author>();
		for(Author a: this.autoriIdMap.values()) {
				if(!coautori.contains(a) && !a.equals(value)) {
				nocoautori.add(a);
				}
			}
		Collections.sort(nocoautori);
		return nocoautori;
	}

}

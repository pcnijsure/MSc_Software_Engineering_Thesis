package ch.uzh.ifi.seal.monolith2microservices.models.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.*;

@Entity
public class Microservice {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ElementCollection
	private List<String> classFiles = new ArrayList<>();
	
	@Transient
	private List<Edge> graph = new ArrayList<>();
	
	@ManyToMany(cascade={CascadeType.REMOVE})
	private List<Microservice> relations = new ArrayList<>();
	
	@Column(unique=true)
	private String hash;
	
	private int score;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getClasses() {
		return classFiles;
	}

	public void setClasses(List<String> classes) {
		this.classFiles = classes;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public void addRelation(Microservice m){
		this.relations.add(m);
	}
	
	public List<Microservice> getRelations() {
		return relations;
	}

	public void setRelations(List<Microservice> relations) {
		this.relations = relations;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void removeClass(String cls){
		for(Iterator<String> iterator = this.classFiles.iterator(); iterator.hasNext();){
			String current = iterator.next();
			if(current.equals(cls)){
				iterator.remove();
				return;
			}
		}
	}
	
	
	public void addEdge(Edge edge){
		this.graph.add(edge);
	}
	

	public void addClass(String cls){
		this.classFiles.add(cls);
	}
	
	
	@Override
	public String toString() {
		return "Microservice [id=" + id + ", classes=" + classFiles + ", hash=" + hash + "]";
	}
	
}

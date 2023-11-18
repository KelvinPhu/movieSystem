package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "actors")
public class actors {
	
	@Id
	@GeneratedValue
	private Long actorsID;
	private String actorsName;
	private int yearOfBirth;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "actors_to_movies",
			   joinColumns = {@JoinColumn(name="actorsID")},
			   inverseJoinColumns = {@JoinColumn(name="moviesID")}
			)
	private Set<movies> moviesList = new HashSet<movies>();
	
	// constructor
	public actors(Long actorsID, String actorsName, int yearOfBirth) {
		super();
		this.actorsID = actorsID;
		this.actorsName = actorsName;
		this.yearOfBirth = yearOfBirth;
	}
	public actors() {
		super();
	}

	// getter & setter
	public Long getActorsID(){
		return actorsID;
	}

	public void setActorsID(Long actorsID) {
		this.actorsID = actorsID;
	}

	public String getActorsName() {
		return actorsName;
	}

	public void setActorsName(String actorsName) {
		this.actorsName = actorsName;
	}

	public int getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public Set<movies> getMoviesList() {
		return moviesList;
	}

	public void setMoviesList(Set<movies> moviesList) {
		this.moviesList = moviesList;
	}
}

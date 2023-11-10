package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "movies")
public class movies {
	@Id
	@GeneratedValue
	private Long moviesID;
	private String moviesTitle;
	private int yearOfReleast;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "genresID")
	private genres genre;
	
	@ManyToMany(mappedBy = "moviesList", cascade = CascadeType.ALL)
	private Set<actors> actorsList = new HashSet<actors>();
	
	
	// constructor
	public movies(Long moviesID, String moviesTitle, int yearOfReleast, genres genre, Set<actors> actorsList) {
		super();
		this.moviesID = moviesID;
		this.moviesTitle = moviesTitle;
		this.yearOfReleast = yearOfReleast;
		this.genre = genre;
		this.actorsList = actorsList;
	}

	public movies() {
		super();
	}

	// getter & setter
	public int getYearOfReleast() {
		return yearOfReleast;
	}

	public void setYearOfReleast(int yearOfReleast) {
		this.yearOfReleast = yearOfReleast;
	}

	public genres getGenre() {
		return genre;
	}

	public void setGenre(genres genre) {
		this.genre = genre;
	}

	public Long getMoviesID() {
		return moviesID;
	}

	public void setMoviesID(Long moviesID) {
		this.moviesID = moviesID;
	}

	public String getMoviesTitle() {
		return moviesTitle;
	}

	public void setMoviesTitle(String moviesTitle) {
		this.moviesTitle = moviesTitle;
	}

	public Set<actors> getActorsList() {
		return actorsList;
	}

	public void setActorsList(Set<actors> actorsList) {
		this.actorsList = actorsList;
	}
	
	
}

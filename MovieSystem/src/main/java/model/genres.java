package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "genres")
public class genres {
	@Id
	@GeneratedValue
	private Long genresID;
	private String genresName;
	
	@OneToMany(mappedBy = "genre", fetch = FetchType.EAGER)
	private List<movies> moviesList;
	
	// constructor
	public genres() {
		super();
	}

	public genres(Long genresID, String genresName, List<movies> moviesList) {
		super();
		this.genresID = genresID;
		this.genresName = genresName;
		this.moviesList = moviesList;
	}

	public genres(Long genresID, String genresName, List<movies> moviesList){
		super();
		this.genresID = genresID;
		this.genresName = genresName;
		this.movieList = movieList;
	}

	// setter & getter
	public Long getGenresID() {
		return genresID;
	}

	public void setGenresID(Long genresID) {
		this.genresID = genresID;
	}

	public String getGenresName() {
		return genresName;
	}

	public void setGenresName(String genresName) {
		this.genresName = genresName;
	}

	public List<movies> getMoviesList() {
		return moviesList;
	}

	public void setMoviesList(List<movies> moviesList) {
		this.moviesList = moviesList;
	}
	
	public void addMovie(movies movie) {
		if (moviesList == null) {
	        moviesList = new ArrayList<>();
	    }
	    moviesList.add(movie);
	    movie.setGenre(this);
	}
}

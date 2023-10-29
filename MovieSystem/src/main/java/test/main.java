package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import DAO.actorRepository;
import DAO.genreRespository;
import DAO.movieRepository;
import model.actors;
import model.genres;
import model.movies;
import util.hibernateUtil;


public class main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		
		do {
			System.out.println("========================");
			System.out.println("Movies System Management");
			System.out.println("========================");
			System.out.println("Option 1 Movies \r\n"
					 + "Option 2 Genres \r\n"
					 + "Option 3 Actors \r\n");
			choice = sc.nextInt();
			sc.nextLine();
			
			switch (choice) {
				
				case 1:
					
					System.out.println("Option with Movies");
					System.out.println("1. Adding a new movie record \r\n"
									 + "2. Deleting movie by ID \r\n"
									 + "3. Searching movie record by title (by name) \r\n"
									 + "4. Return all record \r\n"
									 + "5. Retrieving all records with Actors appearing \r\n");
					choice = sc.nextInt();
					sc.nextLine();
					
					switch (choice) {
						case 1:
							
							// movie detail section
							System.out.println("Enter title for new movie: ");
							String movieTitle = sc.nextLine();
							System.out.println("Enter year of release for new movie: ");
							int yearOfReleast = sc.nextInt();
							sc.nextLine();
							movies m = new movies();
							m.setMoviesTitle(movieTitle);
							m.setYearOfReleast(yearOfReleast);
							
							// movie genres detail section
							List<genres> genresList = new ArrayList<>();
							System.out.println("How many genres does the movie have?");
							int genreNumber = sc.nextInt();
							sc.nextLine();
							for (int i = 0; i < genreNumber; i++) {
								System.out.println("enter genres for the movie: ");
								String genreName = sc.nextLine();
								genres g = new genres();
								g.setGenresName(genreName);
								g.addMovie(m);
								genresList.add(g);
								
							}
							
							
							// movie actor detail section
							List<actors> actorsList = new ArrayList<>();
							System.out.println("How many actors in this movie ?");
							int actorNumber = sc.nextInt();
							sc.nextLine();
							for (int i = 0; i < actorNumber; i++) {
								System.out.println("Enter the actor Name: ");
								String actorName = sc.nextLine();
								System.out.println("Enter the actor year of birth: ");
								int yearOfBirth = sc.nextInt();
								sc.nextLine();
								actors a = new actors();
								a.setActorsName(actorName);
								a.setYearOfBirth(yearOfBirth);
								a.getMoviesList().add(m);
								actorsList.add(a);
							}
							
							Session s = hibernateUtil.getSessionFactory().openSession();
							Transaction trans = s.beginTransaction();
								
							try {
								// insert to table
								movieRepository mr = new movieRepository();
								genreRespository gr = new genreRespository();
								actorRepository ar = new actorRepository();
								for (genres g : genresList) {
								    gr.insert(g);
								}
								for (actors a : actorsList) {
								    ar.insert(a);
								}
								mr.insert(m);
								
								trans.commit();
							} catch (Exception e) {
						        e.printStackTrace();
						        trans.rollback();
						    } finally {
						        s.close();
						    }
							break;
							
						case 2:
							s = hibernateUtil.getSessionFactory().openSession();
							try {
								Query query = s.createQuery("select m.moviesID from movies m ", Long.class);
								List<Long> movieID = query.getResultList();
								for (long id : movieID) {
									System.out.println("movies ID: " +id);
								}
								
								System.out.println("Select movie ID to delete: ");
								Long moviesID = sc.nextLong();
								m = new movies();
								m.setMoviesID(moviesID);
								
								trans = s.beginTransaction();
								movieRepository mr = new movieRepository();
								mr.delete(moviesID);
								trans.commit();
							}catch (Exception e) {
								e.printStackTrace();
							}finally {
								s.close();
							}
							break;
							
						case 3:
						    s = hibernateUtil.getSessionFactory().openSession();
						    trans = null;
						    try {
						        Query query = s.createQuery("select m.moviesID, m.moviesTitle, m.yearOfReleast from movies m", Object[].class);
						        List<Object[]> moviesInfo = query.getResultList();
						        
						        System.out.println("Select a movie title to see the information: ");
						        String selectedTitle = sc.nextLine();
						        
						        for (Object[] movie : moviesInfo) {
						            Long movieID = (Long) movie[0];
						            movieTitle = (String) movie[1];
						            yearOfReleast = (Integer) movie[2];
						            
						            if (selectedTitle.equalsIgnoreCase(movieTitle)) {
						                m = new movies();
						                m.setMoviesID(movieID);
						                m.setMoviesTitle(movieTitle);
						                m.setYearOfReleast(yearOfReleast);
						                
						                System.out.println("=======================");
						                System.out.println("Movie ID: " + m.getMoviesID());
						                System.out.println("Movie Title: " + m.getMoviesTitle());
						                System.out.println("Year of Release: " + m.getYearOfReleast());
//						                System.out.println("Genres: ");
//						                m = new movies();
//						                Query genresQuery = s.createQuery("select g.genresName from genres g join g.moviesList m where m.moviesID = :movieID", String.class);
//						                genresQuery.setParameter("movieID", m.getMoviesID());
//						                List<String> genreList = genresQuery.getResultList();
//							            for (String genres	 : genreList) {
//											System.out.println(genres);
//										}
//							            
//						                System.out.println("Actors: ");
//						                Query actorsQuery = s.createQuery("select a.actorsName from actors a join a.moviesList m where m.moviesID = :movieID", String.class);
//						                actorsQuery.setParameter("movieID", m.getMoviesID());
//						                List<String> actorList = actorsQuery.getResultList();
//						                for (String actor : actorList) {
//						                    System.out.println(actor);
//						                }
						                
						                System.out.println("");
						            }
						            
						            
						        }
						        
						        trans = s.beginTransaction();
						        movieRepository mr = new movieRepository();
						        mr.selectByTitle(selectedTitle);
						        trans.commit();
						    } catch (Exception e) {
						        e.printStackTrace();
						    } finally {
						        s.close();
						    }
						    break;
							
						case 4:
							m = new movies();
							s = hibernateUtil.getSessionFactory().openSession();
							try {
								trans = s.beginTransaction();
								
								movieRepository mr = new movieRepository();
								List<movies> list = mr.selectAll();
								if (!list.isEmpty()) {
									System.out.println("All movies");
									for (movies movies : list) {
										System.out.println("Movie id: " +movies.getMoviesID());
										System.out.println("Movie title: " +movies.getMoviesTitle());
										System.out.println("Movie year of release: " +movies.getYearOfReleast());
										System.out.println("-----------------");
									}
								}else {
									System.out.println("The movie not found");
								}
								trans.commit();
								
							} catch (Exception e) {
								s.close();
								e.printStackTrace();
							}
							break;
							
						case 5:
							s = hibernateUtil.getSessionFactory().openSession();
						    trans = null;
						    try {
						        Query query = s.createQuery("select m.moviesID, m.moviesTitle, m.yearOfReleast from movies m", Object[].class);
						        List<Object[]> moviesInfo = query.getResultList();
						        
						        System.out.println("Select a movie title to see the information: ");
						        String selectedTitle = sc.nextLine();
						        
						        for (Object[] movie : moviesInfo) {
						            Long movieID = (Long) movie[0];
						            movieTitle = (String) movie[1];
						            yearOfReleast = (Integer) movie[2];
						            
						            if (selectedTitle.equalsIgnoreCase(movieTitle)) {
						                m = new movies();
						                m.setMoviesID(movieID);
						                m.setMoviesTitle(movieTitle);
						                m.setYearOfReleast(yearOfReleast);
						                
						                System.out.println("=======================");
						                System.out.println("Movie ID: " + m.getMoviesID());
						                System.out.println("Movie Title: " + m.getMoviesTitle());
						                System.out.println("Year of Release: " + m.getYearOfReleast());
						                System.out.println("Genres: ");
						                m = new movies();
						                Query genresQuery = s.createQuery("select g.genresName from genres g join g.moviesList m where m.moviesID = :movieID", String.class);
						                genresQuery.setParameter("movieID", m.getMoviesID());
						                List<String> genreList = genresQuery.getResultList();
							            for (String genres	 : genreList) {
											System.out.println(genres);
										}
							            
						                System.out.println("Actors: ");
						                Query actorsQuery = s.createQuery("select a.actorsName from actors a join a.moviesList m where m.moviesID = :movieID", String.class);
						                actorsQuery.setParameter("movieID", m.getMoviesID());
						                List<String> actorList = actorsQuery.getResultList();
						                for (String actor : actorList) {
						                    System.out.println(actor);
						                }
						                
						                System.out.println("");
						            }
						        }
						        
						        trans = s.beginTransaction();
						        movieRepository mr = new movieRepository();
						        mr.selectByTitle(selectedTitle);
						        trans.commit();
						    } catch (Exception e) {
						        e.printStackTrace();
						    } finally {
						        s.close();
						    }
							break;
		
						default:
							break;
					}
					
					break;
					
					
				case 2:
					genreRespository gr = new genreRespository();
					
					System.out.println("1. Adding genre records to database \r\n"
									 + "2. Deleteing genre records from database \r\n"
									 + "3. Searching genre record by name \r\n"
									 + "4. Searching genre record by ID \r\n"
									 + "5. Return all records \r\n");
					choice = sc.nextInt();
					sc.nextLine();
					
					switch (choice) {
						case 1:
							
							break;
						case 2:
											
							break;
						case 3:
							
							break;
						case 4:
							
							break;
		
						default:
							break;
					}
					
					break;
	
				case 3:
					System.out.println("1. Adding object of type Actor to database \r\n"
									 + "2. Searching object of type actor by ID \r\n"
									 + "3. Seaching object of type actor by year condition \r\n"
									 + "4. Seaching object of type actor by name condition \r\n");
					choice = sc.nextInt();
					sc.nextLine();
					

					switch (choice) {
						case 1:
							
							break;
							
						case 2:
							
							break;
							
						case 3:
							
							break;
							
						case 4:
							
							break;
							
						case 5:
							
							break;
						default:
							break;
					}
					break;
					
				
				default:
					break;
			}
			
		} while (choice != 0);
	}
}

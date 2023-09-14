package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.movies;
import util.hibernateUtil;

public class movieRepository {

	// add new movie method
	public movies insert(movies t) {
		SessionFactory sf = hibernateUtil.getSessionFactory();
		if (sf != null) {
			try(Session s = sf.openSession()) {
				Transaction trans = s.beginTransaction();
				
				s.saveOrUpdate(t);
				
				trans.commit();
				
			} catch (Exception e) {
				sf.close();
				e.printStackTrace();
			}
		}
		return null;
	}

	// delete movie method
	public void delete(Long moviesID) {
		SessionFactory sf = hibernateUtil.getSessionFactory();
		if (sf != null) {
			try(Session s = sf.openSession()) {
				Transaction trans = s.beginTransaction();
				
				movies moviesToDelete = s.get(movies.class, moviesID);
				if (moviesToDelete != null) {
					s.delete(moviesToDelete);
					trans.commit();
				}
			}catch (Exception e) {
				sf.close();
				e.printStackTrace();
			}
		}
	}

	// get movie by title method
	public movies selectByTitle(String moviesTitle) {
		SessionFactory sf = hibernateUtil.getSessionFactory();
		if (sf != null) {
			try(Session s = sf.openSession();){
				Transaction trans = s.beginTransaction();
				
				String hql = "from movies m where m.moviesTitle =: moviesTitle";
				Query q = s.createQuery(hql);
				q.setParameter("moviesTitle", moviesTitle);
				List<movies> list = q.getResultList();
				
				trans.commit();
				if (!list.isEmpty()) {
					return list.get(0);
				}
			}catch(Exception e) {
				sf.close();
				e.printStackTrace();
			}
		}
		return null;
	}
	
	// select all movies method
	public List<movies> selectAll() {
		List<movies> list = new ArrayList<>();
		SessionFactory sf = hibernateUtil.getSessionFactory();
		if (sf != null) {
			try(Session s = sf.openSession()) {
				Transaction trans = s.beginTransaction();
				
				String hql = "from movies";
				Query q = s.createQuery(hql);
				list = q.getResultList();
				
				trans.commit();
			} catch (Exception e) {
				sf.close();
				e.printStackTrace();
			}
		}
		return list;
	}

}

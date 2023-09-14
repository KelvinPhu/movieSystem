package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.genres;
import util.hibernateUtil;

public class genreRespository{

	public genres insert(genres g) {
		SessionFactory sf = hibernateUtil.getSessionFactory();
		if (sf != null) {
			try(Session s = sf.openSession()) {
				Transaction trans = s.beginTransaction();
				s.saveOrUpdate(g);
				
				trans.commit();
			} catch (Exception e) {
				sf.close();
				e.printStackTrace();
			}
		}
		return null;
	}

	public genres delete(genres t) {
		SessionFactory sf = hibernateUtil.getSessionFactory();
		if (sf != null) {
			try(Session s = sf.openSession()) {
				Transaction trans = s.beginTransaction();
				
				s.delete(t);
				
				trans.commit();
				
			} catch (Exception e) {
				sf.close();
				e.printStackTrace();
			}
		}
		return null;
	}

	public genres selectByName(genres t) {
		List<genres> list = new ArrayList<>();
		SessionFactory sf = hibernateUtil.getSessionFactory();
		if (sf != null) {
			try(Session s = sf.openSession()) {
				Transaction trans = s.beginTransaction();
				
				String hql = "from genres g where g.genresName =: genresName";
				Query q = s.createQuery(hql);
				q.setParameter("genresName", t.getGenresName());
				list = q.getResultList();
				
				trans.commit();
				
				if (!list.isEmpty()) {
					return list.get(0);
				}
				
			} catch (Exception e) {
				sf.close();
				e.printStackTrace();
			}
		}
		return null;
	}

	public genres selectByID(genres t) {
		List<genres> list = new ArrayList<>();
		SessionFactory sf = hibernateUtil.getSessionFactory();
		if (sf != null) {
			try(Session s = sf.openSession()) {
				Transaction trans = s.beginTransaction();
				
				String hql = "from genres g where g.genresID =: genresID";
				Query q = s.createQuery(hql);
				q.setParameter("genresID", t.getGenresID());
				list = q.getResultList();
				
				trans.commit();
				
				if (!list.isEmpty()) {
					return list.get(0);
				}
			} catch (Exception e) {
				sf.close();
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<genres> selectAll() {
		List<genres> list = new ArrayList<>();
		SessionFactory sf = hibernateUtil.getSessionFactory();
		if (sf != null) {
			try(Session s = sf.openSession()) {
				Transaction trans = s.beginTransaction();
				
				String hql = "from genres";
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

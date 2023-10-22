package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.actors;
import util.hibernateUtil;

public class actorRepository{

	public actors insert(actors a) {
		SessionFactory sf = hibernateUtil.getSessionFactory();
		if (sf != null) {
			try(Session s = sf.openSession()) {
				Transaction trans = s.beginTransaction();

				// this is a transaction
				s.saveOrUpdate(a);
				trans.commit();
				// transaction done
				
			} catch (Exception e) {
				sf.close();
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public actors selectByID(actors t) {
		List<actors> list = new ArrayList<>();
		SessionFactory sf = hibernateUtil.getSessionFactory();
		if (sf != null) {
			try(Session s = sf.openSession()) {
				Transaction trans = s.beginTransaction();
				
				String hql = "from actors a where a.actorsID =: actorsID";
				Query q = s.createQuery(hql);
				q.setParameter("actorsID", t.getActorsID());
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

	public ArrayList<actors> selectByCondition(String condition) {
		ArrayList<actors> list = new ArrayList<>();
		SessionFactory sf = hibernateUtil.getSessionFactory();
		if (sf != null) {
			try(Session s = sf.openSession()) {
				Transaction trans = s.beginTransaction();
				
				String hql = "from actor a where a.actorsName =: condition";
				Query q = s.createQuery(hql);
				q.setParameter("condition", condition);
				list = (ArrayList<actors>) q.getResultList();
				
				trans.commit();
			} catch (Exception e) {
				sf.close();
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public ArrayList<actors> selectByYearCondition(int condition) {
		ArrayList<actors> list = new ArrayList<>();
		SessionFactory sf = hibernateUtil.getSessionFactory();
		if (sf != null) {
			try(Session s = sf.openSession()) {
				Transaction trans = s.beginTransaction();
				
				String hql = "from actor a where a.yearOfBirth =: condition";
				Query q = s.createQuery(hql);
				q.setParameter("condition", condition);
				list = (ArrayList<actors>) q.getResultList();
				
				trans.commit();
			} catch (Exception e) {
				sf.close();
				e.printStackTrace();
			}
		}
		return list;
	}
}

package pl.pwn.reaktor.ankieta.service;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pl.pwn.reaktor.ankieta.model.Ankieta;
import pl.pwn.reaktor.ankieta.model.AnkietaFilter;
import pl.pwn.reaktor.ankieta.utils.HibernateUtils;

import java.util.List;

public class AnkietaService {


    public long save(Ankieta ankieta) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        long id = (Long) session.save(ankieta);

        transaction.commit();
        session.close();
        return  id;
    }


    public List<Ankieta> getAllAnkieta(){
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<Ankieta> ankiety= session.createQuery("SELECT a FROM Ankieta a").list();
        //sql
//        List<Ankieta> ankiety= session.createSQLQuery("SELECT * FROM ankieta").list();

        transaction.commit();
        session.close();
        return ankiety;
    }

    public Ankieta findById(long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Ankieta ankieta = (Ankieta) session.get(Ankieta.class, id);

        transaction.commit();
        session.close();
        return ankieta;
    }

    public void delete(long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("DELETE FROM Ankieta WHERE id=:id");
        query.setLong("id",id);
        query.executeUpdate();

        transaction.commit();
        session.close();
    }

    public void update(Ankieta ankieta){
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.update(ankieta);

        transaction.commit();
        session.close();
    }

    public String getLastEmail(){
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT a.mail FROM Ankieta a ORDER BY a.id DESC");
        query.setMaxResults(1);
        String email = (String) query.uniqueResult();

        transaction.commit();
        session.close();
        return email;
    }

    public String getNameAndLastName(String mail) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT a FROM Ankieta a WHERE a.mail=:mail");
        query.setString("mail", mail);
        query.setMaxResults(1);

        Ankieta ankieta = (Ankieta) query.uniqueResult();

        transaction.commit();
        session.close();

        return ankieta.getName() + " " + ankieta.getLastName();
    }


    public List<Ankieta> filter(AnkietaFilter filter) {

        Session session = HibernateUtils.getSessionFactory().openSession();

        Criteria criteria = session.createCriteria(Ankieta.class);

        if (filter.getMail()!= null && !"".equals(filter.getMail())) {
            criteria.add(Restrictions.eq("mail", filter.getMail()));
        }
        if (filter.getJava()!= null && !"Select".equals(filter.getJava())) {
            criteria.add(Restrictions.eq("java", "Yes".equals(filter.getJava())));
        }
        if (filter.getLanguage()!= null && !"Select".equals(filter.getLanguage())
            && !"".equals(filter.getLanguage())) {
            criteria.add(Restrictions.eq("language", filter.getLanguage()));
        }
        List<Ankieta> ankietas = criteria.list();

        session.close();
        return ankietas;
    }

}

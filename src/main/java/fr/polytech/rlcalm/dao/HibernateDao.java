package fr.polytech.rlcalm.dao;

import fr.polytech.rlcalm.initializer.ControllerInitializer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Table;
import java.math.BigInteger;

public abstract class HibernateDao<T> {

    protected Session hibernateSession;
    private String tableName;

    public HibernateDao(Session hibernateSession, Class<T> clazz) {
        this.hibernateSession = hibernateSession;
        Configuration config = ControllerInitializer.getConfig();
        if (config == null) {
            throw new RuntimeException("Creation of an hibernateDao without a MYSQL configuration");
        }
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        if (tableAnnotation == null || tableAnnotation.name().isEmpty()) {
            tableName = clazz.getSimpleName().toLowerCase();
        } else {
            tableName = tableAnnotation.name();
        }
    }

    public boolean isEmpty() {
        return ((BigInteger) hibernateSession.createSQLQuery("SELECT EXISTS (SELECT NULL FROM " + tableName + ")").uniqueResult()).intValue() == 0;
    }

    public void save(T toSave) {
        Transaction transaction = hibernateSession.beginTransaction();
        hibernateSession.save(toSave);
        transaction.commit();
    }

    public void remove(T toRemove) {
        Transaction transaction = hibernateSession.beginTransaction();
        hibernateSession.delete(toRemove);
        transaction.commit();
    }
}

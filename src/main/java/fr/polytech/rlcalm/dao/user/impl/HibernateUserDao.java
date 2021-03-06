package fr.polytech.rlcalm.dao.user.impl;

import fr.polytech.rlcalm.beans.User;
import fr.polytech.rlcalm.dao.HibernateDao;
import fr.polytech.rlcalm.dao.user.UserDao;
import org.hibernate.Session;

import java.util.Optional;

public class HibernateUserDao extends HibernateDao<User> implements UserDao {

    public HibernateUserDao(Session hibernateSession) {
        super(hibernateSession, User.class);
    }

    @Override
    public Optional<User> getUserByName(String username) {
        return hibernateSession.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst();
    }

}

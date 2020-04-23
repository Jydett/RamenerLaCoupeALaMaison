package fr.polytech.rlcalm.dao.match.impl;

import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.dao.HibernateDao;
import fr.polytech.rlcalm.dao.match.MatchDao;
import org.hibernate.Session;

import java.util.Collection;
import java.util.Optional;

public class HibernateMatchDao extends HibernateDao<Match> implements MatchDao {

    public HibernateMatchDao(Session hibernateSession) {
        super(hibernateSession, Match.class);
    }

    @Override
    public Optional<Match> findById(Long matchId) {
        return Optional.ofNullable(hibernateSession.get(Match.class, matchId));
    }

    @Override
    public Collection<Match> getAll() {
        return hibernateSession.createQuery("select m from Match m", Match.class).getResultList();
    }
}

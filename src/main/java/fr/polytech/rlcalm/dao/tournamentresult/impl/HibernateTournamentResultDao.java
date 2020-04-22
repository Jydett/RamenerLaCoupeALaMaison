package fr.polytech.rlcalm.dao.tournamentresult.impl;

import fr.polytech.rlcalm.beans.TournamentResult;
import fr.polytech.rlcalm.dao.HibernateDao;
import fr.polytech.rlcalm.dao.tournamentresult.TournamentResultDao;
import org.hibernate.Session;

import java.util.List;

public class HibernateTournamentResultDao extends HibernateDao<TournamentResult> implements TournamentResultDao {

    public HibernateTournamentResultDao(Session hibernateSession) {
        super(hibernateSession, TournamentResult.class);
    }

    @Override
    public List<TournamentResult> getByYear(Integer year) {
        return hibernateSession.createQuery("select t From TournamentResult t where t.year = :year order by t.placement", TournamentResult.class)
                .setParameter("year", year)
                .getResultList();
    }
}

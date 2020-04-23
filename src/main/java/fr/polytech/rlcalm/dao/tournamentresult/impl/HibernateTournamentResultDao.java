package fr.polytech.rlcalm.dao.tournamentresult.impl;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.beans.TournamentResult;
import fr.polytech.rlcalm.dao.HibernateDao;
import fr.polytech.rlcalm.dao.tournamentresult.TournamentResultDao;
import org.hibernate.Session;

import java.util.Collection;
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

    @Override
    public Collection<TournamentResult> getAll() {
        return hibernateSession.createQuery("select r From TournamentResult r order by r.year desc, r.placement asc", TournamentResult.class).list();
    }

    @Override
    public List<TournamentResult> getByIdBatch(List<Long> ids) {
        return hibernateSession.byMultipleIds(TournamentResult.class).multiLoad(ids);
    }

    public List<TournamentResult> getPalmaresFromClub(Club clubId) {
        return hibernateSession.createQuery("select r From TournamentResult r where r.club.id = :clubId order by r.year desc", TournamentResult.class)
                .setParameter("clubId", clubId)
                .getResultList();
    }
}

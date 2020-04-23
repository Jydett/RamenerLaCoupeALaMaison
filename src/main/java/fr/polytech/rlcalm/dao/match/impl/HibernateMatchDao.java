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

    @Override
    public int getNumberOfPlanifiedMatch(Long clubId) {
        return ((Long) hibernateSession.createQuery("select count(*) from Match m where m.result is null and (m.player1.id = :clubId or m.player2.id = :clubId)")
                .setParameter("clubId", clubId)
                .uniqueResult())
                .intValue();
    }

    @Override
    public int getNumberOfDisputedMatch(Long clubId) {
        return ((Long) hibernateSession.createQuery("select count(*) from Match m where m.result is not null and (m.player1.id = :clubId or m.player2.id = :clubId)")
                .setParameter("clubId", clubId)
                .uniqueResult())
                .intValue();
    }

    @Override
    public int getNumberOfVictory(Long clubId) {
        return ((Long) hibernateSession.createQuery("select count(*) from Match m WHERE m.result is not null AND ((m.player1.id = :clubId AND m.result.score1 > m.result.score2) OR (m.player2.id = :clubId AND m.result.score2 > m.result.score1))")
                .setParameter("clubId", clubId)
                .uniqueResult())
                .intValue();
    }
}

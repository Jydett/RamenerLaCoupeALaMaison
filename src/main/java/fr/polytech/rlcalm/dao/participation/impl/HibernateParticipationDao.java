package fr.polytech.rlcalm.dao.participation.impl;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.beans.Participation;
import fr.polytech.rlcalm.beans.Player;
import fr.polytech.rlcalm.dao.HibernateDao;
import fr.polytech.rlcalm.dao.participation.ParticipationDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateParticipationDao extends HibernateDao<Participation> implements ParticipationDao {

    public HibernateParticipationDao(Session hibernateSession) {
        super(hibernateSession, Participation.class);
    }

    @Override
    public List<Participation> getParticipationsOfClubOnMatch(Club club, Match match) {
        return hibernateSession.createQuery("select p from Participation p where p.match.id = :matchId and p.player.club.id = :clubId", Participation.class)
                .setParameter("matchId", match.getId())
                .setParameter("clubId", club.getId())
                .getResultList();
    }

    @Override
    public List<Participation> getParticipationsOnMatch(Match match) {
        return hibernateSession.createQuery("select p from Participation p where p.match.id = :matchId", Participation.class)
                .setParameter("matchId", match.getId())
                .getResultList();
    }

    @Override
    public void removeParticipationsOnMatch(Match match) {
        Transaction transaction = hibernateSession.beginTransaction();
        hibernateSession.createQuery("delete from Participation p where p.match.id = :matchId")
                .setParameter("matchId", match.getId()).executeUpdate();
        transaction.commit();
    }

    @Override
    public void removeParticipationsOfClubOnMatch(Club club, Match match) {
        Transaction transaction = hibernateSession.beginTransaction();
        hibernateSession.createQuery("delete from Participation p where p.match.id = :matchId and p.player.club.id = :clubId")
                .setParameter("matchId", match.getId())
                .setParameter("clubId", club.getId())
                .executeUpdate();
        transaction.commit();
    }

    @Override
    public List<Participation> getParticipationOfPlayer(Player player) {
        return hibernateSession.createQuery("select p from Participation p where p.player.id = :playerId", Participation.class)
                .setParameter("playerId", player.getId()).getResultList();
    }
}

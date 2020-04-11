package fr.polytech.rlcalm.dao.participation.impl;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.beans.Participation;
import fr.polytech.rlcalm.beans.Player;
import fr.polytech.rlcalm.dao.HibernateDao;
import fr.polytech.rlcalm.dao.match.MatchDao;
import fr.polytech.rlcalm.dao.participation.ParticipationDao;
import org.hibernate.Session;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class HibernateParticipationDao extends HibernateDao<Participation> implements ParticipationDao {

    public HibernateParticipationDao(Session hibernateSession) {
        super(hibernateSession, Participation.class);
    }

    //TODO left join pour avoir des valeurs à 0 sur les joueru qui n'ont pas marqué
    @Override
    public List<Participation> getParticipationsOfClubOnMatch(Club club, Match match) {
        return hibernateSession.createQuery("select p from Participation p where p.match.id = :matchId and p.player.club.id = :clubId", Participation.class)
                .setParameter("matchId", match.getId())
                .setParameter("clubId", club.getId())
                .getResultList();
    }

    @Override
    public List<Participation> getParticipationsOnMatch(Match match) {
        throw new UnsupportedOperationException();//TODO toimplement
    }

    @Override
    public void removeParticipationsOnMatch(Match match) {
        throw new UnsupportedOperationException();//TODO toimplement
    }

    @Override
    public void removeParticipationsOfClubOnMatch(Club club, Match match) {
        throw new UnsupportedOperationException();//TODO toimplement
    }

    @Override
    public List<Participation> getParticipationOfPlayer(Player player) {
        throw new UnsupportedOperationException();//TODO toimplement
    }
}

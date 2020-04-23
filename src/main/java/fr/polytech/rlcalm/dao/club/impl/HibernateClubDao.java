package fr.polytech.rlcalm.dao.club.impl;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.dao.HibernateDao;
import fr.polytech.rlcalm.dao.club.ClubDao;
import org.hibernate.Session;

import java.util.Optional;

public class HibernateClubDao extends HibernateDao<Club> implements ClubDao {
    public HibernateClubDao(Session hibernateSession) {
        super(hibernateSession, Club.class);
    }

    @Override
    public int getNumberOfMatch(Long clubId) {
        return ((Long) hibernateSession.createQuery("select count(*) from Match m where m.player1.id = :clubId or m.player2.id = :clubId ")
                    .setParameter("clubId", clubId)
                    .uniqueResult())
                .intValue();
    }

    @Override
    public int getNumberOfVictory(Long clubId) {
        return ((Long) hibernateSession.createQuery("select count(*) from Match m WHERE m.result is not null AND (m.player1.id = :clubId AND m.result.score1 > m.result.score2) OR (m.player2.id = :clubId AND m.result.score2 > m.result.score1)")
                    .setParameter("clubId", clubId)
                    .uniqueResult())
                .intValue();
    }

    @Override
    public Optional<Club> findById(Long clubId){
        return Optional.ofNullable(hibernateSession.get(Club.class, clubId));
    }

}

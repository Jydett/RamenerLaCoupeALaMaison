package fr.polytech.rlcalm.dao.club.impl;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.dao.HibernateDao;
import fr.polytech.rlcalm.dao.club.ClubDao;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class HibernateClubDao extends HibernateDao<Club> implements ClubDao {
    public HibernateClubDao(Session hibernateSession) {
        super(hibernateSession, Club.class);
    }

    @Override
    public Optional<Club> findById(Long clubId){
        return Optional.ofNullable(hibernateSession.get(Club.class, clubId));
    }

    @Override
    public List<Club> getByIdBatch(List<Long> ids) {
        return hibernateSession.byMultipleIds(Club.class).multiLoad(ids);
    }

}

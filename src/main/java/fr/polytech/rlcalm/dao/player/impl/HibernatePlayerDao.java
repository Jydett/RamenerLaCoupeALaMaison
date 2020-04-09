package fr.polytech.rlcalm.dao.player.impl;

import fr.polytech.rlcalm.beans.Player;
import fr.polytech.rlcalm.dao.HibernateDao;
import fr.polytech.rlcalm.dao.player.PlayerDao;
import org.hibernate.Session;

import java.util.Optional;

public class HibernatePlayerDao extends HibernateDao<Player> implements PlayerDao {

    public HibernatePlayerDao(Session hibernateSession) {
        super(hibernateSession, Player.class);
    }

    @Override
    public Optional<Player> findById(Long playerId) {
        return Optional.ofNullable(hibernateSession.get(Player.class, playerId));
    }
}

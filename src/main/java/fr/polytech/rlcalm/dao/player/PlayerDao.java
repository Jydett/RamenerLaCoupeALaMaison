package fr.polytech.rlcalm.dao.player;

import fr.polytech.rlcalm.beans.Player;

import java.util.Optional;

public interface PlayerDao {
    Optional<Player> findById(Long playerId);

    boolean isEmpty();

    void save(Player p);

    void remove(Player p);
}

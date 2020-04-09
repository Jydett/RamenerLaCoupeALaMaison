package fr.polytech.rlcalm.dao.club;

import fr.polytech.rlcalm.beans.Club;

import java.util.Collection;
import java.util.Optional;

public interface ClubDao {
    boolean isEmpty();
    void save(Club m);
    Collection<Club> getAll();
    Optional<Club> findById(Long matchId);
}

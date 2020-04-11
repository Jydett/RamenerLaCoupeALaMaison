package fr.polytech.rlcalm.dao.match;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.beans.Participation;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MatchDao {
    Optional<Match> findById(Long matchId);
    boolean isEmpty();
    void save(Match m);
    Collection<Match> getAll();

    void remove(Match match);
}

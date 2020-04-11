package fr.polytech.rlcalm.dao.participation;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.beans.Participation;
import fr.polytech.rlcalm.beans.Player;

import java.util.List;

public interface ParticipationDao {
    List<Participation> getParticipationsOfClubOnMatch(Club club, Match match);

    boolean isEmpty();

    void save(Participation participation);

    List<Participation> getParticipationsOnMatch(Match match);

    void removeParticipationsOnMatch(Match match);

    void removeParticipationsOfClubOnMatch(Club club, Match match);

    List<Participation> getParticipationOfPlayer(Player player);

    void remove(Participation participation);
}

package fr.polytech.rlcalm.dao.tournamentresult;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.beans.TournamentResult;

import java.util.Collection;
import java.util.List;

public interface TournamentResultDao {

    List<TournamentResult> getByYear(Integer year);

    void save(TournamentResult tournamentResult);

    boolean isEmpty();

    Collection<TournamentResult> getAll();

    List<TournamentResult> getByIdBatch(List<Long> ids);

    List<TournamentResult> getPalmaresFromClub(Club clubId);

    void deleteByYear(Integer year);
}

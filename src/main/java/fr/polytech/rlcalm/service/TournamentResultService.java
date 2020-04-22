package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.TournamentResult;
import fr.polytech.rlcalm.dao.tournamentresult.TournamentResultDao;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class TournamentResultService {

    private TournamentResultDao tournamentResultDao;


    public List<TournamentResult> getByYear(Integer year) {
        return tournamentResultDao.getByYear(year);
    }

    public Collection<TournamentResult> getAll() {
        return tournamentResultDao.getAll();
    }
}

package fr.polytech.rlcalm.dao.tournamentresult.impl;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.beans.TournamentResult;
import fr.polytech.rlcalm.dao.HashMapDao;
import fr.polytech.rlcalm.dao.tournamentresult.TournamentResultDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class HashMapTournamentResultDao extends HashMapDao<Long, TournamentResult> implements TournamentResultDao {
    private final static AtomicLong id = new AtomicLong(0);

    public HashMapTournamentResultDao() {
        super(id::incrementAndGet);
    }

    @Override
    public List<TournamentResult> getByYear(Integer year) {
        return table.values().stream().filter(t -> year.equals(t.getYear())).collect(Collectors.toList());
    }

    @Override
    public List<TournamentResult> getByIdBatch(List<Long> ids) {
        List<TournamentResult> res = new ArrayList<>();
        for (Long id : ids) {
            TournamentResult tr = table.get(id);
            if (tr != null) {
                res.add(tr);
            }
        }
        return res;
    }

    @Override
    public List<TournamentResult> getPalmaresFromClub(Club club) {
        Long id = club.getId();
        return table.values().stream().filter(t -> id.equals(t.getClub().getId())).collect(Collectors.toList());
    }

    @Override
    public void deleteByYear(Integer year) {
        List<Long> toRemove = table.values()
                .stream()
                .filter(t -> year.equals(t.getYear()))
                .map(TournamentResult::getId)
                .collect(Collectors.toList());
        toRemove.forEach(table::remove);
    }
}

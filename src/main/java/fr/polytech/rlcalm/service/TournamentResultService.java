package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.TournamentResult;
import fr.polytech.rlcalm.dao.tournamentresult.TournamentResultDao;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.utils.FormUtils;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TournamentResultService {

    private TournamentResultDao tournamentResultDao;

    public List<TournamentResult> getByYear(Integer year) {
        return tournamentResultDao.getByYear(year);
    }

    public Collection<TournamentResult> getAll() {
        return tournamentResultDao.getAll();
    }

    public void updateOrder(String[] orders) {
        List<Long> ids = Arrays.stream(orders)
                .map(p -> FormUtils.getRequiredLong(p, "L'id d'une des participations n'est pas valide !")).collect(Collectors.toList());
        Map<Long, TournamentResult> resultById = tournamentResultDao.getByIdBatch(ids).stream().collect(Collectors.toMap(TournamentResult::getId, Function.identity()));
        if (resultById.size() != ids.size()) {
            throw new ServiceException("Mauvaise taille !");
        }
        Integer yearCheck = null;
        for (int i = 0; i < ids.size(); i++) {
            TournamentResult tournamentResult = resultById.get(ids.get(i));
            if (tournamentResult == null) {
                throw new ServiceException("Id inconnu");
            }
            if (yearCheck != null) {
                if (! yearCheck.equals(tournamentResult.getYear())) {
                    throw new ServiceException("Les matches appartiennent à des éditions différentes !");
                }
            }
            yearCheck = tournamentResult.getYear();
            tournamentResult.setPlacement(i + 1);
        }
        for (TournamentResult value : resultById.values()) {
            tournamentResultDao.save(value);
        }
    }

    public void deleteByYear(Integer year) {
        tournamentResultDao.deleteByYear(year);
    }
}

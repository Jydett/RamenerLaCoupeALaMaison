package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.beans.TournamentResult;
import fr.polytech.rlcalm.dao.club.ClubDao;
import fr.polytech.rlcalm.dao.tournamentresult.TournamentResultDao;
import fr.polytech.rlcalm.exception.InvalidFormException;
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
    private ClubDao clubDao;

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

    public List<TournamentResult> createEmptyResultForYear(Integer year) {
        return clubDao
            .getAll()
            .stream()
            .map(c -> new TournamentResult(c, year, -1))
            .collect(Collectors.toList());
    }

    public void saveCreation(String yearStr, String[] clubsStr) {
        Integer year = FormUtils.getRequiredInt(yearStr, "L'année n'est pas valide");
        List<Long> clubIds = Arrays.stream(clubsStr)
            .map(id -> FormUtils.getRequiredLong(id, "Un des clubs n'est pas valide"))
            .collect(Collectors.toList());
        List<Club> clubs = clubDao.getByIdBatch(clubIds);
        if (clubs.size() != clubIds.size()) {
            throw new InvalidFormException("Un des clubs est introuvable");
        }
        for (int i = 0; i < clubs.size(); i++) {
            tournamentResultDao.save(new TournamentResult(clubs.get(i), year, i + 1));
        }
    }
}

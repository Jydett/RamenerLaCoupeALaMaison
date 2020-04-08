package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.beans.MatchResult;
import fr.polytech.rlcalm.dao.club.ClubDao;
import fr.polytech.rlcalm.dao.match.MatchDao;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.form.MatchUpdateForm;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class MatchService {
    public MatchDao dao;
    public ClubDao clubDao;

    public Match getMatch(Long id) {
        return dao.findById(id).orElse(null);
    }

    //TODO chercher par annee
    public Collection<Match> getAllMatches() {
        return dao.getAll();
    }

    public void delete(Match match) {
        dao.remove(match);
    }

    public void update(Match match, MatchUpdateForm form) {
        match.setCity(form.getCity());
        match.setStadium(form.getStadium());

        match.setPlayer1(clubDao.findById(form.getPlayerId1()).orElseThrow(() -> new ServiceException()));
        match.setPlayer2(clubDao.findById(form.getPlayerId2()).orElseThrow(() -> new ServiceException()));

        //TODO verif date
        match.setInstant(form.getInstant());
        Integer score1 = form.getScore1();
        Integer score2 = form.getScore2();
        if (score1 == null || score2 == null) {
            if (score1 != null || score2 != null) {
                throw new ServiceException();
            }
            //score1 == null && score2 == null
        } else {
            //score1 != null && score2 != null
            MatchResult result = match.getResult();
            if (result == null) {
                result = new MatchResult(score1, score2);
                match.setResult(result);
            } else {
                result.setScore1(score1);
                result.setScore2(score2);
            }
        }

        dao.save(match);
    }
}

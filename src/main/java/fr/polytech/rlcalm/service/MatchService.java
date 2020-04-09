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

    public void update(MatchUpdateForm form) {
        Match match;
        if (form.getId() == null) {
            match = new Match();
        } else {
            match = dao.findById(form.getId()).orElseThrow(() -> new ServiceException("Impossible de trouver ce match !"));
        }

        match.setCity(form.getCity());
        match.setStadium(form.getStadium());

        if (form.getPlayerId1().equals(form.getPlayerId2())) {
            throw new ServiceException("Une equipe ne peux pas jouer contre elle même !");
        }
        match.setPlayer1(clubDao.findById(form.getPlayerId1()).orElseThrow(() -> new ServiceException("Impossible de trouver la première équipe")));
        match.setPlayer2(clubDao.findById(form.getPlayerId2()).orElseThrow(() -> new ServiceException("Impossible de trouver la deuxième équipe")));

        //TODO verif date
        match.setInstant(form.getInstant());
        Integer score1 = form.getScore1();
        Integer score2 = form.getScore2();
        if (score1 == null || score2 == null) {
            if (score1 != null || score2 != null) {
                throw new ServiceException("Un des deux score est nul !");
            }
            //score1 == null && score2 == null
            match.setResult(null);
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

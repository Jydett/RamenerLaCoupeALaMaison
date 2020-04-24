package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.*;
import fr.polytech.rlcalm.dao.club.ClubDao;
import fr.polytech.rlcalm.dao.match.MatchDao;
import fr.polytech.rlcalm.dao.participation.ParticipationDao;
import fr.polytech.rlcalm.dao.player.PlayerDao;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.form.MatchUpdateForm;
import fr.polytech.rlcalm.form.ParticipationUpdateForm;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class MatchService {
    public MatchDao dao;
    public ClubDao clubDao;
    public ParticipationDao participationDao;
    public PlayerDao playerDao;

    public Match getMatch(Long id) {
        return dao.findById(id).orElse(null);
    }

    public Collection<Match> getAllMatches() {
        return dao.getAll();
    }

    public void delete(Match match) {
        removeScoring(match);
        dao.remove(match);
    }

    public void update(MatchUpdateForm form) {
        Match match;
        boolean isCreateOperation = form.getId() == null;
        if (isCreateOperation) {
            match = new Match();
        } else {
            match = dao.findById(form.getId()).orElseThrow(() -> new ServiceException("Impossible de trouver ce match !"));
        }

        match.setCity(form.getCity());
        match.setStadium(form.getStadium());

        if (form.getPlayerId1().equals(form.getPlayerId2())) {
            throw new ServiceException("Une equipe ne peux pas jouer contre elle même !");
        }
        Club player1 = clubDao.findById(form.getPlayerId1()).orElseThrow(() -> new ServiceException("Impossible de trouver la première équipe"));
        Club player2 = clubDao.findById(form.getPlayerId2()).orElseThrow(() -> new ServiceException("Impossible de trouver la première équipe"));
        if (isCreateOperation) {
            match.setPlayer2(player2);
            match.setPlayer1(player1);
        } else {
            if (! match.getPlayer1().getId().equals(form.getPlayerId1())) {
                if (match.getResult() != null && ! match.getResult().getScore1().equals(0)) {
                    participationDao.removeParticipationsOfClubOnMatch(player1, match);
                    match.getResult().setScore1(0);
                }
                match.setPlayer1(player1);
            }
            if (! match.getPlayer2().getId().equals(form.getPlayerId2())) {
                if (match.getResult() != null && ! match.getResult().getScore2().equals(0)) {
                    participationDao.removeParticipationsOfClubOnMatch(player2, match);
                    match.getResult().setScore2(0);
                }
                match.setPlayer2(player2);
            }
        }
        match.setInstant(form.getInstant());
        dao.save(match);
    }

    public List<Participation> getParticipationsOfPlayer1OnMatch(Match match) {
        return getParticipationsOfPlayersOfClubOnMatch(match, match.getPlayer1());
    }

    private List<Participation> getParticipationsOfPlayersOfClubOnMatch(Match match, Club club) {
        return participationDao.getParticipationsOfClubOnMatch(club, match);
    }

    public List<Participation> getParticipationsOfPlayer2OnMatch(Match match) {
        return getParticipationsOfPlayersOfClubOnMatch(match, match.getPlayer2());
    }

    public void removeScoring(Match match) {
        match.setResult(null);
        participationDao.removeParticipationsOnMatch(match);
        dao.save(match);
    }

    public void updateParticipation(Match m, ParticipationUpdateForm form) {
        Map<Long, Integer> scoreByPlayers = form.getScoreByPlayers();
        Integer score1 = saveParticipationAndReturnScore(scoreByPlayers, getParticipationsOfPlayer1OnMatch(m));
        Integer score2 = saveParticipationAndReturnScore(scoreByPlayers, getParticipationsOfPlayer2OnMatch(m));

        MatchResult result = m.getResult();
        if (result == null) {
            result = new MatchResult(score1, score2);
            m.setResult(result);
        } else {
            result.setScore1(score1);
            result.setScore2(score2);
        }
        dao.save(m);
    }

    private Integer saveParticipationAndReturnScore(Map<Long, Integer> scoreByPlayers, List<Participation> participations) {
        int score = 0;
        for (Participation participation : participations) {
            Integer newScore = scoreByPlayers.get(participation.getPlayer().getId());
            participation.setGoals(newScore);
            score = score + newScore;
            participationDao.save(participation);
        }
        return score;
    }
}

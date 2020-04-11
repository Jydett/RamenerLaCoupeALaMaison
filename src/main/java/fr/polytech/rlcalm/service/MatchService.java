package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.*;
import fr.polytech.rlcalm.dao.club.ClubDao;
import fr.polytech.rlcalm.dao.match.MatchDao;
import fr.polytech.rlcalm.dao.participation.ParticipationDao;
import fr.polytech.rlcalm.dao.player.PlayerDao;
import fr.polytech.rlcalm.exception.InvalidFormException;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.form.MatchUpdateForm;
import fr.polytech.rlcalm.form.ParticipationUpdateForm;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class MatchService {
    public MatchDao dao;
    public ClubDao clubDao;
    public ParticipationDao participationDao;
    public PlayerDao playerDao;

    public Match getMatch(Long id) {
        return dao.findById(id).orElse(null);
    }

    //TODO chercher par annee
    public Collection<Match> getAllMatches() {
        return dao.getAll();
    }

    public void delete(Match match) {
        removeScoring(match);
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
        Club player1 = clubDao.findById(form.getPlayerId1()).orElseThrow(() -> new ServiceException("Impossible de trouver la première équipe"));
        Club player2 = clubDao.findById(form.getPlayerId2()).orElseThrow(() -> new ServiceException("Impossible de trouver la première équipe"));
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

        //TODO verif date
        match.setInstant(form.getInstant());
        dao.save(match);
    }

    public List<Participation> getParticipationsOfPlayer1OnMatch(Match match) {
        return getParticipationsOfPlayersOfClubOnMatch(match, match.getPlayer1());
    }

    private List<Participation> getParticipationsOfPlayersOfClubOnMatch(Match match, Club club) {
        List<Participation> res = participationDao.getParticipationsOfClubOnMatch(club, match);
        res.addAll(
                Optional.ofNullable(club.getPlayers()).orElse(new ArrayList<>()).stream()
                .filter(p -> res.stream().noneMatch(pp -> pp.getPlayer().getId().equals(p.getId())))
                .map(p -> new Participation(null, p, 0, match))
                .collect(Collectors.toList())
        );
        return res;
    }

    public List<Participation> getParticipationsOfPlayer2OnMatch(Match match) {
        return getParticipationsOfPlayersOfClubOnMatch(match, match.getPlayer2());
    }

    public void addScoring(Match match) {
        match.setResult(new MatchResult(0, 0));
        dao.save(match);
    }

    public void removeScoring(Match match) {
        match.setResult(null);
        participationDao.removeParticipationsOnMatch(match);
        dao.save(match);
    }

    public void updateParticipation(Match m, ParticipationUpdateForm form) {
        Integer score1 = 0;
        Integer score2 = 0;
        //TODO remove
        Map<Long, Integer> scoreByPlayers = form.getScoreByPlayers();
        List<Participation> participations = getParticipationsOfPlayer1OnMatch(m);
        for (Participation participation : participations) {
            Integer newScore = scoreByPlayers.get(participation.getPlayer().getId());
            participation.setGoals(newScore);
            if (newScore != 0) {
                score1 = score1 + newScore;
                participationDao.save(participation);
            } else {
                participationDao.remove(participation);
            }
        }

        participations = getParticipationsOfPlayer2OnMatch(m);
        for (Participation participation : participations) {
            Integer newScore = scoreByPlayers.get(participation.getPlayer().getId());
            participation.setGoals(newScore);
            if (newScore != 0) {
                score2 = score2 + newScore;
                participationDao.save(participation);
            } else {
                participationDao.remove(participation);
            }
        }

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
}

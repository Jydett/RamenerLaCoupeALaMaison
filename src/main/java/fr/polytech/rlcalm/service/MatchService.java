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

        if (form.getPlayerId1().equals(form.getPlayerId2())) {
            throw new ServiceException("Une equipe ne peux pas jouer contre elle même !");
        }

        Club team1 = clubDao.findById(form.getPlayerId1()).orElseThrow(() -> new ServiceException("Impossible de trouver la première équipe"));
        Club team2 = clubDao.findById(form.getPlayerId2()).orElseThrow(() -> new ServiceException("Impossible de trouver la première équipe"));

        if (isCreateOperation) {
            match.setTeam2(team2);
            match.setTeam1(team1);
        } else {
            if (! Objects.equals(match.getInstant(), form.getInstant()) && match.getResult() != null) {
                throw new ServiceException("La date d'un marche ne peut pas être modifié une fois son score affecté");
            }
            if (! match.getTeam1().getId().equals(form.getPlayerId1())) {
                if (match.getResult() != null && ! match.getResult().getScore1().equals(0)) {
                    participationDao.removeParticipationsOfClubOnMatch(team1, match);
                    match.getResult().setScore1(0);
                }
                match.setTeam1(team1);
            }
            if (! match.getTeam2().getId().equals(form.getPlayerId2())) {
                if (match.getResult() != null && ! match.getResult().getScore2().equals(0)) {
                    participationDao.removeParticipationsOfClubOnMatch(team2, match);
                    match.getResult().setScore2(0);
                }
                match.setTeam2(team2);
            }
        }

        match.setCity(form.getCity());
        match.setStadium(form.getStadium());

        match.setInstant(form.getInstant());
        dao.save(match);
    }

    public List<Participation> getParticipationsOfteam1OnMatch(Match match) {
        return getParticipationsOfPlayersOfClubOnMatch(match, match.getTeam1());
    }

    private List<Participation> getParticipationsOfPlayersOfClubOnMatch(Match match, Club club) {
        List<Participation> participations = participationDao.getParticipationsOfClubOnMatch(club, match);
        return club.getPlayers()
            .stream()
            .map(p ->
                participations
                    .stream()
                    .filter(par -> par.getPlayer().getId().equals(p.getId()))
                    .findFirst()
                    .orElse(new Participation(null, p, 0, match))
            ).collect(Collectors.toList());
    }

    public List<Participation> getParticipationsOfteam2OnMatch(Match match) {
        return getParticipationsOfPlayersOfClubOnMatch(match, match.getTeam2());
    }

    public void removeScoring(Match match) {
        match.setResult(null);
        participationDao.removeParticipationsOnMatch(match);
        dao.save(match);
    }

    public void updateParticipation(Match m, ParticipationUpdateForm form) {
        if (m.getInstant() == null) {
            throw new ServiceException("Un score ne peux pas être affecté à un match qui n'est pas planifié");
        }
        Map<Long, Integer> scoreByPlayers = form.getScoreByPlayers();
        Integer score1 = saveParticipationAndReturnScore(scoreByPlayers, getParticipationsOfteam1OnMatch(m));
        Integer score2 = saveParticipationAndReturnScore(scoreByPlayers, getParticipationsOfteam2OnMatch(m));

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

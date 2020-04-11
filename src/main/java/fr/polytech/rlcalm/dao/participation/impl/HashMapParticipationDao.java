package fr.polytech.rlcalm.dao.participation.impl;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.beans.Participation;
import fr.polytech.rlcalm.beans.Player;
import fr.polytech.rlcalm.dao.HashMapDao;
import fr.polytech.rlcalm.dao.match.MatchDao;
import fr.polytech.rlcalm.dao.participation.ParticipationDao;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class HashMapParticipationDao extends HashMapDao<Long, Participation> implements ParticipationDao {

    private final static AtomicLong id = new AtomicLong(0);

    public HashMapParticipationDao() {
        super(id::incrementAndGet);
    }

    @Override
    public List<Participation> getParticipationsOfClubOnMatch(Club club, Match match) {
        return table.values()
                .stream()
                .filter(p -> p.getMatch().getId().equals(match.getId()) && p.getPlayer().getClub().getId().equals(club.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Participation> getParticipationsOnMatch(Match match) {
        return table.values()
                .stream()
                .filter(p -> p.getMatch().getId().equals(match.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeParticipationsOnMatch(Match match) {
        getParticipationsOnMatch(match)
                .stream()
                .map(Participation::getId)
                .collect(Collectors.toList())
                .forEach(table::remove);
    }

    @Override
    public void removeParticipationsOfClubOnMatch(Club club, Match match) {
        Long clubId = club.getId();
        getParticipationsOnMatch(match)
            .stream()
            .filter(p -> p.getPlayer().getClub().getId().equals(clubId))
            .forEach(p -> table.remove(p.getId()));
    }

    @Override
    public List<Participation> getParticipationOfPlayer(Player player) {
        return table.values().stream()
            .filter(p -> p.getPlayer().getId().equals(player.getId()))
            .collect(Collectors.toList());
    }
}

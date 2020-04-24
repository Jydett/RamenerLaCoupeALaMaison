package fr.polytech.rlcalm.dao.match.impl;

import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.dao.HashMapDao;
import fr.polytech.rlcalm.dao.match.MatchDao;

import java.util.concurrent.atomic.AtomicLong;

public class HashMapMatchDao extends HashMapDao<Long, Match> implements MatchDao {

    private final static AtomicLong id = new AtomicLong(0);

    public HashMapMatchDao() {
        super(id::incrementAndGet);
    }

    @Override
    public int getNumberOfPlanifiedMatch(Long clubId) {
        return ((Long) table.values()
            .stream()
            .filter(p -> p.getResult() == null &&
                (
                    (p.getPlayer1().getId().equals(clubId))
                        ||
                    (p.getPlayer2().getId().equals(clubId))
                ))
            .count())
            .intValue();
    }

    @Override
    public int getNumberOfDisputedMatch(Long clubId) {
        return ((Long) table.values()
            .stream()
            .filter(p -> p.getResult() != null &&
                (
                    (p.getPlayer1().getId().equals(clubId))
                        ||
                    (p.getPlayer2().getId().equals(clubId))
                ))
            .count())
            .intValue();
    }

    @Override
    public int getNumberOfVictory(Long clubId) {
        return ((Long) table.values()
            .stream()
            .filter(p -> p.getResult() != null &&
                (
                    (p.getPlayer1().getId().equals(clubId) && p.getResult().getScore1() > p.getResult().getScore2())
                        ||
                    (p.getPlayer2().getId().equals(clubId) && p.getResult().getScore2() > p.getResult().getScore1())
                ))
            .count())
            .intValue();
    }
}

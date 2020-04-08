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
}

package fr.polytech.rlcalm.dao.club.impl;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.dao.HashMapDao;
import fr.polytech.rlcalm.dao.club.ClubDao;

import java.util.concurrent.atomic.AtomicLong;

public class HashMapClubDao extends HashMapDao<Long, Club> implements ClubDao {

    private final static AtomicLong id = new AtomicLong(0);

    public HashMapClubDao() {
        super(id::incrementAndGet);
    }
}

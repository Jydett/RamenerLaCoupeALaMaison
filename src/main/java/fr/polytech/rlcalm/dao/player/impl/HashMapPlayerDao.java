package fr.polytech.rlcalm.dao.player.impl;

import fr.polytech.rlcalm.beans.Player;
import fr.polytech.rlcalm.dao.HashMapDao;
import fr.polytech.rlcalm.dao.player.PlayerDao;

import java.util.concurrent.atomic.AtomicLong;

public class HashMapPlayerDao extends HashMapDao<Long, Player> implements PlayerDao {

    private final static AtomicLong id = new AtomicLong(0);

    public HashMapPlayerDao() {
        super(id::incrementAndGet);
    }
}

package fr.polytech.rlcalm.dao.player.impl;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.beans.Player;
import fr.polytech.rlcalm.dao.HashMapDao;
import fr.polytech.rlcalm.dao.player.PlayerDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class HashMapPlayerDao extends HashMapDao<Long, Player> implements PlayerDao {

    private final static AtomicLong id = new AtomicLong(0);

    public HashMapPlayerDao() {
        super(id::incrementAndGet);
    }

    //Simulate hibernate link annotation
    @Override
    public void save(Player p) {
        Club club = p.getClub();
        if (club != null) {
            List<Player> players = club.getPlayers();
            if (players == null) {
                players = new ArrayList<>();
                club.setPlayers(players);
            }
            players.add(p);
        }
        super.save(p);
    }
}

package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.Player;
import fr.polytech.rlcalm.dao.player.PlayerDao;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerService {

    public PlayerDao dao;

    public Player getPlayer(Long id) {
        return dao.findById(id).orElse(null);
    }
}

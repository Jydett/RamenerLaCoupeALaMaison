package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.beans.Player;
import fr.polytech.rlcalm.dao.club.ClubDao;
import fr.polytech.rlcalm.dao.player.PlayerDao;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.form.PlayerUpdateForm;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerService {

    public PlayerDao dao;
    public ClubDao clubDao;

    public Player getPlayer(Long id) {
        return dao.findById(id).orElse(null);
    }

    public void update(PlayerUpdateForm form) {
        Player player;
        if (form.getId() == null) {
            player = new Player();
        } else {
            player = dao.findById(form.getId()).orElseThrow(() -> new ServiceException("Impossible de trouver ce joueur !"));
        }
        Club c = clubDao.findById(form.getClubId()).orElseThrow(() -> new ServiceException("Impossible de trouver ce club"));
        player.setClub(c);
        Integer mediaRating = form.getMediaRating();
        if (mediaRating < 0 || mediaRating > 20) {
            throw new ServiceException("La note des média ne respecte pas les bornes (elle doit être en 0 et 20)");
        }
        player.setMediaRating(mediaRating);
        player.setRole(form.getRole());
        dao.save(player);
    }

    public void delete(Player player) {
        dao.remove(player);
    }
}

package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.beans.Participation;
import fr.polytech.rlcalm.beans.Player;
import fr.polytech.rlcalm.dao.club.ClubDao;
import fr.polytech.rlcalm.dao.participation.ParticipationDao;
import fr.polytech.rlcalm.dao.player.PlayerDao;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.form.PlayerUpdateForm;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class PlayerService {

    public PlayerDao dao;
    public ClubDao clubDao;
    public ParticipationDao participationDao;

    public Player getPlayer(Long id) {
        return dao.findById(id).orElse(null);
    }

    public void update(PlayerUpdateForm form) {
        Player player;
        boolean isCreateOperation = form.getId() == null;
        if (isCreateOperation) {
            player = new Player();
        } else {
            player = dao.findById(form.getId()).orElseThrow(() -> new ServiceException("Impossible de trouver ce joueur !"));
        }
        Club c = clubDao.findById(form.getClubId()).orElseThrow(() -> new ServiceException("Impossible de trouver ce club"));

        Integer mediaRating = form.getMediaRating();
        if (mediaRating < 0 || mediaRating > 20) {
            throw new ServiceException("La note des média ne respecte pas les bornes (elle doit être en 0 et 20)");
        }

        if (! isCreateOperation && ! form.getClubId().equals(player.getClub().getId())) {
            player.getClub().removePlayer(player);
        }
        c.addPlayer(player);
        player.setMediaRating(mediaRating);
        player.setRole(form.getRole());
        player.setName(form.getName());
        dao.save(player);
    }

    public void delete(Player player) {
        player.getClub().removePlayer(player);
        dao.remove(player);
    }

    public List<Participation> getParticipationOfPlayer(Player player) {
        return participationDao.getParticipationOfPlayer(player);
    }

    public Collection<Player> getAll() {
        return dao.getAll();
    }
}

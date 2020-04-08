package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.dao.club.ClubDao;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class ClubService {
    private ClubDao clubDao;

    public Club getClub(Long id) {
        return clubDao.findById(id).orElse(null);
    }
    public Collection<Club> getAll() {
        return clubDao.getAll();
    }
}

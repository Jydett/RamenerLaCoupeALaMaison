package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.dao.club.ClubDao;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class ClubService {
    private ClubDao clubDao;

    public Collection<Club> getAll() {
        return clubDao.getAll();
    }
}

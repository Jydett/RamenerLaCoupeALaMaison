package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.beans.Country;
import fr.polytech.rlcalm.dao.club.ClubDao;
import fr.polytech.rlcalm.dao.country.CountryDao;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.form.ClubUpdateForm;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class ClubService {
    private ClubDao clubDao;
    private CountryDao countryDao;

    public Club getClub(Long id) {
        return clubDao.findById(id).orElse(null);
    }

    public Collection<Club> getAll() {
        return clubDao.getAll();
    }

    public void delete(Club club) {
        //TODO implémenter delete
    }

    public void update(ClubUpdateForm form) {
        Club club;
        boolean isCreateOperation = form.getId() == null;

        if (isCreateOperation) {
            club = new Club();
        } else {
            club = clubDao.findById(form.getId()).orElseThrow(() -> new ServiceException("Impossible de trouver ce club !"));
        }

        Country country = countryDao.findById(form.getCountryId()).orElseThrow(() -> new ServiceException("Impossible de trouver ce pays !"));
        club.setCountry(country);
        club.setName(form.getName());
        clubDao.save(club);
    }
}

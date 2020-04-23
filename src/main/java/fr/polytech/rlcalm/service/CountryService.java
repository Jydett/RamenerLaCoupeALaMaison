package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.Country;
import fr.polytech.rlcalm.dao.country.CountryDao;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class CountryService {
    private CountryDao countryDao;

    public Collection<Country> getAll() {
        return countryDao.getAll();
    }
}

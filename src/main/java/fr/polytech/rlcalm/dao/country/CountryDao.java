package fr.polytech.rlcalm.dao.country;

import fr.polytech.rlcalm.beans.Country;

import java.util.Collection;
import java.util.Optional;

public interface CountryDao {
    boolean isEmpty();
    void save(Country m);
    Optional<Country> findById(Integer countryId);
    Collection<Country> getAll();
}

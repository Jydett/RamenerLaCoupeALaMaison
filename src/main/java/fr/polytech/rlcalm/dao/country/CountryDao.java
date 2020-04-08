package fr.polytech.rlcalm.dao.country;

import fr.polytech.rlcalm.beans.Country;

public interface CountryDao {
    boolean isEmpty();
    void save(Country m);
}

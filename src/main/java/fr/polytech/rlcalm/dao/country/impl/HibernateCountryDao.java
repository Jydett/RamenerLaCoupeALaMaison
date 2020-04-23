package fr.polytech.rlcalm.dao.country.impl;

import fr.polytech.rlcalm.beans.Country;
import fr.polytech.rlcalm.dao.HibernateDao;
import fr.polytech.rlcalm.dao.country.CountryDao;
import org.hibernate.Session;

import java.util.Optional;

public class HibernateCountryDao extends HibernateDao<Country> implements CountryDao {
    public HibernateCountryDao(Session hibernateSession) {
        super(hibernateSession, Country.class);
    }

    @Override
    public Optional<Country> findById(Integer countryId) {
        return Optional.ofNullable(hibernateSession.get(Country.class, countryId));
    }
}

package fr.polytech.rlcalm.dao.country.impl;

import fr.polytech.rlcalm.beans.Country;
import fr.polytech.rlcalm.dao.HibernateDao;
import fr.polytech.rlcalm.dao.country.CountryDao;
import org.hibernate.Session;

public class HibernateCountryDao extends HibernateDao<Country> implements CountryDao {

    public HibernateCountryDao(Session hibernateSession) {
        super(hibernateSession, Country.class);
    }
}

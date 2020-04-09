package fr.polytech.rlcalm.dao.country.impl;

import fr.polytech.rlcalm.beans.Country;
import fr.polytech.rlcalm.dao.HashMapDao;
import fr.polytech.rlcalm.dao.country.CountryDao;

import java.util.concurrent.atomic.AtomicInteger;

public class HashMapCountryDao extends HashMapDao<Integer, Country> implements CountryDao {

    private final static AtomicInteger id = new AtomicInteger(0);

    public HashMapCountryDao() {
        super(id::incrementAndGet);
    }
}

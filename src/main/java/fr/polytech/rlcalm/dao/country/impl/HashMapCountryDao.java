package fr.polytech.rlcalm.dao.country.impl;

import fr.polytech.rlcalm.beans.Country;
import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.dao.HashMapDao;
import fr.polytech.rlcalm.dao.country.CountryDao;
import fr.polytech.rlcalm.dao.match.MatchDao;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class HashMapCountryDao extends HashMapDao<Integer, Country> implements CountryDao {

    private final static AtomicInteger id = new AtomicInteger(0);

    public HashMapCountryDao() {
        super(id::incrementAndGet);
    }
}

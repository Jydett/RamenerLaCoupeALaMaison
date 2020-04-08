package fr.polytech.rlcalm.initializer;

import fr.polytech.rlcalm.beans.*;
import fr.polytech.rlcalm.dao.club.ClubDao;
import fr.polytech.rlcalm.dao.club.impl.HashMapClubDao;
import fr.polytech.rlcalm.dao.country.CountryDao;
import fr.polytech.rlcalm.dao.country.impl.HashMapCountryDao;
import fr.polytech.rlcalm.dao.match.MatchDao;
import fr.polytech.rlcalm.dao.match.impl.HashMapMatchDao;
import fr.polytech.rlcalm.dao.match.impl.HibernateMatchDao;
import fr.polytech.rlcalm.dao.player.PlayerDao;
import fr.polytech.rlcalm.dao.player.impl.HashMapPlayerDao;
import fr.polytech.rlcalm.dao.player.impl.HibernatePlayerDao;
import fr.polytech.rlcalm.service.ClubService;
import fr.polytech.rlcalm.service.MatchService;
import fr.polytech.rlcalm.service.PlayerService;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.Instant;
import java.util.Optional;

@WebListener
public class ControllerInitializer implements ServletContextListener {

    @Getter private static PlayerService playerService;
    @Getter private static MatchService matchService;
    @Getter private static ClubService clubService;

    public enum DateBaseImpl {
        HASHMAP, MYSQL
    }

    private static final DateBaseImpl INITIALIZER_TYPE = DateBaseImpl.HASHMAP;

    private static Configuration configuration;

    static {
        PlayerDao playerDao = null;
        MatchDao matchDao = null;
        ClubDao clubDao = null;
        CountryDao countryDao = null;
        switch (INITIALIZER_TYPE) {
            case MYSQL: {
                configuration = new Configuration().configure();
                Session hibernateSession = configuration.buildSessionFactory().openSession();

                playerDao = new HibernatePlayerDao(hibernateSession);
                matchDao = new HibernateMatchDao(hibernateSession);
                break;
            }
            case HASHMAP: {
                matchDao = new HashMapMatchDao();
                playerDao = new HashMapPlayerDao();
                clubDao = new HashMapClubDao();
                countryDao = new HashMapCountryDao();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + INITIALIZER_TYPE);
        }
        playerService = new PlayerService(playerDao);
        matchService = new MatchService(matchDao, clubDao);
        clubService = new ClubService(clubDao);
        fillTables(playerDao, matchDao, clubDao, countryDao);
    }

    public static Configuration getConfig() {
        return configuration;
    }

    private static void fillTables(PlayerDao playerDao, MatchDao matchDao, ClubDao clubDao, CountryDao countryDao) {
        if (playerDao.isEmpty()) {
            playerDao.save(new Player(null, "Ronaldo", 0, null, Role.Central));
            playerDao.save(new Player(null, "Zizou", 3, null, Role.AttackingMidfielder));
            playerDao.save(new Player(null, "Messi", 2, null, Role.CenterBack));
            playerDao.save(new Player(null, "Mbappé", 9, null, Role.Goalkeeper));
            playerDao.save(new Player(null, "Zlatan", 6, null, Role.Striker));
            playerDao.save(new Player(null, "Naymar", 5, null, Role.LeftMidfielder));
        }
        Country fr = null, all = null, it = null, es = null, ru = null;
        if (countryDao.isEmpty()) {
            countryDao.save(fr = new Country(null, "France", "fr"));
            countryDao.save(all = new Country(null, "Allemagne", "ge"));
            countryDao.save(it = new Country(null, "Italie", "it"));

        }
        Club lldb = null;
        Club lfds = null;
        if (clubDao.isEmpty()) {
            clubDao.save(lldb = new Club(null, "Les lions de Berlin", all));
            clubDao.save(lfds = new Club(null, "Les fou du stade", fr));
            clubDao.save(new Club(null, "Russie",  ru));
            clubDao.save(new Club(null, "Espagne", es));
            clubDao.save(new Club(null, "Italie", it));
        }
        if (matchDao.isEmpty()) {
            matchDao.save(new Match(null, "Paris", "Stade de France", Instant.now(), null, lldb, lfds, null));
        }
    }
}

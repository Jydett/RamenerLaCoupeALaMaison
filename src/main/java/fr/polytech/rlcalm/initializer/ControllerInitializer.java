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
import fr.polytech.rlcalm.dao.user.UserDao;
import fr.polytech.rlcalm.dao.user.impl.HashMapUserDao;
import fr.polytech.rlcalm.dao.user.impl.HibernateUserDao;
import fr.polytech.rlcalm.service.ClubService;
import fr.polytech.rlcalm.service.MatchService;
import fr.polytech.rlcalm.service.PlayerService;
import fr.polytech.rlcalm.service.UserService;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.Instant;
import java.util.Collections;

@WebListener
public class ControllerInitializer implements ServletContextListener {

    @Getter private static PlayerService playerService;
    @Getter private static MatchService matchService;
    @Getter private static ClubService clubService;
    @Getter private static UserService userService;

    @Getter private static Configuration configuration;

    public enum DateBaseImpl {
        HASHMAP, MYSQL
    }

    private static final DateBaseImpl INITIALIZER_TYPE = DateBaseImpl.HASHMAP;

    static {
        PlayerDao playerDao = null;
        MatchDao matchDao = null;
        ClubDao clubDao = null;
        CountryDao countryDao = null;
        UserDao userDao = null;
        switch (INITIALIZER_TYPE) {
            case MYSQL: {
                configuration = new Configuration().configure();
                Session hibernateSession = configuration.buildSessionFactory().openSession();

                playerDao = new HibernatePlayerDao(hibernateSession);
                matchDao = new HibernateMatchDao(hibernateSession);
                userDao = new HibernateUserDao(hibernateSession);
                break;
            }
            case HASHMAP: {
                matchDao = new HashMapMatchDao();
                playerDao = new HashMapPlayerDao();
                clubDao = new HashMapClubDao();
                countryDao = new HashMapCountryDao();
                userDao = new HashMapUserDao();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + INITIALIZER_TYPE);
        }
        playerService = new PlayerService(playerDao, clubDao);
        matchService = new MatchService(matchDao, clubDao);
        clubService = new ClubService(clubDao);
        userService = new UserService(userDao);
        fillTables(playerDao, matchDao, clubDao, countryDao, userDao);
    }


    private static void fillTables(PlayerDao playerDao, MatchDao matchDao, ClubDao clubDao, CountryDao countryDao, UserDao userDao) {
        Country fr = null, all = null, it = null, es = null, ru = null;
        if (countryDao.isEmpty()) {
            countryDao.save(fr = new Country(null, "France", "fr"));
            countryDao.save(all = new Country(null, "Allemagne", "de"));
            countryDao.save(it = new Country(null, "Italie", "it"));
            countryDao.save(ru = new Country(null, "Russie", "ru"));
            countryDao.save(es = new Country(null, "Espagne", "es"));
        }
        Club lldb = null;
        Club lfds = null;
        if (clubDao.isEmpty()) {
            clubDao.save(lldb = new Club(null, "Les lions de Berlin", all, null));
            clubDao.save(lfds = new Club(null, "Les fou du stade", fr, null));
            clubDao.save(new Club(null, "Russie",  ru, null));
            clubDao.save(new Club(null, "Espagne", es, null));
            clubDao.save(new Club(null, "Italie", it, null));
        }
        if (playerDao.isEmpty()) {
            playerDao.save(new Player(null, "Ronaldo", 0, lldb, Role.Central));
            playerDao.save(new Player(null, "Zizou", 3, lldb, Role.AttackingMidfielder));
            playerDao.save(new Player(null, "Messi", 2, lldb, Role.CenterBack));
            playerDao.save(new Player(null, "Mbappé", 9, lfds, Role.Goalkeeper));
            playerDao.save(new Player(null, "Zlatan", 6, lfds, Role.Striker));
            playerDao.save(new Player(null, "Naymar", 5, lfds, Role.LeftMidfielder));
        }
        clubDao.save(lldb);
        clubDao.save(lfds);
        if (matchDao.isEmpty()) {
            matchDao.save(new Match(null, "Paris", "Stade de France", Instant.now(), null, lldb, lfds, null));
        }
        if (userDao.isEmpty()) {
            userDao.save(new User(null, "admin", "password"));
        }
    }
}

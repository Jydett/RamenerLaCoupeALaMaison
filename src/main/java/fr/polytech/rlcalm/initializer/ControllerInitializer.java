package fr.polytech.rlcalm.initializer;

import fr.polytech.rlcalm.beans.*;
import fr.polytech.rlcalm.dao.club.ClubDao;
import fr.polytech.rlcalm.dao.club.impl.HashMapClubDao;
import fr.polytech.rlcalm.dao.club.impl.HibernateClubDao;
import fr.polytech.rlcalm.dao.country.CountryDao;
import fr.polytech.rlcalm.dao.country.impl.HashMapCountryDao;
import fr.polytech.rlcalm.dao.country.impl.HibernateCountryDao;
import fr.polytech.rlcalm.dao.match.MatchDao;
import fr.polytech.rlcalm.dao.match.impl.HashMapMatchDao;
import fr.polytech.rlcalm.dao.match.impl.HibernateMatchDao;
import fr.polytech.rlcalm.dao.participation.ParticipationDao;
import fr.polytech.rlcalm.dao.participation.impl.HashMapParticipationDao;
import fr.polytech.rlcalm.dao.participation.impl.HibernateParticipationDao;
import fr.polytech.rlcalm.dao.player.PlayerDao;
import fr.polytech.rlcalm.dao.player.impl.HashMapPlayerDao;
import fr.polytech.rlcalm.dao.player.impl.HibernatePlayerDao;
import fr.polytech.rlcalm.dao.tournamentresult.TournamentResultDao;
import fr.polytech.rlcalm.dao.tournamentresult.impl.HibernateTournamentResultDao;
import fr.polytech.rlcalm.dao.user.UserDao;
import fr.polytech.rlcalm.dao.user.impl.HashMapUserDao;
import fr.polytech.rlcalm.dao.user.impl.HibernateUserDao;
import fr.polytech.rlcalm.service.*;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@WebListener
public class ControllerInitializer implements ServletContextListener {

    @Getter private static PlayerService playerService;
    @Getter private static MatchService matchService;
    @Getter private static ClubService clubService;
    @Getter private static UserService userService;
    @Getter private static CountryService countryService;
    @Getter private static TournamentResultService tournamentResultService;

    @Getter private static Configuration configuration;

    public enum DateBaseImpl {
        HASHMAP, MYSQL
    }

    private static final DateBaseImpl INITIALIZER_TYPE = DateBaseImpl.MYSQL;

    private static Session hibernateSession = null;

    static {
        PlayerDao playerDao = null;
        MatchDao matchDao = null;
        ClubDao clubDao = null;
        CountryDao countryDao = null;
        UserDao userDao = null;
        ParticipationDao participationDao = null;
        TournamentResultDao tournamentResultDao = null;
        switch (INITIALIZER_TYPE) {
            case MYSQL: {
                configuration = new Configuration().configure();
                hibernateSession = configuration.buildSessionFactory().openSession();
                matchDao = new HibernateMatchDao(hibernateSession);
                playerDao = new HibernatePlayerDao(hibernateSession);
                clubDao = new HibernateClubDao(hibernateSession);
                countryDao = new HibernateCountryDao(hibernateSession);
                userDao = new HibernateUserDao(hibernateSession);
                participationDao = new HibernateParticipationDao(hibernateSession);
                tournamentResultDao = new HibernateTournamentResultDao(hibernateSession);
                break;
            }
            case HASHMAP: {
                matchDao = new HashMapMatchDao();
                playerDao = new HashMapPlayerDao();
                clubDao = new HashMapClubDao();
                countryDao = new HashMapCountryDao();
                userDao = new HashMapUserDao();
                participationDao = new HashMapParticipationDao();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + INITIALIZER_TYPE);
        }
        tournamentResultService = new TournamentResultService(tournamentResultDao);
        playerService = new PlayerService(playerDao, clubDao, participationDao);
        matchService = new MatchService(matchDao, clubDao, participationDao, playerDao);
        clubService = new ClubService(clubDao, countryDao);
        userService = new UserService(userDao);
        countryService = new CountryService(countryDao);
        fillTables(playerDao, matchDao, clubDao, countryDao, userDao, participationDao, tournamentResultDao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (hibernateSession != null && hibernateSession.isOpen()) {
            hibernateSession.close();
        }
    }

    private static void fillTables(PlayerDao playerDao, MatchDao matchDao, ClubDao clubDao, CountryDao countryDao,
                                   UserDao userDao, ParticipationDao participationDao, TournamentResultDao tournamentResultDao) {
        Country fr = null, all = null, it = null, es = null, ru = null;
        if (countryDao.isEmpty()) {
            countryDao.save(fr = new Country(null, "France", "fr"));
            countryDao.save(all = new Country(null, "Allemagne", "de"));
            countryDao.save(it = new Country(null, "Italie", "it"));
            countryDao.save(ru = new Country(null, "Russie", "ru"));
            countryDao.save(es = new Country(null, "Espagne", "es"));
        }
        Club lldb = null, lfds = null, lodr = null;
        if (clubDao.isEmpty()) {
            clubDao.save(lldb = new Club("Les lions de Berlin", all));
            clubDao.save(lfds = new Club("Les fou du stade", fr));
            clubDao.save(lodr = new Club("Les ours de Russie",  ru));
            clubDao.save(new Club("Espagne", es));
            clubDao.save(new Club("Italie", it));
        }
        Player zizou = null, messi = null, bonk = null, ronaldo = null;
        if (playerDao.isEmpty()) {
            playerDao.save(ronaldo = new Player(null, "Ronaldo", 0, lldb, Role.Central));
            playerDao.save(zizou = new Player(null, "Zizou", 3, lldb, Role.AttackingMidfielder));
            playerDao.save(messi = new Player(null, "Messi", 2, lldb, Role.CenterBack));
            lldb.setPlayers(new ArrayList<>(Arrays.asList(ronaldo, zizou, messi)));
            clubDao.save(lldb);

            Player p1, p2, p3;
            playerDao.save(p1 = new Player(null, "Mbappé", 9, lfds, Role.Goalkeeper));
            playerDao.save(p2 = new Player(null, "Zlatan", 6, lfds, Role.Striker));
            playerDao.save(p3 = new Player(null, "Naymar", 5, lfds, Role.LeftMidfielder));
            lfds.setPlayers(new ArrayList<>(Arrays.asList(p1, p2, p3)));
            clubDao.save(lfds);

            playerDao.save(bonk = new Player(null, "Bолк", 5, lodr, Role.LeftMidfielder));
            lodr.setPlayers(new ArrayList<>(Collections.singletonList(bonk)));
            clubDao.save(lodr);
        }
        clubDao.save(lldb);
        clubDao.save(lfds);
        Match france_allemagne = null, france_russie = null;
        if (matchDao.isEmpty()) {
            matchDao.save(france_allemagne = new Match(null, "Paris", "Stade de France", Instant.now(), lldb, lfds, null));
            matchDao.save(france_russie = new Match(null, "Berlin", "Wurst Stadion", Instant.now(), lfds, lodr, null));
        }
        if (participationDao.isEmpty()) {
            participationDao.save(new Participation(null, zizou, 3, france_allemagne));
            france_allemagne.setResult(new MatchResult(3, 0));
            matchDao.save(france_allemagne);
        }
        if (userDao.isEmpty()) {
            userDao.save(new User(null, "admin", "password"));
        }
        if (tournamentResultDao.isEmpty()) {
            tournamentResultDao.save(new TournamentResult(lldb, 2020, 1));
            tournamentResultDao.save(new TournamentResult(lfds, 2020, 2));
            tournamentResultDao.save(new TournamentResult(lldb, 2016, 2));
            tournamentResultDao.save(new TournamentResult(lfds, 2016, 1));
        }
    }
}

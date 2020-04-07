package fr.polytech.rlcalm.initializer;

import fr.polytech.rlcalm.beans.Player;
import fr.polytech.rlcalm.beans.Role;
import fr.polytech.rlcalm.dao.player.PlayerDao;
import fr.polytech.rlcalm.dao.player.impl.HibernatePlayerDao;
import fr.polytech.rlcalm.service.PlayerService;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Optional;

@WebListener
public class ControllerInitializer implements ServletContextListener {

    @Getter
    private static PlayerService playerService;

    public enum DateBaseImpl {
        HASHMAP, MYSQL
    }

    private static final DateBaseImpl INITIALIZER_TYPE = DateBaseImpl.HASHMAP;

    private static PlayerDao playerDao;
    private static Configuration configuration;

    static {
        switch (INITIALIZER_TYPE) {
            case MYSQL: {
                configuration = new Configuration().configure();
                Session hibernateSession = configuration.buildSessionFactory().openSession();
                playerDao = new HibernatePlayerDao(hibernateSession);
                playerService = new PlayerService(playerDao);
                break;
            }
            case HASHMAP: {
                playerDao = new PlayerDao() {//TODO juste pour test, à enlever
                    @Override
                    public Optional<Player> findById(Long playerId) {
                        return Optional.empty();
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }

                    @Override
                    public void save(Player p) {

                    }
                };
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + INITIALIZER_TYPE);
        }
        fillTables();
    }

    public static Configuration getConfig() {
        return configuration;
    }

    private static void fillTables() {
        if (playerDao.isEmpty()) {
            playerDao.save(new Player(0L, "Ronaldo", 0, null, Role.Central));
        }
    }
}

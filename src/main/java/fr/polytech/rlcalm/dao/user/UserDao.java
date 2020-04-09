package fr.polytech.rlcalm.dao.user;

import fr.polytech.rlcalm.beans.User;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    void save(User user);

    Optional<User> findById(Long userId);

    Collection<User> getAll();

    Optional<User> getUserByName(String username);

    boolean isEmpty();
}

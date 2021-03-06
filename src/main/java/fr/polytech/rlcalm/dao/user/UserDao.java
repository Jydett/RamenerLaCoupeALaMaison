package fr.polytech.rlcalm.dao.user;

import fr.polytech.rlcalm.beans.User;

import java.util.Optional;

public interface UserDao {
    void save(User user);

    Optional<User> getUserByName(String username);

    boolean isEmpty();
}

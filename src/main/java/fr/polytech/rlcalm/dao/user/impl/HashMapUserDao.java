package fr.polytech.rlcalm.dao.user.impl;

import fr.polytech.rlcalm.beans.User;
import fr.polytech.rlcalm.dao.HashMapDao;
import fr.polytech.rlcalm.dao.user.UserDao;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class HashMapUserDao extends HashMapDao<Long, User> implements UserDao {

    private final static AtomicLong id = new AtomicLong(0);

    public HashMapUserDao() {
        super(id::incrementAndGet);
    }

    @Override
    public Optional<User> getUserByName(String username) {
        return table.values().stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}

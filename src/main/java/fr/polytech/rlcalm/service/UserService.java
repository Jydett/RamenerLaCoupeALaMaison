package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.User;
import fr.polytech.rlcalm.dao.user.UserDao;
import fr.polytech.rlcalm.exception.InvalidFormException;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.utils.FormUtils;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class UserService {
    public UserDao userDao;

    public User authenticate(String login, String password) throws ServiceException, InvalidFormException {
        if (FormUtils.isNullOrEmpty(login) || FormUtils.isNullOrEmpty(password)) {
            throw new InvalidFormException("Un login et un mot de passe sont requis");
        }
        Optional<User> optionalUser = userDao.getUserByName(login);
        if (! optionalUser.isPresent()) {
            throw new ServiceException("Utilisateur inconu");
        }
        User u = optionalUser.get();
        if (! password.equals(u.getPassword())) {
            throw new ServiceException("Mot de passe incorrecte");
        }
        return u;
    }
}

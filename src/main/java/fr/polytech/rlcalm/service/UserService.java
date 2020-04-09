package fr.polytech.rlcalm.service;

import fr.polytech.rlcalm.beans.User;
import fr.polytech.rlcalm.dao.user.UserDao;
import fr.polytech.rlcalm.exception.ServiceException;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class UserService {
    public UserDao userDao;

    public User authenticate(String login, String password) throws ServiceException {
        if (login == null || password == null) {
            throw new ServiceException("Formulaire invlide");
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

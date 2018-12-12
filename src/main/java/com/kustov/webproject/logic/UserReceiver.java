package com.kustov.webproject.logic;

import com.kustov.webproject.dao.UserDAO;
import com.kustov.webproject.dao.UserRoleDao;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.service.Encryptor;

import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 * The Class UserReceiver.
 */
public class UserReceiver {

    /**
     * Check user.
     *
     * @param login    the login
     * @param password the password
     * @return the user
     * @throws ServiceException the service exception
     */
    public User checkUser(String login, String password) throws ServiceException {
        UserDAO dao = new UserDAO();
        try {
            Encryptor encryptor = new Encryptor();
            User user = dao.findUserByUsernameAndPassword(login, encryptor.encryptPassword(password));
            if (user != null) {
                UserRoleDao userRoleDao = new UserRoleDao();
                user.setType(userRoleDao.findRoleByUserId(user.getId()));
            }
            return user;
        } catch (NoSuchAlgorithmException | DAOException exc) {
            throw new ServiceException(exc);
        }
    }


    /**
     * Find all users.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<User> findAllUsers() throws ServiceException {
        UserDAO dao = new UserDAO();
        try {
            return dao.findAll();
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Find user by id.
     *
     * @param id the id
     * @return the user
     * @throws ServiceException the service exception
     */
    public User findUserById(int id) throws ServiceException {
        UserDAO userDAO = new UserDAO();
        try {
            return userDAO.findById(id);
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

}

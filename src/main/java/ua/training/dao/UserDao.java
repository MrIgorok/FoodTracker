package ua.training.dao;

import ua.training.model.User;
import ua.training.utils.exception.ServiceException;

public interface UserDao {
    User findById(long id) throws ServiceException;
    User findByName(String name) throws ServiceException;
    User create(User user) throws ServiceException;
    void update(User user) throws ServiceException;
    void delete(long id) throws ServiceException;
}

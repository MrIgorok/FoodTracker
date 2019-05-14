package ua.training.model.dao;

import ua.training.model.entity.User;
import ua.training.utils.exception.PersistentException;

public interface UserDao {
    User findById(long id) throws PersistentException;
    User findByName(String name) throws PersistentException;
    User create(User user) throws PersistentException;
    void update(User user) throws PersistentException;
    void delete(long id) throws PersistentException;
}

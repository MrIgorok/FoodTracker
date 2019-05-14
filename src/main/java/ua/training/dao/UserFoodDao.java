package ua.training.dao;

import ua.training.model.food.UserFoodInfo;
import ua.training.utils.exception.PersistentException;

import java.time.LocalDateTime;

public interface UserFoodDao {
    UserFoodInfo findById(long id) throws PersistentException;
    UserFoodInfo findByIdInInterval(long id, LocalDateTime fromInclude, LocalDateTime toInclude) throws PersistentException;
    UserFoodInfo findByUserId(long id) throws PersistentException;
    UserFoodInfo create(long userId, UserFoodInfo userFoodInfo) throws PersistentException;
    void update(UserFoodInfo UserFoodInfo) throws PersistentException;
    void delete(long id) throws PersistentException;
}

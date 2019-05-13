package ua.training.dao;

import ua.training.model.food.UserFoodInfo;
import ua.training.utils.exception.ServiceException;

import java.time.LocalDateTime;

public interface UserFoodDao {
    UserFoodInfo findById(long id) throws ServiceException;
    UserFoodInfo findByIdInInterval(long id, LocalDateTime fromInclude, LocalDateTime toInclude) throws ServiceException;
    UserFoodInfo create(UserFoodInfo userFoodInfo) throws ServiceException;
    void update(UserFoodInfo UserFoodInfo) throws ServiceException;
    void delete(long id) throws ServiceException;
}

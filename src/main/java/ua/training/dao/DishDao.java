package ua.training.dao;

import ua.training.model.food.Dish;
import ua.training.model.food.Ingredient;
import ua.training.utils.exception.ServiceException;

public interface DishDao {
    Dish findById(long id) throws ServiceException;
    Dish findByName(String name) throws ServiceException;
    Dish create(Dish dish) throws ServiceException;
    void update(Dish dish) throws ServiceException;
    void delete(long id) throws ServiceException;
}

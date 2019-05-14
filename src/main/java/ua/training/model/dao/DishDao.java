package ua.training.model.dao;

import ua.training.model.entity.food.Dish;
import ua.training.utils.exception.PersistentException;

public interface DishDao {
    Dish findById(long id) throws PersistentException;
    Dish findByName(String name) throws PersistentException;
    Dish create(Dish dish) throws PersistentException;
    void update(Dish dish) throws PersistentException;
    void delete(long id) throws PersistentException;
}

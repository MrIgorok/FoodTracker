package ua.training.dao;

import ua.training.model.food.Ingredient;
import ua.training.utils.exception.ServiceException;

public interface IngredientDao {
    Ingredient findById(long id) throws ServiceException;
    Ingredient findByName(String name) throws ServiceException;
    Ingredient create(Ingredient ingredient) throws ServiceException;
    void update(Ingredient ingredient) throws ServiceException;
    void delete(long id) throws ServiceException;
}

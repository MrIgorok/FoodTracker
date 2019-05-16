package ua.training.model.dao;

import ua.training.model.entity.food.Ingredient;
import ua.training.utils.exception.PersistentException;

public interface IngredientDao {
    Ingredient findById(long id) throws PersistentException;
    Ingredient findByName(String name) throws PersistentException;
    Ingredient create(Ingredient ingredient) throws PersistentException;
    void update(Ingredient ingredient) throws PersistentException;
    void delete(long id) throws PersistentException;

    boolean contains(String name) throws PersistentException;
}

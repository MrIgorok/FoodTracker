package ua.training.model.service;

import ua.training.model.dao.IngredientDao;
import ua.training.model.entity.food.Ingredient;
import ua.training.utils.exception.PersistentException;
import ua.training.utils.exception.ServiceException;

public class IngredientService {
    private IngredientDao ingredientDao;

    public void createIngredient(Ingredient ingredient) throws ServiceException {
        // TODO: validate ingredient
        try {
            ingredientDao.create(ingredient);
        } catch (PersistentException e) {
            // TODO: LOG
            throw new ServiceException();
        }
    }

    public void deleteIngredient(Ingredient ingredient) throws ServiceException {
        // TODO: validate ingredient
        try {
            ingredientDao.delete(ingredient.getId());
        } catch (PersistentException e) {
            // TODO: LOG
            throw new ServiceException();
        }
    }

    public void updateIngredient(Ingredient ingredient) throws ServiceException {
        try {
            ingredientDao.update(ingredient);
        } catch (PersistentException e) {
            throw new ServiceException();
        }
    }

    public Ingredient findIngredientByName(String name) throws ServiceException {
        Ingredient ingredient;

        try {
            ingredient = ingredientDao.findByName(name);
        } catch (PersistentException e) {
            throw new ServiceException();
        }

        return ingredient;
    }

    public Ingredient findIngredientById(long id) throws ServiceException {
        Ingredient ingredient;

        try {
            ingredient = ingredientDao.findById(id);
        } catch (PersistentException e) {
            throw new ServiceException();
        }

        return ingredient;
    }
}

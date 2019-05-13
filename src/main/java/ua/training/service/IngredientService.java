package ua.training.service;

import ua.training.dao.IngredientDao;
import ua.training.model.food.Ingredient;

public class IngredientService {
    IngredientDao ingredientDao;

    public void createIngredient(Ingredient ingredient) {

        ingredientDao.create(ingredient);
    }

    public void deleteIngredient(Ingredient ingredient) {
        ingredientDao.delete(ingredient);
    }

    public void updateIngredient(Ingredient ingredient) {
        ingredientDao.update(ingredient);
    }

    public Ingredient findIngredientByName(String name) {
        return ingredientDao.findByName(name);
    }

    public Ingredient findIngredientById(long id) {
        return ingredientDao.findById(id);
    }


}

package ua.training.model.service;

import ua.training.model.dao.DishDao;
import ua.training.model.entity.food.Dish;
import ua.training.utils.exception.PersistentException;
import ua.training.utils.exception.ServiceException;

public class DishService {
    private DishDao dishDao;

    public void createDish(Dish dish) throws ServiceException {
        // TODO: validate ingredient
        try {
            dishDao.create(dish);
        } catch (PersistentException e) {
            // TODO: LOG
            throw new ServiceException();
        }
    }

    public void deleteDish(Dish dish) throws ServiceException {
        // TODO: validate ingredient
        try {
            dishDao.delete(dish.getId());
        } catch (PersistentException e) {
            // TODO: LOG
            throw new ServiceException();
        }
    }

    public void updateDish(Dish dish) throws ServiceException {
        // TODO: validate ingredient
        try {
            dishDao.update(dish);
        } catch (PersistentException e) {
            // TODO: LOG
            throw new ServiceException();
        }
    }

    public Dish findDishById(long id) throws ServiceException {
        Dish dish;

        try {
            dish = dishDao.findById(id);
        } catch (PersistentException e) {
            // TODO: LOG
            throw new ServiceException();
        }

        return dish;
    }

    public Dish findDishByName(String name) throws ServiceException {
        Dish dish;

        try {
            dish = dishDao.findByName(name);
        } catch (PersistentException e) {
            // TODO: LOG
            throw new ServiceException();
        }

        return dish;
    }

}

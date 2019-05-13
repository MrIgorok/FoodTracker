package ua.training.service;

import ua.training.dao.DishDao;
import ua.training.model.food.Dish;

public class DishService {
    private DishDao dishDao;

    public void createDish(Dish dish) {
        dishDao.create(dish);
    }

    public void deleteDish(Dish dish) {
        dishDao.delete(dish);
    }

    public void updateDish(Dish dish) {
        dishDao.update(dish);
    }

    public Dish findDish(Dish dish) {
        return dishDao.find(dish);
    }

//    public double getCalories() {
//        return dishes.stream()
//                .reduce(0.0,
//                        (accumulate, dish) -> accumulate + dish.getCalories(),
//                        (val1, val2) -> val1 + val2);
//    }
}

package ua.training.service;

import ua.training.dao.UserFoodDao;
import ua.training.model.User;
import ua.training.model.food.Dish;
import ua.training.model.food.UserFoodInfo;
import ua.training.utils.exception.PersistentException;
import ua.training.utils.exception.ServiceException;

import java.time.LocalDateTime;
import java.util.Map;

public class UserFoodService {
    private UserFoodDao userFoodDao;

    public void createUserFoodInfo(User user) throws ServiceException {
        // TODO: validate ingredient
        try {
            userFoodDao.create(user.getId(), user.getFoodInfo());
        } catch (PersistentException e) {
            // TODO: LOG
            throw new ServiceException();
        }
    }

    public void deleteUserFoodInfo(long id) throws ServiceException {
        try {
            userFoodDao.delete(id);
        } catch (PersistentException e) {
            // TODO: LOG
            throw new ServiceException();
        }
    }

    public void addUserMeal(User user, LocalDateTime timestamp, Dish dish) throws ServiceException {

    }

    public void addAllUserMeals(User user, Map<LocalDateTime, Dish> dishes) throws ServiceException {

    }

    public UserFoodInfo findUserFoodInfoById(long id) {
        return new UserFoodInfo();
    }

    //    public double getCalories() {
//        return dishes.stream()
//                .reduce(0.0,
//                        (accumulate, dish) -> accumulate + dish.getCalories(),
//                        (val1, val2) -> val1 + val2);
//    }
}

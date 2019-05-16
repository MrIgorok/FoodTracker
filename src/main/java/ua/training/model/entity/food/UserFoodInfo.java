package ua.training.model.entity.food;

import java.time.LocalDateTime;
import java.util.*;

public class UserFoodInfo {
    private Long id;
    private int caloriesPerDay;

    private NavigableMap<LocalDateTime, Dish> mealTimestamp = new TreeMap<>();

    public UserFoodInfo() {

    }

    public UserFoodInfo(Long id, int caloriesPerDay, NavigableMap<LocalDateTime, Dish> mealTimestamp) {
        this.id = id;
        this.caloriesPerDay = caloriesPerDay;
        this.mealTimestamp = mealTimestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setCaloriesPerDay(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public void setMealTimestamp(NavigableMap<LocalDateTime, Dish> mealTimestamp) {
        this.mealTimestamp = mealTimestamp;
    }

    public NavigableMap<LocalDateTime, Dish> getMealTimestamp() {
        return mealTimestamp;
    }
}

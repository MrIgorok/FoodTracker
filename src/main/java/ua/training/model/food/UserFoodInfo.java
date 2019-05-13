package ua.training.model.food;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class UserFoodInfo {
    private long id = -1L;
    private double caloriesPerDay;

    private NavigableMap<LocalDateTime, Dish> mealTimestamp = new TreeMap<>();

    public UserFoodInfo() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setCaloriesPerDay(double caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public Dish getDishInTimestamp(LocalDateTime dateTime) {
        return mealTimestamp.get(dateTime);
    }

    public void setMealTimestamp(NavigableMap<LocalDateTime, Dish> mealTimestamp) {
        this.mealTimestamp = mealTimestamp;
    }

    public void addDishesPerDay(LocalDateTime dateTime, Dish dish) {
        mealTimestamp.put(dateTime, dish);
    }

    public Map<LocalDateTime, Dish> getDishesInDaysInterval(LocalDate fromInclude, LocalDate toInclude) {
        return mealTimestamp.subMap(LocalDateTime.of(fromInclude, LocalTime.MIN), LocalDateTime.of(toInclude, LocalTime.MAX));
    }

    public NavigableMap<LocalDateTime, Dish> getMealTimestamp() {
        return mealTimestamp;
    }

    public List<Dish> getDishesInDay(LocalDate date) {
        Map<LocalDateTime, Dish> map = mealTimestamp.subMap(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));

        return new ArrayList<>(map.values());
    }
}

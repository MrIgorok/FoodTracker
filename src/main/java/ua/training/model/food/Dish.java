package ua.training.model.food;

import java.util.List;

public class Dish {
    private long id = -1L;
    private String name;
    private List<IngredientWeight> ingredients;

    public Dish() {

    }

    public Dish(long id, String name, List<IngredientWeight> ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientWeight> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientWeight> ingredients) {
        this.ingredients = ingredients;
    }

    public double getCalories() {
        return ingredients.stream()
                .reduce(0.0,
                        (accumulate, ingredient) -> accumulate + ingredient.getWeight().getValue(),
                        (val1, val2) -> val1 + val2);
    }
}

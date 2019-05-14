package ua.training.model.entity.food;

public class IngredientWeight {
    private Ingredient ingredient;
    private Weight weight;

    public IngredientWeight(Ingredient ingredient, Weight amount) {
        this.ingredient = ingredient;
        this.weight = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }
}

package ua.training.model.entity.food;

public class IngredientWeight {
    private Ingredient ingredient;
    private long weightGrams;

    public IngredientWeight() {
    }

    public IngredientWeight(Ingredient ingredient, long weightGrams) {
        this.ingredient = ingredient;
        this.weightGrams = weightGrams;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public long getWeightGrams() {
        return weightGrams;
    }

    public void setWeightGrams(long weightGrams) {
        this.weightGrams = weightGrams;
    }
}

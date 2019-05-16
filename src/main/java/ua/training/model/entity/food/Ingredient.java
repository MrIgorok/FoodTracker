package ua.training.model.entity.food;

public class Ingredient {
    private Long id;
    private String name;
    private int caloriesPer100Grams;
    private int milliProteinsPer100Grams;
    private int milliCarbohydratesPer100Grams;
    private int milliFatsPer100Grams;

    public Ingredient() {

    }

    public Ingredient(long id, String name, int caloriesPer100Grams, int milliProteinsPer100Grams,
                      int milliCarbohydratesPer100Grams, int milliFatsPer100Grams) {
        this.id = id;
        this.name = name;
        this.caloriesPer100Grams = caloriesPer100Grams;
        this.milliProteinsPer100Grams = milliProteinsPer100Grams;
        this.milliCarbohydratesPer100Grams = milliCarbohydratesPer100Grams;
        this.milliFatsPer100Grams = milliFatsPer100Grams;
    }

    public Long getId() {
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

    public int getCaloriesPer100Grams() {
        return caloriesPer100Grams;
    }

    public void setCaloriesPer100Grams(int caloriesPer100Grams) {
        this.caloriesPer100Grams = caloriesPer100Grams;
    }

    public int getMilliProteinsPer100Grams() {
        return milliProteinsPer100Grams;
    }

    public void setMilliProteinsPer100Grams(int milliProteinsPer100Grams) {
        this.milliProteinsPer100Grams = milliProteinsPer100Grams;
    }

    public int getMilliCarbohydratesPer100Grams() {
        return milliCarbohydratesPer100Grams;
    }

    public void setMilliCarbohydratesPer100Grams(int milliCarbohydratesPer100Grams) {
        this.milliCarbohydratesPer100Grams = milliCarbohydratesPer100Grams;
    }

    public int getMilliFatsPer100Grams() {
        return milliFatsPer100Grams;
    }

    public void setMilliFatsPer100Grams(int milliFatsPer100Grams) {
        this.milliFatsPer100Grams = milliFatsPer100Grams;
    }
}

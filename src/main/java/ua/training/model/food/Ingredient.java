package ua.training.model.food;

public class Ingredient {
    private long id = -1L;
    private String name;
    private double kiloCaloriesPer100Grams;
    private double proteinsPer100Grams;
    private double carbohydratesPer100Grams;
    private double fatsPer100Grams;

    public Ingredient() {

    }

    public Ingredient(long id, String name, double kiloCaloriesPer100Grams, double proteinsPer100Grams,
                      double carbohydratesPer100Grams, double fatsPer100Grams) {
        this.id = id;
        this.name = name;
        this.kiloCaloriesPer100Grams = kiloCaloriesPer100Grams;
        this.proteinsPer100Grams = proteinsPer100Grams;
        this.carbohydratesPer100Grams = carbohydratesPer100Grams;
        this.fatsPer100Grams = fatsPer100Grams;
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

    public double getKiloCaloriesPer100Grams() {
        return kiloCaloriesPer100Grams;
    }

    public void setKiloCaloriesPer100Grams(double kiloCaloriesPer100Grams) {
        this.kiloCaloriesPer100Grams = kiloCaloriesPer100Grams;
    }

    public double getProteinsPer100Grams() {
        return proteinsPer100Grams;
    }

    public void setProteinsPer100Grams(double proteinsPer100Grams) {
        this.proteinsPer100Grams = proteinsPer100Grams;
    }

    public double getCarbohydratesPer100Grams() {
        return carbohydratesPer100Grams;
    }

    public void setCarbohydratesPer100Grams(double carbohydratesPer100Grams) {
        this.carbohydratesPer100Grams = carbohydratesPer100Grams;
    }

    public double getFatsPer100Grams() {
        return fatsPer100Grams;
    }

    public void setFatsPer100Grams(double fatsPer100Grams) {
        this.fatsPer100Grams = fatsPer100Grams;
    }
}

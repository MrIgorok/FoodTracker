package ua.training.model.food;

public class Weight {
    private double value;
    private Unit measureUnit;

    private Weight(double weight, Unit measureUnit) {
        this.value = weight;
        this.measureUnit = measureUnit;
    }

    public static Weight getWeightByGrams(double weight) {
        return new Weight(weight, Unit.GRAMS);
    }

    public static Weight getValue(double weight, Unit unit) {
        return new Weight(weight, unit);
    }

    public double getValue() {
        return value;
    }

    public Unit getMeasureUnit() {
        return measureUnit;
    }

    public enum Unit {
        GRAMS(1), KILOGRAMS(1000);

        private double scaleValue;

        Unit(double scaleValue) {
            this.scaleValue = scaleValue;
        }
    }
}

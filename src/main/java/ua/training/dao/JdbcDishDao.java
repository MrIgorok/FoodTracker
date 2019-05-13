package ua.training.dao;

import ua.training.configuration.Inject;
import ua.training.model.food.Dish;
import ua.training.model.food.Ingredient;
import ua.training.model.food.IngredientWeight;
import ua.training.model.food.Weight;
import ua.training.utils.exception.ServiceException;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDishDao implements DishDao {
    private DataSource dataSource;

    private static String find = "SELECT * FROM Dish WHERE %s = ?";
    private static String getIngredients = "SELECT Ingredient.id, Ingredient.name, Dish_Ingredient_Relations.ingredient_weight, " +
            "Ingredient.kilo_calories_per_100_grams, Ingredient.proteins_per_100_grams, " +
            "Ingredient.carbohydrates_per_100_grams, Ingredient.fats_per_100_grams " +
            "FROM Dish_Ingredient_Relations RIGHT JOIN Ingredient ON Dish_Ingredient_Relations.ingredient_fk=Ingredient.id " +
            "WHERE Dish_Ingredient_Relations.dish_fk = ?";
    private static String update = "UPDATE Dish SET name = ? WHERE id = ?";

    @Inject
    public JdbcDishDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Dish findById(long id) throws ServiceException {
        Dish dish;

        try (Connection conn = dataSource.getConnection()) {
            dish = find(conn, id);
        } catch (SQLException e) {
            throw new ServiceException("Dish id doesn't exist.", e);
        }

        return dish;
    }

    @Override
    public Dish findByName(String name) throws ServiceException {
        Dish dish;

        try (Connection conn = dataSource.getConnection()) {
            dish = find(conn, name);
        } catch (SQLException e) {
            throw new ServiceException("Dish name doesn't exist.", e);
        }

        return dish;
    }

    @Override
    public Dish create(Dish dish) throws ServiceException {
        try (Connection conn = dataSource.getConnection()) {
            dish = create(conn, dish);
        } catch (SQLException e) {
            throw new ServiceException("Dish creation error.", e);
        }

        return dish;
    }

    @Override
    public void update(Dish dish) throws ServiceException {
        // TODO
    }

    @Override
    public void delete(long id) throws ServiceException {
        try (Connection conn = dataSource.getConnection()) {
            String deleteById = "DELETE FROM Dish WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(deleteById);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ServiceException("Dish delete error.", e);
        }
    }

    static Dish find(Connection conn, long id) throws SQLException {
        ResultSet resultSet = executeFindById(conn, id);

        return getDishWithIngredients(conn, resultSet);
    }

    static Dish find(Connection conn, String name) throws SQLException {
        ResultSet resultSet = executeFindByName(conn, name);

        return getDishWithIngredients(conn, resultSet);
    }

    private static ResultSet executeFindByName(Connection conn, String name) throws SQLException {
        String findByName = String.format(find, "name");
        PreparedStatement preparedStatement = conn.prepareStatement(findByName);
        preparedStatement.setString(1, name);

        return preparedStatement.executeQuery();
    }

    private static ResultSet executeFindById(Connection conn, long id) throws SQLException {
        String findByName = String.format(find, "id");
        PreparedStatement preparedStatement = conn.prepareStatement(findByName);
        preparedStatement.setLong(1, id);

        return preparedStatement.executeQuery();
    }

    private static Dish getDishWithIngredients(Connection conn, ResultSet resultSet) throws SQLException {
        Dish dish = dishMapping(resultSet);

        PreparedStatement preparedStatement = conn.prepareStatement(getIngredients);
        preparedStatement.setLong(1, dish.getId());

        resultSet = preparedStatement.executeQuery();

        dish.setIngredients(getListIngredients(resultSet));

        return dish;
    }

    private static Dish dishMapping(ResultSet resultSet) throws SQLException {
        Dish dish = new Dish();

        if (resultSet.next()) {
            dish.setId(resultSet.getLong("id"));
            dish.setName(resultSet.getString("name"));
            // TODO: IngredientValidator.validate(ingredient)
        } else {
            dish.setId(-1L);
        }

        return dish;
    }

    private static List<IngredientWeight> getListIngredients(ResultSet resultSet) throws SQLException {
        List<IngredientWeight> ingredients = new ArrayList<>();

        while (resultSet.next()) {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(resultSet.getLong("Ingredient.id"));
            ingredient.setName(resultSet.getString("Ingredient.name"));
            ingredient.setKiloCaloriesPer100Grams(resultSet.getDouble("Ingredient.kilo_calories_per_100_grams"));
            ingredient.setProteinsPer100Grams(resultSet.getDouble("Ingredient.proteins_per_100_grams"));
            ingredient.setCarbohydratesPer100Grams(resultSet.getDouble("Ingredient.carbohydrates_per_100_grams"));
            ingredient.setFatsPer100Grams(resultSet.getDouble("Ingredient.fats_per_100_grams"));

            Weight weight = Weight.getWeightByGrams(resultSet.getDouble("Dish_Ingredient_Relations.ingredient_weight"));

            ingredients.add(new IngredientWeight(ingredient, weight));
        }

        return ingredients;
    }

    // TODO
    private static Dish create(Connection conn, Dish dish) throws SQLException {
        String create = "INSERT INTO Dish (name) VALUES(?)";
        PreparedStatement preparedStatement = conn.prepareStatement(create, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, dish.getName());

        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if (resultSet.next()) {
            dish.setId(resultSet.getLong(1));
        }

        List<IngredientWeight> ingredients = dish.getIngredients();

        List<Ingredient> createdIngredients = new ArrayList<>();

        for (IngredientWeight ingredientWeight : ingredients) {
            if (ingredientWeight.getIngredient().getId() == -1) {
                createdIngredients.add(ingredientWeight.getIngredient());
            }
        }

        JdbcIngredientDao.createAll(conn, createdIngredients);

        String createIngredientDishRelation = "INSERT INTO Dish_Ingredient_Relations (dish_fk, ingredient_fk, ingredient_weight) VALUES(?, ?, ?)";
        preparedStatement = conn.prepareStatement(createIngredientDishRelation);
        for (IngredientWeight ingredientWeight : dish.getIngredients()) {
            preparedStatement.setLong(1, dish.getId());
            preparedStatement.setLong(2, ingredientWeight.getIngredient().getId());
            preparedStatement.setDouble(3, ingredientWeight.getWeight().getValue());
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();

        return dish;
    }
}

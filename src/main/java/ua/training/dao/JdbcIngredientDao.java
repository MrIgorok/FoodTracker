package ua.training.dao;

import ua.training.configuration.Inject;
import ua.training.model.food.Ingredient;
import ua.training.utils.exception.ServiceException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Iterator;
import java.util.List;

public class JdbcIngredientDao implements IngredientDao {
    private DataSource dataSource;

    @Inject
    public JdbcIngredientDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Ingredient findById(long id) throws ServiceException {
        Ingredient ingredient;

        try (Connection conn = dataSource.getConnection()) {
            ingredient = find(conn, id);
        } catch (SQLException e) {
            throw new ServiceException("Ingredient id doesn't exist.", e);
        }

        return ingredient;
    }

    @Override
    public Ingredient findByName(String name) throws ServiceException {
        Ingredient ingredient;

        try (Connection conn = dataSource.getConnection()) {
            ingredient = find(conn, name);
        } catch (SQLException e) {
            throw new ServiceException("Ingredient name doesn't exist.", e);
        }

        return ingredient;
    }

    @Override
    public Ingredient create(Ingredient ingredient) throws ServiceException {
        try (Connection conn = dataSource.getConnection()) {
            ingredient = create(conn, ingredient);
        } catch (SQLException e) {
            throw new ServiceException("Ingredient creation error.", e);
        }

        return ingredient;
    }

    @Override
    public void update(Ingredient ingredient) throws ServiceException {
        try (Connection conn = dataSource.getConnection()) {
            String update = "UPDATE Ingredient " +
                    "SET kilo_calories_per_100_grams = ?, proteins_per_100_grams = ?, " +
                    "carbohydrates_per_100_grams = ?, fats_per_100_grams = ? " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(update);
            preparedStatement.setDouble(1, ingredient.getKiloCaloriesPer100Grams());
            preparedStatement.setDouble(2, ingredient.getProteinsPer100Grams());
            preparedStatement.setDouble(3, ingredient.getCarbohydratesPer100Grams());
            preparedStatement.setDouble(4, ingredient.getFatsPer100Grams());
            preparedStatement.setLong(5, ingredient.getId());


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ServiceException("Ingredient update error.", e);
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        try (Connection conn = dataSource.getConnection()) {
            String deleteById = "DELETE FROM Ingredient WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(deleteById);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ServiceException("Ingredient delete error.", e);
        }
    }

    static Ingredient find(Connection conn, String name) throws SQLException {
        PreparedStatement preparedStatement = createFindPreparedStatement(conn, name);

        return executeStatementAndMapIngredient(preparedStatement);
    }

    static Ingredient find(Connection conn, long id) throws SQLException {
        PreparedStatement preparedStatement = createFindPreparedStatement(conn, id);

        return executeStatementAndMapIngredient(preparedStatement);
    }

    private static PreparedStatement createFindPreparedStatement(Connection conn, String name) throws SQLException {
        String findByName = "SELECT * FROM Ingredient WHERE name = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(findByName);
        preparedStatement.setString(1, name);

        return preparedStatement;
    }

    private static PreparedStatement createFindPreparedStatement(Connection conn, long id) throws SQLException {
        String findByName = "SELECT * FROM Ingredient WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(findByName);
        preparedStatement.setLong(1, id);

        return preparedStatement;
    }

    private static Ingredient executeStatementAndMapIngredient(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        return ingredientMapping(resultSet);
    }

    private static Ingredient ingredientMapping(ResultSet resultSet) throws SQLException {
        Ingredient ingredient = new Ingredient();

        if (resultSet.next()) {
            ingredient.setId(resultSet.getLong("id"));
            ingredient.setName(resultSet.getString("name"));
            ingredient.setKiloCaloriesPer100Grams(resultSet.getDouble("kilo_calories_per_100_grams"));
            ingredient.setProteinsPer100Grams(resultSet.getDouble("proteins_per_100_grams"));
            ingredient.setCarbohydratesPer100Grams(resultSet.getDouble("carbohydrates_per_100_grams"));
            ingredient.setFatsPer100Grams(resultSet.getDouble("fats_per_100_grams"));
            // TODO: IngredientValidator.validate(ingredient)
        } else {
            ingredient.setId(-1L);
        }

        return ingredient;
    }

    static Ingredient create(Connection conn, Ingredient ingredient) throws SQLException {
        String create = "INSERT INTO Ingredient " +
                "(name, kilo_calories_per_100_grams, proteins_per_100_grams, carbohydrates_per_100_grams, fats_per_100_grams) " +
                "VALUES(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(create, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, ingredient.getName());
        preparedStatement.setDouble(2, ingredient.getKiloCaloriesPer100Grams());
        preparedStatement.setDouble(3, ingredient.getProteinsPer100Grams());
        preparedStatement.setDouble(4, ingredient.getCarbohydratesPer100Grams());
        preparedStatement.setDouble(5, ingredient.getFatsPer100Grams());


        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if (resultSet.next()) {
            ingredient.setId(resultSet.getLong(1));
        }

        return ingredient;
    }

    static void createAll(Connection conn, List<Ingredient> ingredientList) throws SQLException {
        String create = "INSERT INTO Ingredient " +
                "(name, kilo_calories_per_100_grams, proteins_per_100_grams, carbohydrates_per_100_grams, fats_per_100_grams) " +
                "VALUES(?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(create, Statement.RETURN_GENERATED_KEYS);

        for (Ingredient ingredient : ingredientList) {
            preparedStatement.setString(1, ingredient.getName());
            preparedStatement.setDouble(2, ingredient.getKiloCaloriesPer100Grams());
            preparedStatement.setDouble(3, ingredient.getProteinsPer100Grams());
            preparedStatement.setDouble(4, ingredient.getCarbohydratesPer100Grams());
            preparedStatement.setDouble(5, ingredient.getFatsPer100Grams());
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        Iterator itr = ingredientList.iterator();

        while (resultSet.next() && itr.hasNext()) {
            ((Ingredient)itr.next()).setId(resultSet.getLong(1));
        }
    }
}

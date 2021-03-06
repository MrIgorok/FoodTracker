package ua.training.model.dao;

import ua.training.model.entity.food.Ingredient;
import ua.training.utils.QueriesInitializer;
import ua.training.utils.exception.PersistentException;

import javax.inject.Inject;
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
    public Ingredient findById(long id) throws PersistentException {
        Ingredient ingredient;

        try (Connection conn = dataSource.getConnection()) {
            ingredient = find(conn, id);
        } catch (SQLException e) {
            throw new PersistentException("Ingredient id doesn't exist.", e);
        }

        return ingredient;
    }

    @Override
    public Ingredient findByName(String name) throws PersistentException {
        Ingredient ingredient;

        try (Connection conn = dataSource.getConnection()) {
            ingredient = find(conn, name);
        } catch (SQLException e) {
            throw new PersistentException("Ingredient name doesn't exist.", e);
        }

        return ingredient;
    }

    @Override
    public Ingredient create(Ingredient ingredient) throws PersistentException {
        try (Connection conn = dataSource.getConnection()) {
            ingredient = create(conn, ingredient);
        } catch (SQLException e) {
            throw new PersistentException("Ingredient creation error.", e);
        }

        return ingredient;
    }

    @Override
    public void update(Ingredient ingredient) throws PersistentException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(Queries.update);
            preparedStatement.setDouble(1, ingredient.getKiloCaloriesPer100Grams());
            preparedStatement.setDouble(2, ingredient.getMilliProteinsPer100Grams());
            preparedStatement.setDouble(3, ingredient.getMilliCarbohydratesPer100Grams());
            preparedStatement.setDouble(4, ingredient.getMilliFatsPer100Grams());
            preparedStatement.setLong(5, ingredient.getId());


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException("Ingredient update error.", e);
        }
    }

    @Override
    public void delete(long id) throws PersistentException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(Queries.deleteById);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException("Ingredient delete error.", e);
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
        PreparedStatement preparedStatement = conn.prepareStatement(Queries.findByName);
        preparedStatement.setString(1, name);

        return preparedStatement;
    }

    private static PreparedStatement createFindPreparedStatement(Connection conn, long id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(Queries.findById);
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
            ingredient.setMilliProteinsPer100Grams(resultSet.getDouble("proteins_per_100_grams"));
            ingredient.setMilliCarbohydratesPer100Grams(resultSet.getDouble("carbohydrates_per_100_grams"));
            ingredient.setMilliFatsPer100Grams(resultSet.getDouble("fats_per_100_grams"));
            // TODO: IngredientValidator.validate(ingredient)
        } else {
            ingredient.setId(-1L);
        }

        return ingredient;
    }

    static Ingredient create(Connection conn, Ingredient ingredient) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(Queries.create, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, ingredient.getName());
        preparedStatement.setDouble(2, ingredient.getKiloCaloriesPer100Grams());
        preparedStatement.setDouble(3, ingredient.getMilliProteinsPer100Grams());
        preparedStatement.setDouble(4, ingredient.getMilliCarbohydratesPer100Grams());
        preparedStatement.setDouble(5, ingredient.getMilliFatsPer100Grams());


        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if (resultSet.next()) {
            ingredient.setId(resultSet.getLong(1));
        }

        return ingredient;
    }

    static void createAll(Connection conn, List<Ingredient> ingredientList) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(Queries.create, Statement.RETURN_GENERATED_KEYS);

        for (Ingredient ingredient : ingredientList) {
            preparedStatement.setString(1, ingredient.getName());
            preparedStatement.setDouble(2, ingredient.getKiloCaloriesPer100Grams());
            preparedStatement.setDouble(3, ingredient.getMilliProteinsPer100Grams());
            preparedStatement.setDouble(4, ingredient.getMilliCarbohydratesPer100Grams());
            preparedStatement.setDouble(5, ingredient.getMilliFatsPer100Grams());
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        Iterator itr = ingredientList.iterator();

        while (resultSet.next() && itr.hasNext()) {
            ((Ingredient)itr.next()).setId(resultSet.getLong(1));
        }
    }

    private static class Queries extends QueriesInitializer {
        static boolean loaded = false;
        static String findById;
        static String findByName;
        static String create;
        static String update;
        static String deleteById;

        protected boolean loaded() {
            return loaded;
        }

        protected void setLoad() {
            loaded = true;
        }
    }
}

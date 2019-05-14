package ua.training.model.dao;

import ua.training.model.entity.food.*;
import ua.training.utils.QueriesInitializer;
import ua.training.utils.exception.PersistentException;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class JdbcUserFoodDao implements UserFoodDao {
    private DataSource dataSource;

    private static String getDishes = "SELECT date_time, dish_fk FROM User_Food_Info_Dish_Relations WHERE user_food_info_fk = ?";

    @Inject
    public JdbcUserFoodDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UserFoodInfo findById(long id) throws PersistentException {
        UserFoodInfo userFoodInfo;

        try (Connection conn = dataSource.getConnection()) {
            userFoodInfo = find(conn, id);
        } catch (SQLException e) {
            throw new PersistentException("UserFoodInfo doesn't exist.", e);
        }

        return userFoodInfo;
    }

    @Override
    public UserFoodInfo findByIdInInterval(long id, LocalDateTime fromInclude, LocalDateTime toInclude) throws PersistentException {
        return null;
    }

    @Override
    public UserFoodInfo findByUserId(long userId) throws PersistentException {
        UserFoodInfo userFoodInfo;

        try (Connection conn = dataSource.getConnection()) {
            long id = findUserFoodInfoIdByUserId(conn, userId);
            userFoodInfo = find(conn, id);
        } catch (SQLException e) {
            throw new PersistentException("UserFoodInfo doesn't exist.", e);
        }

        return userFoodInfo;
    }

    private long findUserFoodInfoIdByUserId(Connection conn, long id) throws SQLException, PersistentException {
        long result;

        PreparedStatement preparedStatement = conn.prepareStatement(Queries.findUserFoodInfoIdByUserId);
        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            result = resultSet.getLong("id");
        } else {
            throw new PersistentException("User doesn't exist.");
        }

        return result;
    }

    @Override
    public UserFoodInfo create(long userId, UserFoodInfo userFoodInfo) throws PersistentException {
        try (Connection conn = dataSource.getConnection()) {
            create(conn, userId, userFoodInfo);
        } catch (SQLException e) {
            throw new PersistentException("UserFoodInfo creation error.", e);
        }

        return userFoodInfo;
    }

    @Override
    public void update(UserFoodInfo userFoodInfo) throws PersistentException {
        // TODO
    }

    //TODO: code duplicated
    @Override
    public void delete(long id) throws PersistentException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(Queries.deleteById);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException("User_Food_Info delete error.", e);
        }
    }

    static UserFoodInfo find(Connection conn, long id) throws SQLException {
        UserFoodInfo userFoodInfo;

        PreparedStatement preparedStatement = conn.prepareStatement(Queries.findById);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        userFoodInfo = userFoodInfoMapping(resultSet);

        preparedStatement = conn.prepareStatement(getDishes);
        preparedStatement.setLong(1, userFoodInfo.getId());

        resultSet = preparedStatement.executeQuery();

        userFoodInfo.setMealTimestamp(getMealTimestampMap(conn, resultSet));

        return userFoodInfo;
    }

    private static UserFoodInfo userFoodInfoMapping(ResultSet resultSet) throws SQLException {
        UserFoodInfo userFoodInfo = new UserFoodInfo();

        if (resultSet.next()) {
            userFoodInfo.setId(resultSet.getLong("id"));
            userFoodInfo.setCaloriesPerDay(resultSet.getFloat("calories_per_day"));
            // TODO: IngredientValidator.validate(ingredient)
        } else {
            userFoodInfo.setId(-1L);
        }

        return userFoodInfo;
    }

    private static NavigableMap<LocalDateTime, Dish> getMealTimestampMap(Connection conn, ResultSet resultSet) throws SQLException {
        NavigableMap<LocalDateTime, Dish> dishTimestamp = new TreeMap<>();

        while (resultSet.next()) {
            Dish dish = JdbcDishDao.find(conn, resultSet.getLong("dish_fk"));

            dishTimestamp.put(resultSet.getTimestamp("date_time").toLocalDateTime(), dish);
        }

        return dishTimestamp;
    }

    private static UserFoodInfo create(Connection conn, long userId, UserFoodInfo userFoodInfo) throws SQLException {
        // TODO: conn.setAutoCommit(off) - transaction
        PreparedStatement preparedStatement = conn.prepareStatement(Queries.create, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1, userId);
        preparedStatement.setFloat(2, (float)userFoodInfo.getCaloriesPerDay());

        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if (resultSet.next()) {
            userFoodInfo.setId(resultSet.getLong(1));
        }

        String createIngredientDishRelation = "INSERT INTO User_Food_Info_Dish_Relations (user_food_info_fk, dish_fk, date_time) VALUES(?, ?, ?)";
        preparedStatement = conn.prepareStatement(createIngredientDishRelation);
        for (Map.Entry<LocalDateTime, Dish> element : userFoodInfo.getMealTimestamp().entrySet()) {
            preparedStatement.setLong(1, userFoodInfo.getId());
            preparedStatement.setLong(2, element.getValue().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(element.getKey()));
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();

        return userFoodInfo;
    }

    private static class Queries extends QueriesInitializer {
        static boolean loaded = false;
        static String findById;
        static String findUserFoodInfoIdByUserId;
        static String deleteById;
        static String create;

        protected boolean loaded() {
            return loaded;
        }

        protected void setLoad() {
            loaded = true;
        }
    }
}

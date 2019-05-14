package ua.training.model.dao;

import ua.training.model.entity.User;
import ua.training.utils.QueriesInitializer;
import ua.training.utils.exception.PersistentException;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;

public class JdbcUserDao implements UserDao {
    private DataSource dataSource;

    @Inject
    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(long id) throws PersistentException {
        User user;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(Queries.findById);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = userMapping(resultSet);
        } catch (SQLException e) {
            throw new PersistentException("User id doesn't exist.", e);
        }

        return user;
    }

    @Override
    public User findByName(String name) throws PersistentException {
        User user;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(Queries.findByName);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = userMapping(resultSet);
        } catch (SQLException e) {
            throw new PersistentException("User name doesn't exist.", e);
        }

        return user;
    }

    @Override
    public User create(User user) throws PersistentException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(Queries.create, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());


            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            throw new PersistentException("User creation error.", e);
        }

        return user;
    }

    @Override
    public void update(User user) throws PersistentException {
        if (user.getId() == -1)
            throw new PersistentException("Wrong user id.");

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(Queries.update);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException("User update error.", e);
        }
    }

    @Override
    public void delete(long id) throws PersistentException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(Queries.deleteById);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException("User delete error.", e);
        }
    }

    private static User userMapping(ResultSet resultSet) throws SQLException {
        User user = new User();

        if (resultSet.next()) {
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setLogin(resultSet.getString("login"));
            // TODO: IngredientValidator.validate(ingredient)
        } else {
            user.setId(-1L);
        }

        return user;
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

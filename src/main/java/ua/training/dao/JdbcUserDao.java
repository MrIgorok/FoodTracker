package ua.training.dao;

import ua.training.configuration.Inject;
import ua.training.model.User;
import ua.training.utils.exception.ServiceException;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcUserDao implements UserDao {
    private DataSource dataSource;

    @Inject
    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(long id) throws ServiceException {
        User user;

        try (Connection conn = dataSource.getConnection()) {
            String findById = "SELECT id, name, login FROM User_t WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(findById);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = userMapping(resultSet);
        } catch (SQLException e) {
            throw new ServiceException("User id doesn't exist.", e);
        }

        return user;
    }

    @Override
    public User findByName(String name) throws ServiceException {
        User user;

        try (Connection conn = dataSource.getConnection()) {
            String findById = "SELECT id, name, login FROM User_t WHERE name = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(findById);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = userMapping(resultSet);
        } catch (SQLException e) {
            throw new ServiceException("User name doesn't exist.", e);
        }

        return user;
    }

    @Override
    public User create(User user) throws ServiceException {
        try (Connection conn = dataSource.getConnection()) {
            String create = "INSERT INTO User (name, login, password) VALUES(?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(create, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());


            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            throw new ServiceException("User creation error.", e);
        }

        return user;
    }

    @Override
    public void update(User user) throws ServiceException {
        if (user.getId() == -1)
            throw new ServiceException("Wrong user id.");

        try (Connection conn = dataSource.getConnection()) {
            String update = "UPDATE User SET name = ?, login = ?, password = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(update);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ServiceException("User update error.", e);
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        try (Connection conn = dataSource.getConnection()) {
            String deleteById = "DELETE FROM User WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(deleteById);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ServiceException("User delete error.", e);
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

}

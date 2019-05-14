package ua.training.configuration;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataBaseConfiguration {
    private static final DataSource dataSource;

    static {
        InitialContext context;

        try {
            context = new InitialContext();

            dataSource = (DataSource)context.lookup("java:/comp/env/jdbc/postgres");
        } catch (NamingException e) {
            // TODO: Log
            throw new RuntimeException(e);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}

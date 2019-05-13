package ua.training.configuration;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class DataBaseConfiguration {
    private static final BasicDataSource dataSource;

    static {
        ResourceBundle databaseProperties = PropertyResourceBundle.getBundle("database.postgres");
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(databaseProperties.getString("driver"));
        dataSource.setUrl(databaseProperties.getString("url"));
        dataSource.setUsername(databaseProperties.getString("user"));
        dataSource.setPassword(databaseProperties.getString("password"));
        dataSource.addConnectionProperty("ssl", databaseProperties.getString("ssl"));
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
